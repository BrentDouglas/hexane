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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
class CheckedStatement<S extends Statement> implements Statement {
  final Terminal xa;
  final Connection conn;
  final S delegate;

  CheckedStatement(final Terminal xa, final Connection conn, final S delegate) throws SQLException {
    this.xa = xa;
    this.conn = conn;
    this.delegate = delegate;
    xa.enlist(this);
  }

  @Override
  public ResultSet executeQuery(final String sql) throws SQLException {
    try {
      return new CheckedResultSet(xa, this, delegate.executeQuery(sql));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int executeUpdate(final String sql) throws SQLException {
    try {
      return delegate.executeUpdate(sql);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void close() throws SQLException {
    try {
      delegate.close();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    } finally {
      xa.delist(this);
    }
  }

  @Override
  public int getMaxFieldSize() throws SQLException {
    try {
      return delegate.getMaxFieldSize();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setMaxFieldSize(final int max) throws SQLException {
    try {
      delegate.setMaxFieldSize(max);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getMaxRows() throws SQLException {
    try {
      return delegate.getMaxRows();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setMaxRows(final int max) throws SQLException {
    try {
      delegate.setMaxRows(max);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setEscapeProcessing(final boolean enable) throws SQLException {
    try {
      delegate.setEscapeProcessing(enable);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    try {
      return delegate.getQueryTimeout();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setQueryTimeout(final int seconds) throws SQLException {
    try {
      delegate.setQueryTimeout(seconds);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void cancel() throws SQLException {
    try {
      delegate.cancel();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    try {
      return delegate.getWarnings();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void clearWarnings() throws SQLException {
    try {
      delegate.clearWarnings();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setCursorName(final String name) throws SQLException {
    try {
      delegate.setCursorName(name);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean execute(final String sql) throws SQLException {
    try {
      return delegate.execute(sql);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    try {
      return new CheckedResultSet(xa, this, delegate.getResultSet());
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getUpdateCount() throws SQLException {
    try {
      return delegate.getUpdateCount();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    try {
      return delegate.getMoreResults();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setFetchDirection(final int direction) throws SQLException {
    try {
      delegate.setFetchDirection(direction);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getFetchDirection() throws SQLException {
    try {
      return delegate.getFetchDirection();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setFetchSize(final int rows) throws SQLException {
    try {
      delegate.setFetchSize(rows);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getFetchSize() throws SQLException {
    try {
      return delegate.getFetchSize();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {
    try {
      return delegate.getResultSetConcurrency();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getResultSetType() throws SQLException {
    try {
      return delegate.getResultSetType();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void addBatch(final String sql) throws SQLException {
    try {
      delegate.addBatch(sql);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void clearBatch() throws SQLException {
    try {
      delegate.clearBatch();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int[] executeBatch() throws SQLException {
    try {
      return delegate.executeBatch();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    return conn;
  }

  @Override
  public boolean getMoreResults(final int current) throws SQLException {
    try {
      return delegate.getMoreResults(current);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    try {
      return new CheckedResultSet(xa, this, delegate.getGeneratedKeys());
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
    try {
      return delegate.executeUpdate(sql, autoGeneratedKeys);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
    try {
      return delegate.executeUpdate(sql, columnIndexes);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
    try {
      return delegate.executeUpdate(sql, columnNames);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
    try {
      return delegate.execute(sql, autoGeneratedKeys);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
    try {
      return delegate.execute(sql, columnIndexes);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean execute(final String sql, final String[] columnNames) throws SQLException {
    try {
      return delegate.execute(sql, columnNames);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    try {
      return delegate.getResultSetHoldability();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isClosed() throws SQLException {
    try {
      return delegate.isClosed();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setPoolable(final boolean poolable) throws SQLException {
    try {
      delegate.setPoolable(poolable);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isPoolable() throws SQLException {
    try {
      return delegate.isPoolable();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void closeOnCompletion() throws SQLException {
    try {
      delegate.closeOnCompletion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    try {
      return delegate.isCloseOnCompletion();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long getLargeUpdateCount() throws SQLException {
    try {
      return delegate.getLargeUpdateCount();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setLargeMaxRows(final long max) throws SQLException {
    try {
      delegate.setLargeMaxRows(max);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long getLargeMaxRows() throws SQLException {
    try {
      return delegate.getLargeMaxRows();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long[] executeLargeBatch() throws SQLException {
    try {
      return delegate.executeLargeBatch();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long executeLargeUpdate(final String sql) throws SQLException {
    try {
      return delegate.executeLargeUpdate(sql);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long executeLargeUpdate(final String sql, final int autoGeneratedKeys)
      throws SQLException {
    try {
      return delegate.executeLargeUpdate(sql, autoGeneratedKeys);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long executeLargeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
    try {
      return delegate.executeLargeUpdate(sql, columnIndexes);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long executeLargeUpdate(final String sql, final String[] columnNames) throws SQLException {
    try {
      return delegate.executeLargeUpdate(sql, columnNames);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public <T> T unwrap(final Class<T> iface) throws SQLException {
    try {
      return Util.unwrap(iface, this, delegate);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    try {
      return Util.isWrapperFor(iface, this, delegate);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }
}
