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
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class HexanePooledConnection extends Terminal implements PooledConnection {
  private final Pooled<PooledConnection> val;
  private PooledConnection delegate;
  private final Defaults defaults;

  HexanePooledConnection(
      final Config config, final Pooled<PooledConnection> val, final Defaults defaults) {
    super(config);
    this.val = val;
    this.delegate = val.getValue();
    this.defaults = defaults;
  }

  @Override
  void kill(final SQLException e) {
    delegate = ClosedPooledConnection.INSTANCE;
    final SQLException ex = val.close(true);
    if (ex != null) {
      e.addSuppressed(ex);
    }
  }

  @Override
  void evict(final PreparedStatement statement, final SQLException e) {
    if (val.getCache().remove(statement)) {
      config.getListener().onStatementErrorEviction();
    }
  }

  @Override
  void enlist(final AutoCloseable it) throws SQLException {
    if (delegate == ClosedPooledConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    val.enlist(it);
  }

  @Override
  void delist(final AutoCloseable it) throws SQLException {
    if (delegate == ClosedPooledConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    val.delist(it);
  }

  @Override
  void registerChecked(final CheckedPreparedStatement it, final PreparedStatement real)
      throws SQLException {
    if (delegate == ClosedPooledConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    if (delegate == ClosedPooledConnection.INSTANCE) {
      throw new SQLException(Msg.CONNECTION_CLOSED);
    }
    if (config.isCheckFatal()) {
      return new CheckedConnection(this, val, val.getConnection(), defaults);
    } else {
      return new UncheckedConnection(this, val, val.getConnection(), defaults);
    }
  }

  @Override
  public void addConnectionEventListener(final ConnectionEventListener listener) {
    if (delegate == ClosedPooledConnection.INSTANCE) {
      return;
    }
    delegate.addConnectionEventListener(listener);
  }

  @Override
  public void removeConnectionEventListener(final ConnectionEventListener listener) {
    if (delegate == ClosedPooledConnection.INSTANCE) {
      return;
    }
    delegate.removeConnectionEventListener(listener);
  }

  @Override
  public void addStatementEventListener(final StatementEventListener listener) {
    if (delegate == ClosedPooledConnection.INSTANCE) {
      return;
    }
    delegate.addStatementEventListener(listener);
  }

  @Override
  public void removeStatementEventListener(final StatementEventListener listener) {
    if (delegate == ClosedPooledConnection.INSTANCE) {
      return;
    }
    delegate.removeStatementEventListener(listener);
  }

  @Override
  public void close() throws SQLException {
    delegate = ClosedPooledConnection.INSTANCE;
    final SQLException se = val.close(false);
    if (se != null) {
      throw Util.handleFatalSQL(this, se);
    }
  }

  static final class ClosedPooledConnection implements PooledConnection {
    static final ClosedPooledConnection INSTANCE = new ClosedPooledConnection();

    @Override
    public Connection getConnection() throws SQLException {
      throw getClosedException();
    }

    @Override
    public void close() throws SQLException {}

    @Override
    public void addConnectionEventListener(final ConnectionEventListener listener) {}

    @Override
    public void removeConnectionEventListener(final ConnectionEventListener listener) {}

    @Override
    public void addStatementEventListener(final StatementEventListener listener) {}

    @Override
    public void removeStatementEventListener(final StatementEventListener listener) {}

    @Override
    public String toString() {
      return ClosedPooledConnection.class.getSimpleName();
    }

    private static SQLNonTransientException getClosedException() {
      return new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
  }
}
