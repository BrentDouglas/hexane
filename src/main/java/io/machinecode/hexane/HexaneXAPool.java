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

import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class HexaneXAPool extends BasePool<XAConnection> {
  private final XADataSource dataSource;
  private final Map<Pooled<XAConnection>, ConnectionEventListener> connectionListeners =
      new ConcurrentHashMap<>();
  private final Map<Pooled<XAConnection>, StatementEventListener> statementListeners =
      new ConcurrentHashMap<>();

  HexaneXAPool(final Config config, final Defaults defaults, final XADataSource dataSource) {
    super(config, defaults, HexaneXAPool.class);
    this.dataSource = dataSource;
    executor.execute(task);
  }

  @Override
  protected void register(final Pooled<XAConnection> item) {
    final XAConnection value = item.getValue();
    final ConnectionEventListener connectionListener =
        new HexaneConnectionEventListener<>(this, item);
    connectionListeners.put(item, connectionListener);
    value.addConnectionEventListener(connectionListener);
    final StatementEventListener statementListener = new HexaneStatementEventListener<>(this, item);
    statementListeners.put(item, statementListener);
    value.addStatementEventListener(statementListener);
  }

  @Override
  protected void unregister(final Pooled<XAConnection> item) {
    final XAConnection value = item.getValue();
    final ConnectionEventListener connectionListener = this.connectionListeners.remove(item);
    value.removeConnectionEventListener(connectionListener);
    final StatementEventListener statementListener = this.statementListeners.remove(item);
    value.removeStatementEventListener(statementListener);
  }

  @Override
  protected void closeRegistered() {
    final Iterator<Entry<Pooled<XAConnection>, ConnectionEventListener>> cit =
        this.connectionListeners.entrySet().iterator();
    while (cit.hasNext()) {
      final Entry<Pooled<XAConnection>, ConnectionEventListener> val = cit.next();
      val.getKey().getValue().removeConnectionEventListener(val.getValue());
      cit.remove();
    }
    final Iterator<Entry<Pooled<XAConnection>, StatementEventListener>> sit =
        this.statementListeners.entrySet().iterator();
    while (sit.hasNext()) {
      final Entry<Pooled<XAConnection>, StatementEventListener> val = sit.next();
      val.getKey().getValue().removeStatementEventListener(val.getValue());
      sit.remove();
    }
  }

  @Override
  protected XAConnection getConnection() {
    Connection conn = null;
    try {
      final String user = config.getUser();
      final XAConnection xa;
      if (user == null) {
        xa = dataSource.getXAConnection();
      } else {
        xa = dataSource.getXAConnection(user, config.getPassword());
      }
      return xa;
    } catch (final SQLException e) {
      error(Msg.EXCEPTION_OPENING_CONNECTION, Util.close(conn, e));
      return null;
    }
  }

  @Override
  protected Connection getConnection(final XAConnection item) {
    try {
      return item.getConnection();
    } catch (final SQLException e) {
      error(Msg.EXCEPTION_OPENING_CONNECTION, e);
      return null;
    }
  }

  @Override
  protected AutoCloseable getCloser(final XAConnection item) {
    return item::close;
  }
}
