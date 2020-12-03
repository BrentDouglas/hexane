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

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class CheckedConnection implements Connection {
  private final Terminal xa;
  private final Pooled<?> val;
  private final Connection delegate;
  private final Defaults defaults;
  private final StatementCache cache;
  private int flags = 0;

  CheckedConnection(
      final Terminal xa, final Pooled<?> val, final Connection delegate, final Defaults defaults) {
    this.xa = xa;
    this.val = val;
    this.delegate = delegate;
    this.defaults = defaults;
    this.cache = val.getCache();
  }

  @Override
  public Statement createStatement() throws SQLException {
    try {
      return new CheckedStatement<>(xa, this, delegate.createStatement());
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public PreparedStatement prepareStatement(final String sql) throws SQLException {
    try {
      return new CheckedPreparedStatement<>(xa, this, cache.prepareStatement(delegate, sql));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public CallableStatement prepareCall(final String sql) throws SQLException {
    try {
      return new CheckedCallableStatement(xa, this, cache.prepareCall(delegate, sql));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String nativeSQL(final String sql) throws SQLException {
    try {
      return delegate.nativeSQL(sql);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setAutoCommit(final boolean autoCommit) throws SQLException {
    try {
      flags = defaults.setAutoCommit(delegate, flags, autoCommit);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    try {
      return delegate.getAutoCommit();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void commit() throws SQLException {
    try {
      delegate.commit();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void rollback() throws SQLException {
    try {
      delegate.rollback();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void close() throws SQLException {
    final BooleanRef fatal = new BooleanRef(false);
    SQLException ex = val.closeEnlisted(fatal);
    flags = defaults.reset(delegate, flags);
    Util.cleanupConnection(xa, val, ex);
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
  public DatabaseMetaData getMetaData() throws SQLException {
    try {
      return new CheckedDatabaseMetaData(xa, this, delegate.getMetaData());
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setReadOnly(final boolean readOnly) throws SQLException {
    try {
      flags = defaults.setReadOnly(delegate, flags, readOnly);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isReadOnly() throws SQLException {
    try {
      return delegate.isReadOnly();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setCatalog(final String catalog) throws SQLException {
    try {
      flags = defaults.setCatalog(delegate, flags, catalog);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getCatalog() throws SQLException {
    try {
      return delegate.getCatalog();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setTransactionIsolation(final int level) throws SQLException {
    try {
      flags = defaults.setTransactionIsolation(delegate, flags, level);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    try {
      return delegate.getTransactionIsolation();
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
  public Statement createStatement(final int resultSetType, final int resultSetConcurrency)
      throws SQLException {
    try {
      return new CheckedStatement<>(
          xa, this, delegate.createStatement(resultSetType, resultSetConcurrency));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public PreparedStatement prepareStatement(
      final String sql, final int resultSetType, final int resultSetConcurrency)
      throws SQLException {
    try {
      return new CheckedPreparedStatement<>(
          xa, this, cache.prepareStatement(delegate, sql, resultSetType, resultSetConcurrency));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public CallableStatement prepareCall(
      final String sql, final int resultSetType, final int resultSetConcurrency)
      throws SQLException {
    try {
      return new CheckedCallableStatement(
          xa, this, cache.prepareCall(delegate, sql, resultSetType, resultSetConcurrency));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    try {
      return delegate.getTypeMap();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
    try {
      flags = defaults.setTypeMap(delegate, flags, map);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setHoldability(final int holdability) throws SQLException {
    try {
      flags = defaults.setHoldability(delegate, flags, holdability);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getHoldability() throws SQLException {
    try {
      return delegate.getHoldability();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    try {
      return delegate.setSavepoint();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Savepoint setSavepoint(final String name) throws SQLException {
    try {
      return delegate.setSavepoint(name);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void rollback(final Savepoint savepoint) throws SQLException {
    try {
      delegate.rollback(savepoint);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
    try {
      delegate.releaseSavepoint(savepoint);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Statement createStatement(
      final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)
      throws SQLException {
    try {
      return new CheckedStatement<>(
          xa,
          this,
          delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public PreparedStatement prepareStatement(
      final String sql,
      final int resultSetType,
      final int resultSetConcurrency,
      final int resultSetHoldability)
      throws SQLException {
    try {
      return new CheckedPreparedStatement<>(
          xa,
          this,
          cache.prepareStatement(
              delegate, sql, resultSetType, resultSetConcurrency, resultSetHoldability));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public CallableStatement prepareCall(
      final String sql,
      final int resultSetType,
      final int resultSetConcurrency,
      final int resultSetHoldability)
      throws SQLException {
    try {
      return new CheckedCallableStatement(
          xa,
          this,
          cache.prepareCall(
              delegate, sql, resultSetType, resultSetConcurrency, resultSetHoldability));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys)
      throws SQLException {
    try {
      return new CheckedPreparedStatement<>(
          xa, this, cache.prepareStatement(delegate, sql, autoGeneratedKeys));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes)
      throws SQLException {
    try {
      return new CheckedPreparedStatement<>(
          xa, this, cache.prepareStatement(delegate, sql, columnIndexes));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public PreparedStatement prepareStatement(final String sql, final String[] columnNames)
      throws SQLException {
    try {
      return new CheckedPreparedStatement<>(
          xa, this, cache.prepareStatement(delegate, sql, columnNames));
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Clob createClob() throws SQLException {
    try {
      return delegate.createClob();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Blob createBlob() throws SQLException {
    try {
      return delegate.createBlob();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public NClob createNClob() throws SQLException {
    try {
      return delegate.createNClob();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    try {
      return delegate.createSQLXML();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isValid(final int timeout) throws SQLException {
    try {
      return delegate.isValid(timeout);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
    try {
      flags = defaults.setClientInfo(delegate, flags, name, value);
    } catch (final SQLClientInfoException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setClientInfo(final Properties properties) throws SQLClientInfoException {
    try {
      flags = defaults.setClientInfo(delegate, flags, properties);
    } catch (final SQLClientInfoException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getClientInfo(final String name) throws SQLException {
    try {
      return delegate.getClientInfo(name);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    try {
      return delegate.getClientInfo();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
    try {
      return delegate.createArrayOf(typeName, elements);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
    try {
      return delegate.createStruct(typeName, attributes);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setSchema(final String schema) throws SQLException {
    try {
      flags = defaults.setSchema(delegate, flags, schema);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getSchema() throws SQLException {
    try {
      return delegate.getSchema();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void abort(final Executor executor) throws SQLException {
    try {
      delegate.abort(executor);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void setNetworkTimeout(final Executor executor, final int milliseconds)
      throws SQLException {
    try {
      flags = defaults.setNetworkTimeout(delegate, flags, executor, milliseconds);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    try {
      return delegate.getNetworkTimeout();
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
