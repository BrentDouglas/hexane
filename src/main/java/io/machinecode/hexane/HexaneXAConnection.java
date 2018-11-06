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
import javax.transaction.xa.XAResource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class HexaneXAConnection extends Terminal implements XAConnection {
  private final Pooled<XAConnection> val;
  private final Defaults defaults;
  private XAConnection delegate;

  HexaneXAConnection(final Config config, final Pooled<XAConnection> val, final Defaults defaults) {
    super(config);
    this.val = val;
    this.delegate = val.getValue();
    this.defaults = defaults;
  }

  @Override
  void kill(final SQLException e) {
    delegate = ClosedXAConnection.INSTANCE;
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
    if (delegate == ClosedXAConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    val.enlist(it);
  }

  @Override
  void delist(final AutoCloseable it) throws SQLException {
    if (delegate == ClosedXAConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    val.delist(it);
  }

  @Override
  void registerChecked(final CheckedPreparedStatement it, final PreparedStatement real)
      throws SQLException {
    if (delegate == ClosedXAConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
  }

  @Override
  public XAResource getXAResource() throws SQLException {
    if (delegate == ClosedXAConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    return delegate.getXAResource();
  }

  @Override
  public Connection getConnection() throws SQLException {
    if (delegate == ClosedXAConnection.INSTANCE) {
      throw new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
    if (config.isCheckFatal()) {
      return new CheckedConnection(this, val, val.getConnection(), defaults);
    } else {
      return new UncheckedConnection(this, val, val.getConnection(), defaults);
    }
  }

  @Override
  public void close() throws SQLException {
    delegate = ClosedXAConnection.INSTANCE;
    final SQLException se = val.close(false);
    if (se != null) {
      throw Util.handleFatalSQL(this, se);
    }
  }

  @Override
  public void addConnectionEventListener(final ConnectionEventListener listener) {
    if (delegate == ClosedXAConnection.INSTANCE) {
      return;
    }
    delegate.addConnectionEventListener(listener);
  }

  @Override
  public void removeConnectionEventListener(final ConnectionEventListener listener) {
    if (delegate == ClosedXAConnection.INSTANCE) {
      return;
    }
    delegate.removeConnectionEventListener(listener);
  }

  @Override
  public void addStatementEventListener(final StatementEventListener listener) {
    if (delegate == ClosedXAConnection.INSTANCE) {
      return;
    }
    delegate.addStatementEventListener(listener);
  }

  @Override
  public void removeStatementEventListener(final StatementEventListener listener) {
    if (delegate == ClosedXAConnection.INSTANCE) {
      return;
    }
    delegate.removeStatementEventListener(listener);
  }

  static final class ClosedXAConnection implements XAConnection {
    static final ClosedXAConnection INSTANCE = new ClosedXAConnection();

    @Override
    public XAResource getXAResource() throws SQLException {
      throw getClosedException();
    }

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
      return ClosedXAConnection.class.getSimpleName();
    }

    private static SQLNonTransientException getClosedException() {
      return new SQLNonTransientException(Msg.CONNECTION_CLOSED);
    }
  }
}
