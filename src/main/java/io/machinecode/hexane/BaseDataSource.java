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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Wrapper;
import java.util.logging.Logger;
import javax.sql.CommonDataSource;
import javax.sql.DataSource;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
abstract class BaseDataSource<C, P extends BasePool<C>, D extends CommonDataSource>
    implements CommonDataSource, DataSource, AutoCloseable {
  P pool;
  final D dataSource;

  BaseDataSource(final P pool, final D dataSource) {
    this.pool = pool;
    this.dataSource = dataSource;
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    try {
      return this.dataSource.getLogWriter();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }

  @Override
  public void setLogWriter(final PrintWriter writer) throws SQLException {
    try {
      this.dataSource.setLogWriter(writer);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }

  @Override
  public void setLoginTimeout(final int timeout) throws SQLException {
    try {
      this.dataSource.setLoginTimeout(timeout);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    try {
      return this.dataSource.getLoginTimeout();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    try {
      return this.dataSource.getParentLogger();
    } catch (final SQLFeatureNotSupportedException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }

  @Override
  public void close() throws SQLException {
    this.pool.close();
  }

  @Override
  public Connection getConnection(final String username, final String password)
      throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public <T> T unwrap(final Class<T> iface) throws SQLException {
    try {
      return dataSource instanceof Wrapper ? Util.unwrap(iface, this, (Wrapper) dataSource) : null;
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }

  @Override
  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    try {
      return dataSource instanceof Wrapper && Util.isWrapperFor(iface, this, (Wrapper) dataSource);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }
}
