/*
 * Copyright (C) 2018 Brent Douglas and other contributors
 * as indicated by the @author tags. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.machinecode.hexane;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.sql.SQLTransientConnectionException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
abstract class BasePool<C> implements AutoCloseable {
  static final int STATE_OK = 0;
  static final int STATE_CLOSED = 1;

  private final LinkedBlockingDeque<Pooled<C>> free;
  private final Set<Pooled<C>> all = Collections.newSetFromMap(new ConcurrentHashMap<>());
  private final AtomicInteger total = new AtomicInteger();
  private final Logger log;
  final Config config;
  final Defaults defaults;
  final Executor executor;
  final Runnable task = this::refill;
  private volatile int state;

  BasePool(final Config config, final Defaults defaults) {
    this(config, defaults, BasePool.class);
  }

  BasePool(final Config config, final Defaults defaults, final Class<?> clazz) {
    this.config = config;
    this.defaults = defaults;
    this.free = new LinkedBlockingDeque<>(config.getMaxPoolSize());
    this.executor = config.getMaintenanceExecutor();
    this.log = config.getLoggerFactory().getLogger(clazz);
  }

  Config getConfig() {
    return config;
  }

  Pooled<C> take() throws SQLException {
    if (state != STATE_OK) {
      throw new SQLNonTransientException(Msg.POOL_IS_CLOSED);
    }
    final long start = Clock.getCurrentTime();
    final long timeout = config.getConnectionTimeout();
    final TimeUnit timeoutUnit = config.getConnectionTimeoutUnit();
    try {
      executor.execute(task);
      final Pooled<C> val = timeout == Config.UNSET ? free.poll() : free.poll(timeout, timeoutUnit);
      if (val == null) {
        config.getListener().onConnectionTimeout(start);
        throw new SQLTransientConnectionException(Msg.POOL_TIMEOUT);
      }
      final long end = Clock.getCurrentTime();
      val.setAquired(end);
      config.getListener().onConnectionAcquired(start, end);
      return val;
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new SQLException(e);
    }
  }

  void give(final Pooled<C> val) {
    if (state != STATE_OK) {
      return;
    }
    config.getListener().onConnectionReturned(val.getAquired());
    this.free.addFirst(val);
  }

  void remove(final Pooled<C> val) {
    if (state != STATE_OK) {
      return;
    }
    this.all.remove(val);
    this.total.decrementAndGet();
    unregister(val);
    config.getListener().onConnectionErrorEviction();
    executor.execute(task);
  }

  @Override
  public void close() throws SQLException {
    this.state = STATE_CLOSED;
    final InternalListener listeners = config.getListener();
    SQLException exception = null;
    final BooleanRef fatal = new BooleanRef(false);
    Pooled<C> item;
    while ((item = this.free.poll()) != null) {
      exception = Util.close(config, exception, fatal, item);
      this.all.remove(item);
      this.total.decrementAndGet();
      listeners.onDataSourceClosedEviction();
    }
    final Iterator<Pooled<C>> it = this.all.iterator();
    while (it.hasNext()) {
      item = it.next();
      exception = Util.close(config, exception, fatal, item);
      it.remove();
      this.total.decrementAndGet();
      listeners.onDataSourceClosedEviction();
    }
    closeRegistered();
    listeners.onDataSourceClosed();
    final AutoCloseable closeable = config.getMaintenanceExecutorClosable();
    if (closeable != null) {
      exception = Util.close(closeable, exception);
    }
    if (exception != null) {
      throw exception;
    }
  }

  void refill() {
    final int coreSize = config.getCorePoolSize(), maxSize = config.getMaxPoolSize();
    final long idle = config.getIdleTimeout(), lifetime = config.getLifetimeTimeout();
    int total = this.total.get();
    for (; ; ) {
      if (state != STATE_OK) {
        return;
      }
      final boolean demand = this.free.size() == 0, capacity = total < maxSize;
      final long now = Clock.getCurrentTime();
      if (total < coreSize || (demand && capacity)) {
        addNew(total, maxSize, now);
      } else if (total > coreSize) {
        removeIdle(idle, now);
      }
      removeExpired(lifetime, now);
      total = this.total.get();
      if (total >= coreSize) {
        return;
      }
    }
  }

  void addNew(int total, final int limit, final long start) {
    final C value = getConnection();
    if (value == null) {
      return;
    }
    final AutoCloseable close = getCloser(value);
    final Connection conn = getConnection(value);
    if (conn == null) {
      return;
    }
    try {
      if (!conn.isValid(config.getValidationTimeout())) {
        return;
      }
      defaults.initialize(conn);
    } catch (final SQLException e) {
      return;
    }
    final int cacheSize = config.getStatementCacheSize();
    final InternalListener listener = config.getListener();
    final StatementCache cache =
        cacheSize > 0 ? new LRUStatementCache(cacheSize, listener) : StatementCache.INSTANCE;
    final Pooled<C> item = new Pooled<>(this, value, conn, close, cache);
    boolean add = false;
    do {
      if (this.total.compareAndSet(total, total + 1)) {
        add = true;
        break;
      }
    } while ((total = this.total.get()) < limit);
    if (!add) {
      Util.close(close);
      return;
    }
    register(item);
    this.all.add(item);
    this.free.add(item);
    listener.onConnectionCreation(start);
  }

  private void removeIdle(final long idle, final long now) {
    if (idle == Config.UNSET) {
      return;
    }
    for (final Pooled<C> val : all) {
      if (Clock.getElapsedTime(val.getAccessed(), now) > idle && free.remove(val)) {
        this.all.remove(val);
        this.total.decrementAndGet();
        config.getListener().onConnectionIdleEviction();
        return;
      }
    }
  }

  private void removeExpired(final long lifetime, final long now) {
    if (lifetime == Config.UNSET) {
      return;
    }
    for (final Pooled<C> val : all) {
      if (Clock.getElapsedTime(val.getCreated(), now) > lifetime) {
        val.expire();
        this.free.remove(val);
        this.all.remove(val);
        this.total.decrementAndGet();
        config.getListener().onConnectionLifetimeEviction();
        break;
      }
    }
  }

  void warn(final String msg, final SQLException e) {
    if (e == null) {
      return;
    }
    this.log.warn(msg, e);
  }

  void error(final String msg, final SQLException e) {
    if (e == null) {
      return;
    }
    this.log.error(msg, e);
  }

  protected void register(final Pooled<C> item) {}

  protected void unregister(final Pooled<C> item) {}

  protected void closeRegistered() {}

  protected abstract C getConnection();

  protected abstract Connection getConnection(final C item);

  protected abstract AutoCloseable getCloser(final C item);

  int getTotal() {
    return total.get();
  }

  int getFree() {
    return free.size();
  }

  boolean isClosed() {
    return state == STATE_CLOSED;
  }
}
