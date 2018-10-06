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

import io.machinecode.hexane.HexaneConnection.ClosedConnection;

import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class HexaneManagedConnection extends Terminal implements PooledConnection {
  private final Pooled<Connection> val;
  private Connection delegate;
  private final Defaults defaults;
  private final Set<ConnectionEventListener> connectionListeners;
  private final Set<StatementEventListener> statementListeners;
  private final Map<PreparedStatement, CheckedPreparedStatement> statements;

  HexaneManagedConnection(
      final Config config, final Pooled<Connection> val, final Defaults defaults) {
    super(config);
    this.val = val;
    this.delegate = val.getValue();
    this.defaults = defaults;
    this.statements = new IdentityHashMap<>();
    this.connectionListeners = Collections.newSetFromMap(new IdentityHashMap<>());
    this.statementListeners = Collections.newSetFromMap(new IdentityHashMap<>());
  }

  @Override
  void kill(final SQLException e) {
    delegate = ClosedConnection.INSTANCE;
    final SQLException ex = val.close(true);
    if (ex != null) {
      e.addSuppressed(ex);
    }
    final ConnectionEvent event = new ConnectionEvent(this, e);
    for (final ConnectionEventListener listener : connectionListeners) {
      try {
        listener.connectionErrorOccurred(event);
      } catch (final Exception le) {
        e.addSuppressed(le);
      }
    }
    connectionListeners.clear();
    statementListeners.clear();
    statements.clear();
  }

  @Override
  void evict(final PreparedStatement statement, final SQLException e) {
    if (val.getCache().remove(statement)) {
      config.getListener().onStatementErrorEviction();
    }
  }

  @Override
  void enlist(final AutoCloseable it) throws SQLException {
    if (delegate == ClosedConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    val.enlist(it);
  }

  @Override
  void delist(final AutoCloseable it) throws SQLException {
    if (delegate == ClosedConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    val.delist(it);
  }

  @Override
  void registerChecked(final CheckedPreparedStatement it, final PreparedStatement real)
      throws SQLException {
    if (delegate == ClosedConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    statements.put(real, it);
  }

  @Override
  public Connection getConnection() throws SQLException {
    if (delegate == ClosedConnection.INSTANCE) {
      throw new SQLException(Msg.CONNECTION_CLOSED);
    }
    return new CheckedConnection(this, val, delegate, defaults);
  }

  @Override
  public void addConnectionEventListener(final ConnectionEventListener listener) {
    if (delegate == ClosedConnection.INSTANCE) {
      return;
    }
    connectionListeners.add(listener);
  }

  @Override
  public void removeConnectionEventListener(final ConnectionEventListener listener) {
    if (delegate == ClosedConnection.INSTANCE) {
      return;
    }
    connectionListeners.remove(listener);
  }

  @Override
  public void addStatementEventListener(final StatementEventListener listener) {
    if (delegate == ClosedConnection.INSTANCE) {
      return;
    }
    statementListeners.add(listener);
  }

  @Override
  public void removeStatementEventListener(final StatementEventListener listener) {
    if (delegate == ClosedConnection.INSTANCE) {
      return;
    }
    statementListeners.remove(listener);
  }

  @Override
  public void close() throws SQLException {
    delegate = ClosedConnection.INSTANCE;
    final BooleanRef fatal = new BooleanRef(false);
    SQLException ex = val.closeEnlisted(fatal);
    final ConnectionEvent event = new ConnectionEvent(this, ex);
    for (final ConnectionEventListener listener : connectionListeners) {
      try {
        listener.connectionClosed(event);
      } catch (final Exception le) {
        if (ex == null) {
          ex = new SQLException(le);
        } else {
          ex.addSuppressed(le);
        }
      }
    }
    Util.cleanupConnection(this, val, ex);
  }
}
