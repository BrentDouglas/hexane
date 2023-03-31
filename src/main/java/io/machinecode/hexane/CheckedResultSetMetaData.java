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

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
final class CheckedResultSetMetaData implements ResultSetMetaData {
  private final Terminal xa;
  private final ResultSetMetaData delegate;

  CheckedResultSetMetaData(final Terminal xa, final ResultSetMetaData delegate) {
    this.xa = xa;
    this.delegate = delegate;
  }

  @Override
  public int getColumnCount() throws SQLException {
    try {
      return delegate.getColumnCount();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isAutoIncrement(final int column) throws SQLException {
    try {
      return delegate.isAutoIncrement(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isCaseSensitive(final int column) throws SQLException {
    try {
      return delegate.isCaseSensitive(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isSearchable(final int column) throws SQLException {
    try {
      return delegate.isSearchable(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isCurrency(final int column) throws SQLException {
    try {
      return delegate.isCurrency(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int isNullable(final int column) throws SQLException {
    try {
      return delegate.isNullable(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isSigned(final int column) throws SQLException {
    try {
      return delegate.isSigned(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getColumnDisplaySize(final int column) throws SQLException {
    try {
      return delegate.getColumnDisplaySize(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getColumnLabel(final int column) throws SQLException {
    try {
      return delegate.getColumnLabel(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getColumnName(final int column) throws SQLException {
    try {
      return delegate.getColumnName(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getSchemaName(final int column) throws SQLException {
    try {
      return delegate.getSchemaName(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getPrecision(final int column) throws SQLException {
    try {
      return delegate.getPrecision(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getScale(final int column) throws SQLException {
    try {
      return delegate.getScale(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getTableName(final int column) throws SQLException {
    try {
      return delegate.getTableName(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getCatalogName(final int column) throws SQLException {
    try {
      return delegate.getCatalogName(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getColumnType(final int column) throws SQLException {
    try {
      return delegate.getColumnType(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getColumnTypeName(final int column) throws SQLException {
    try {
      return delegate.getColumnTypeName(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isReadOnly(final int column) throws SQLException {
    try {
      return delegate.isReadOnly(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isWritable(final int column) throws SQLException {
    try {
      return delegate.isWritable(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isDefinitelyWritable(final int column) throws SQLException {
    try {
      return delegate.isDefinitelyWritable(column);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getColumnClassName(final int column) throws SQLException {
    try {
      return delegate.getColumnClassName(column);
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
