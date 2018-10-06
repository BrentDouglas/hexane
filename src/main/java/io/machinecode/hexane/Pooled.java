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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class Pooled<T> implements AutoCloseable {
  private final BasePool<T> pool;
  private final T value;
  private final AutoCloseable close;
  private final Set<AutoCloseable> enlisted;
  private final StatementCache cache;
  private final long created;
  private volatile long aquired;
  private volatile long accessed;
  private volatile boolean expired;
  private volatile boolean broken = false;

  Pooled(final BasePool<T> pool, final T value, final AutoCloseable close, final StatementCache cache) {
    this.pool = pool;
    this.value = value;
    this.close = close;
    this.created = Clock.getCurrentTime();
    this.enlisted = Collections.newSetFromMap(new IdentityHashMap<>());
    this.cache = cache;
  }

  T getValue() {
    return value;
  }

  void enlist(final AutoCloseable it) {
    enlisted.add(it);
  }

  void delist(final AutoCloseable it) {
    enlisted.remove(it);
  }

  SQLException closeEnlisted(final BooleanRef fatal) {
    SQLException exception = null;
    for (final AutoCloseable it : enlisted) {
      exception = Util.close(pool.getConfig(), exception, fatal, it);
    }
    enlisted.clear();
    return exception;
  }

  @Override
  public void close() throws SQLException {
    final SQLException ex = close(false);
    if (ex != null) {
      throw ex;
    }
  }

  SQLException close(final boolean broken) {
    if (this.broken) {
      return null;
    }
    this.accessed = Clock.getCurrentTime();
    final BooleanRef fatal = new BooleanRef(broken);
    SQLException exception = closeEnlisted(fatal);
    if (fatal.getVal()) {
      exception = Util.close(pool.getConfig(), exception, fatal, close);
      this.broken = true;
      pool.remove(this);
    } else if (!this.expired) {
      pool.give(this);
    }
    return exception;
  }

  void expire() {
    this.expired = true;
  }

  long getAccessed() {
    return this.accessed;
  }

  long getAquired() {
    return aquired;
  }

  void setAquired(final long aquired) {
    this.aquired = aquired;
  }

  long getCreated() {
    return this.created;
  }

  StatementCache getCache() {
    return cache;
  }
}
