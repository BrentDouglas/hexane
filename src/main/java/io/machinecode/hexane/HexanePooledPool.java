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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class HexanePooledPool extends BasePool<PooledConnection> {
  private final ConnectionPoolDataSource dataSource;
  private final Map<Pooled<PooledConnection>, ConnectionEventListener> connectionListeners =
      new ConcurrentHashMap<>();
  private final Map<Pooled<PooledConnection>, StatementEventListener> statementListeners =
      new ConcurrentHashMap<>();

  HexanePooledPool(
      final Config config, final Defaults defaults, final ConnectionPoolDataSource dataSource) {
    super(config, defaults, HexanePooledPool.class);
    this.dataSource = dataSource;
    executor.execute(task);
  }

  @Override
  protected void register(final Pooled<PooledConnection> item) {
    final PooledConnection value = item.getValue();
    final ConnectionEventListener connectionListener =
        new HexaneConnectionEventListener<>(this, item);
    connectionListeners.put(item, connectionListener);
    value.addConnectionEventListener(connectionListener);
    final StatementEventListener statementListener = new HexaneStatementEventListener<>(this, item);
    statementListeners.put(item, statementListener);
    value.addStatementEventListener(statementListener);
  }

  @Override
  protected void unregister(final Pooled<PooledConnection> item) {
    final PooledConnection value = item.getValue();
    final ConnectionEventListener connectionListener = this.connectionListeners.remove(item);
    value.removeConnectionEventListener(connectionListener);
    final StatementEventListener statementListener = this.statementListeners.remove(item);
    value.removeStatementEventListener(statementListener);
  }

  @Override
  protected void closeRegistered() {
    final Iterator<Entry<Pooled<PooledConnection>, ConnectionEventListener>> cit =
        this.connectionListeners.entrySet().iterator();
    while (cit.hasNext()) {
      final Entry<Pooled<PooledConnection>, ConnectionEventListener> val = cit.next();
      val.getKey().getValue().removeConnectionEventListener(val.getValue());
      cit.remove();
    }
    final Iterator<Entry<Pooled<PooledConnection>, StatementEventListener>> sit =
        this.statementListeners.entrySet().iterator();
    while (sit.hasNext()) {
      final Entry<Pooled<PooledConnection>, StatementEventListener> val = sit.next();
      val.getKey().getValue().removeStatementEventListener(val.getValue());
      sit.remove();
    }
  }

  @Override
  protected PooledConnection getConnection() {
    Connection conn = null;
    try {
      final String user = config.getUser();
      final PooledConnection xa;
      if (user == null) {
        xa = dataSource.getPooledConnection();
      } else {
        xa = dataSource.getPooledConnection(user, config.getPassword());
      }
      return xa;
    } catch (final SQLException e) {
      error(Msg.EXCEPTION_OPENING_CONNECTION, Util.close(conn, e));
      return null;
    }
  }

  @Override
  protected Connection getConnection(final PooledConnection item) {
    try {
      return item.getConnection();
    } catch (final SQLException e) {
      error(Msg.EXCEPTION_OPENING_CONNECTION, e);
      return null;
    }
  }

  @Override
  protected AutoCloseable getCloser(final PooledConnection item) {
    return item::close;
  }
}
