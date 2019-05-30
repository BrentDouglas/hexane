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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
class CheckedPreparedStatement<S extends PreparedStatement> extends CheckedStatement<S>
    implements PreparedStatement {

  CheckedPreparedStatement(final Terminal xa, final Connection conn, final S delegate)
      throws SQLException {
    super(xa, conn, delegate);
    xa.registerChecked(this, delegate);
  }

  @Override
  public ResultSet executeQuery() throws SQLException {
    try {
      return new CheckedResultSet(xa, this, delegate.executeQuery());
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public int executeUpdate() throws SQLException {
    try {
      return delegate.executeUpdate();
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
    try {
      delegate.setNull(parameterIndex, sqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
    try {
      delegate.setBoolean(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setByte(final int parameterIndex, final byte x) throws SQLException {
    try {
      delegate.setByte(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setShort(final int parameterIndex, final short x) throws SQLException {
    try {
      delegate.setShort(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setInt(final int parameterIndex, final int x) throws SQLException {
    try {
      delegate.setInt(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setLong(final int parameterIndex, final long x) throws SQLException {
    try {
      delegate.setLong(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setFloat(final int parameterIndex, final float x) throws SQLException {
    try {
      delegate.setFloat(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setDouble(final int parameterIndex, final double x) throws SQLException {
    try {
      delegate.setDouble(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
    try {
      delegate.setBigDecimal(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setString(final int parameterIndex, final String x) throws SQLException {
    try {
      delegate.setString(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
    try {
      delegate.setBytes(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setDate(final int parameterIndex, final Date x) throws SQLException {
    try {
      delegate.setDate(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setTime(final int parameterIndex, final Time x) throws SQLException {
    try {
      delegate.setTime(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
    try {
      delegate.setTimestamp(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setAsciiStream(final int parameterIndex, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.setAsciiStream(parameterIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  @Deprecated
  public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.setUnicodeStream(parameterIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBinaryStream(final int parameterIndex, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.setBinaryStream(parameterIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void clearParameters() throws SQLException {
    try {
      delegate.clearParameters();
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(final int parameterIndex, final Object x, final int targetSqlType)
      throws SQLException {
    try {
      delegate.setObject(parameterIndex, x, targetSqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(final int parameterIndex, final Object x) throws SQLException {
    try {
      delegate.setObject(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public boolean execute() throws SQLException {
    try {
      return delegate.execute();
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void addBatch() throws SQLException {
    try {
      delegate.addBatch();
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setCharacterStream(final int parameterIndex, final Reader reader, final int length)
      throws SQLException {
    try {
      delegate.setCharacterStream(parameterIndex, reader, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setRef(final int parameterIndex, final Ref x) throws SQLException {
    try {
      delegate.setRef(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
    try {
      delegate.setBlob(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setClob(final int parameterIndex, final Clob x) throws SQLException {
    try {
      delegate.setClob(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setArray(final int parameterIndex, final Array x) throws SQLException {
    try {
      delegate.setArray(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    try {
      return delegate.getMetaData();
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setDate(final int parameterIndex, final Date x, final Calendar cal)
      throws SQLException {
    try {
      delegate.setDate(parameterIndex, x, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setTime(final int parameterIndex, final Time x, final Calendar cal)
      throws SQLException {
    try {
      delegate.setTime(parameterIndex, x, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal)
      throws SQLException {
    try {
      delegate.setTimestamp(parameterIndex, x, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNull(final int parameterIndex, final int sqlType, final String typeName)
      throws SQLException {
    try {
      delegate.setNull(parameterIndex, sqlType, typeName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setURL(final int parameterIndex, final URL x) throws SQLException {
    try {
      delegate.setURL(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public ParameterMetaData getParameterMetaData() throws SQLException {
    try {
      return delegate.getParameterMetaData();
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
    try {
      delegate.setRowId(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNString(final int parameterIndex, final String value) throws SQLException {
    try {
      delegate.setNString(parameterIndex, value);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNCharacterStream(final int parameterIndex, final Reader value, final long length)
      throws SQLException {
    try {
      delegate.setNCharacterStream(parameterIndex, value, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
    try {
      delegate.setNClob(parameterIndex, value);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setClob(final int parameterIndex, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.setClob(parameterIndex, reader, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBlob(final int parameterIndex, final InputStream inputStream, final long length)
      throws SQLException {
    try {
      delegate.setBlob(parameterIndex, inputStream, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNClob(final int parameterIndex, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.setNClob(parameterIndex, reader, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
    try {
      delegate.setSQLXML(parameterIndex, xmlObject);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(
      final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength)
      throws SQLException {
    try {
      delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setAsciiStream(final int parameterIndex, final InputStream x, final long length)
      throws SQLException {
    try {
      delegate.setAsciiStream(parameterIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBinaryStream(final int parameterIndex, final InputStream x, final long length)
      throws SQLException {
    try {
      delegate.setBinaryStream(parameterIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setCharacterStream(final int parameterIndex, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.setCharacterStream(parameterIndex, reader, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
    try {
      delegate.setAsciiStream(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
    try {
      delegate.setBinaryStream(parameterIndex, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setCharacterStream(final int parameterIndex, final Reader reader)
      throws SQLException {
    try {
      delegate.setCharacterStream(parameterIndex, reader);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNCharacterStream(final int parameterIndex, final Reader value)
      throws SQLException {
    try {
      delegate.setNCharacterStream(parameterIndex, value);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
    try {
      delegate.setClob(parameterIndex, reader);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
    try {
      delegate.setBlob(parameterIndex, inputStream);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
    try {
      delegate.setNClob(parameterIndex, reader);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(
      final int parameterIndex,
      final Object x,
      final SQLType targetSqlType,
      final int scaleOrLength)
      throws SQLException {
    try {
      delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(final int parameterIndex, final Object x, final SQLType targetSqlType)
      throws SQLException {
    try {
      delegate.setObject(parameterIndex, x, targetSqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public long executeLargeUpdate() throws SQLException {
    try {
      return delegate.executeLargeUpdate();
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }
}
