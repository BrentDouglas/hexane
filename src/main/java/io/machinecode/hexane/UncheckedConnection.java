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

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
final class UncheckedConnection implements Connection {
  private final Terminal xa;
  private final Pooled<?> val;
  private Connection delegate;
  private final Defaults defaults;
  private final StatementCache cache;
  private int flags;

  UncheckedConnection(
      final Terminal xa, final Pooled<?> val, final Connection delegate, final Defaults defaults) {
    this.xa = xa;
    this.val = val;
    this.delegate = delegate;
    this.defaults = defaults;
    this.cache = val.getCache();
  }

  @Override
  public Statement createStatement() throws SQLException {
    return new UncheckedStatement<>(xa, this, delegate.createStatement());
  }

  @Override
  public PreparedStatement prepareStatement(final String sql) throws SQLException {
    return new UncheckedPreparedStatement<>(xa, this, cache.prepareStatement(delegate, sql));
  }

  @Override
  public CallableStatement prepareCall(final String sql) throws SQLException {
    return new UncheckedCallableStatement(xa, this, cache.prepareCall(delegate, sql));
  }

  @Override
  public String nativeSQL(final String sql) throws SQLException {
    return delegate.nativeSQL(sql);
  }

  @Override
  public void setAutoCommit(final boolean autoCommit) throws SQLException {
    flags = defaults.setAutoCommit(delegate, flags, autoCommit);
  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    return delegate.getAutoCommit();
  }

  @Override
  public void commit() throws SQLException {
    delegate.commit();
  }

  @Override
  public void rollback() throws SQLException {
    delegate.rollback();
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
    return delegate.isClosed();
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    return delegate.getMetaData();
  }

  @Override
  public void setReadOnly(final boolean readOnly) throws SQLException {
    flags = defaults.setReadOnly(delegate, flags, readOnly);
  }

  @Override
  public boolean isReadOnly() throws SQLException {
    return delegate.isReadOnly();
  }

  @Override
  public void setCatalog(final String catalog) throws SQLException {
    flags = defaults.setCatalog(delegate, flags, catalog);
  }

  @Override
  public String getCatalog() throws SQLException {
    return delegate.getCatalog();
  }

  @Override
  public void setTransactionIsolation(final int level) throws SQLException {
    flags = defaults.setTransactionIsolation(delegate, flags, level);
  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    return delegate.getTransactionIsolation();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return delegate.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    delegate.clearWarnings();
  }

  @Override
  public Statement createStatement(final int resultSetType, final int resultSetConcurrency)
      throws SQLException {
    return new UncheckedStatement<>(
        xa, this, delegate.createStatement(resultSetType, resultSetConcurrency));
  }

  @Override
  public PreparedStatement prepareStatement(
      final String sql, final int resultSetType, final int resultSetConcurrency)
      throws SQLException {
    return new UncheckedPreparedStatement<>(
        xa, this, cache.prepareStatement(delegate, sql, resultSetType, resultSetConcurrency));
  }

  @Override
  public CallableStatement prepareCall(
      final String sql, final int resultSetType, final int resultSetConcurrency)
      throws SQLException {
    return new UncheckedCallableStatement(
        xa, this, cache.prepareCall(delegate, sql, resultSetType, resultSetConcurrency));
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return delegate.getTypeMap();
  }

  @Override
  public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
    flags = defaults.setTypeMap(delegate, flags, map);
  }

  @Override
  public void setHoldability(final int holdability) throws SQLException {
    flags = defaults.setHoldability(delegate, flags, holdability);
  }

  @Override
  public int getHoldability() throws SQLException {
    return delegate.getHoldability();
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    return delegate.setSavepoint();
  }

  @Override
  public Savepoint setSavepoint(final String name) throws SQLException {
    return delegate.setSavepoint(name);
  }

  @Override
  public void rollback(final Savepoint savepoint) throws SQLException {
    delegate.rollback(savepoint);
  }

  @Override
  public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
    delegate.releaseSavepoint(savepoint);
  }

  @Override
  public Statement createStatement(
      final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)
      throws SQLException {
    return new UncheckedStatement<>(
        xa,
        this,
        delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public PreparedStatement prepareStatement(
      final String sql,
      final int resultSetType,
      final int resultSetConcurrency,
      final int resultSetHoldability)
      throws SQLException {
    return new UncheckedPreparedStatement<>(
        xa,
        this,
        cache.prepareStatement(
            delegate, sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public CallableStatement prepareCall(
      final String sql,
      final int resultSetType,
      final int resultSetConcurrency,
      final int resultSetHoldability)
      throws SQLException {
    return new UncheckedCallableStatement(
        xa,
        this,
        cache.prepareCall(
            delegate, sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys)
      throws SQLException {
    return new UncheckedPreparedStatement<>(
        xa, this, cache.prepareStatement(delegate, sql, autoGeneratedKeys));
  }

  @Override
  public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes)
      throws SQLException {
    return new UncheckedPreparedStatement<>(
        xa, this, cache.prepareStatement(delegate, sql, columnIndexes));
  }

  @Override
  public PreparedStatement prepareStatement(final String sql, final String[] columnNames)
      throws SQLException {
    return new UncheckedPreparedStatement<>(
        xa, this, cache.prepareStatement(delegate, sql, columnNames));
  }

  @Override
  public Clob createClob() throws SQLException {
    return delegate.createClob();
  }

  @Override
  public Blob createBlob() throws SQLException {
    return delegate.createBlob();
  }

  @Override
  public NClob createNClob() throws SQLException {
    return delegate.createNClob();
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    return delegate.createSQLXML();
  }

  @Override
  public boolean isValid(final int timeout) throws SQLException {
    return delegate.isValid(timeout);
  }

  @Override
  public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
    flags = defaults.setClientInfo(delegate, flags, name, value);
  }

  @Override
  public void setClientInfo(final Properties properties) throws SQLClientInfoException {
    flags = defaults.setClientInfo(delegate, flags, properties);
  }

  @Override
  public String getClientInfo(final String name) throws SQLException {
    return delegate.getClientInfo(name);
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    return delegate.getClientInfo();
  }

  @Override
  public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
    return delegate.createArrayOf(typeName, elements);
  }

  @Override
  public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
    return delegate.createStruct(typeName, attributes);
  }

  @Override
  public void setSchema(final String schema) throws SQLException {
    flags = defaults.setSchema(delegate, flags, schema);
  }

  @Override
  public String getSchema() throws SQLException {
    return delegate.getSchema();
  }

  @Override
  public void abort(final Executor executor) throws SQLException {
    delegate.abort(executor);
  }

  @Override
  public void setNetworkTimeout(final Executor executor, final int milliseconds)
      throws SQLException {
    flags = defaults.setNetworkTimeout(delegate, flags, executor, milliseconds);
  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    return delegate.getNetworkTimeout();
  }

  @Override
  public <T> T unwrap(final Class<T> iface) throws SQLException {
    return Util.unwrap(iface, this, delegate);
  }

  @Override
  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    return Util.isWrapperFor(iface, this, delegate);
  }
}
