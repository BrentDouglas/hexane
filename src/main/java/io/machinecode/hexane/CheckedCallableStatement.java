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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
class CheckedCallableStatement extends CheckedPreparedStatement<CallableStatement>
    implements CallableStatement {

  CheckedCallableStatement(
      final Terminal xa, final Connection conn, final CallableStatement delegate)
      throws SQLException {
    super(xa, conn, delegate);
  }

  @Override
  public void registerOutParameter(final int parameterIndex, final int sqlType)
      throws SQLException {
    try {
      delegate.registerOutParameter(parameterIndex, sqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale)
      throws SQLException {
    try {
      delegate.registerOutParameter(parameterIndex, sqlType, scale);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public boolean wasNull() throws SQLException {
    try {
      return delegate.wasNull();
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public String getString(final int parameterIndex) throws SQLException {
    try {
      return delegate.getString(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public boolean getBoolean(final int parameterIndex) throws SQLException {
    try {
      return delegate.getBoolean(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public byte getByte(final int parameterIndex) throws SQLException {
    try {
      return delegate.getByte(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public short getShort(final int parameterIndex) throws SQLException {
    try {
      return delegate.getShort(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public int getInt(final int parameterIndex) throws SQLException {
    try {
      return delegate.getInt(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public long getLong(final int parameterIndex) throws SQLException {
    try {
      return delegate.getLong(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public float getFloat(final int parameterIndex) throws SQLException {
    try {
      return delegate.getFloat(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public double getDouble(final int parameterIndex) throws SQLException {
    try {
      return delegate.getDouble(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  @Deprecated
  public BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException {
    try {
      return delegate.getBigDecimal(parameterIndex, scale);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public byte[] getBytes(final int parameterIndex) throws SQLException {
    try {
      return delegate.getBytes(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Date getDate(final int parameterIndex) throws SQLException {
    try {
      return delegate.getDate(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Time getTime(final int parameterIndex) throws SQLException {
    try {
      return delegate.getTime(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
    try {
      return delegate.getTimestamp(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Object getObject(final int parameterIndex) throws SQLException {
    try {
      return delegate.getObject(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
    try {
      return delegate.getBigDecimal(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Object getObject(final int parameterIndex, final Map<String, Class<?>> map)
      throws SQLException {
    try {
      return delegate.getObject(parameterIndex, map);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Ref getRef(final int parameterIndex) throws SQLException {
    try {
      return delegate.getRef(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Blob getBlob(final int parameterIndex) throws SQLException {
    try {
      return delegate.getBlob(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Clob getClob(final int parameterIndex) throws SQLException {
    try {
      return delegate.getClob(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Array getArray(final int parameterIndex) throws SQLException {
    try {
      return delegate.getArray(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Date getDate(final int parameterIndex, final Calendar cal) throws SQLException {
    try {
      return delegate.getDate(parameterIndex, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Time getTime(final int parameterIndex, final Calendar cal) throws SQLException {
    try {
      return delegate.getTime(parameterIndex, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException {
    try {
      return delegate.getTimestamp(parameterIndex, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(
      final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
    try {
      delegate.registerOutParameter(parameterIndex, sqlType, typeName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(final String parameterName, final int sqlType)
      throws SQLException {
    try {
      delegate.registerOutParameter(parameterName, sqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(final String parameterName, final int sqlType, final int scale)
      throws SQLException {
    try {
      delegate.registerOutParameter(parameterName, sqlType, scale);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(
      final String parameterName, final int sqlType, final String typeName) throws SQLException {
    try {
      delegate.registerOutParameter(parameterName, sqlType, typeName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public URL getURL(final int parameterIndex) throws SQLException {
    try {
      return delegate.getURL(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setURL(final String parameterName, final URL val) throws SQLException {
    try {
      delegate.setURL(parameterName, val);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNull(final String parameterName, final int sqlType) throws SQLException {
    try {
      delegate.setNull(parameterName, sqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBoolean(final String parameterName, final boolean x) throws SQLException {
    try {
      delegate.setBoolean(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setByte(final String parameterName, final byte x) throws SQLException {
    try {
      delegate.setByte(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setShort(final String parameterName, final short x) throws SQLException {
    try {
      delegate.setShort(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setInt(final String parameterName, final int x) throws SQLException {
    try {
      delegate.setInt(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setLong(final String parameterName, final long x) throws SQLException {
    try {
      delegate.setLong(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setFloat(final String parameterName, final float x) throws SQLException {
    try {
      delegate.setFloat(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setDouble(final String parameterName, final double x) throws SQLException {
    try {
      delegate.setDouble(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
    try {
      delegate.setBigDecimal(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setString(final String parameterName, final String x) throws SQLException {
    try {
      delegate.setString(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBytes(final String parameterName, final byte[] x) throws SQLException {
    try {
      delegate.setBytes(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setDate(final String parameterName, final Date x) throws SQLException {
    try {
      delegate.setDate(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setTime(final String parameterName, final Time x) throws SQLException {
    try {
      delegate.setTime(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
    try {
      delegate.setTimestamp(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setAsciiStream(final String parameterName, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.setAsciiStream(parameterName, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBinaryStream(final String parameterName, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.setBinaryStream(parameterName, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(
      final String parameterName, final Object x, final int targetSqlType, final int scale)
      throws SQLException {
    try {
      delegate.setObject(parameterName, x, targetSqlType, scale);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(final String parameterName, final Object x, final int targetSqlType)
      throws SQLException {
    try {
      delegate.setObject(parameterName, x, targetSqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(final String parameterName, final Object x) throws SQLException {
    try {
      delegate.setObject(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setCharacterStream(final String parameterName, final Reader reader, final int length)
      throws SQLException {
    try {
      delegate.setCharacterStream(parameterName, reader, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setDate(final String parameterName, final Date x, final Calendar cal)
      throws SQLException {
    try {
      delegate.setDate(parameterName, x, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setTime(final String parameterName, final Time x, final Calendar cal)
      throws SQLException {
    try {
      delegate.setTime(parameterName, x, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal)
      throws SQLException {
    try {
      delegate.setTimestamp(parameterName, x, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNull(final String parameterName, final int sqlType, final String typeName)
      throws SQLException {
    try {
      delegate.setNull(parameterName, sqlType, typeName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public String getString(final String parameterName) throws SQLException {
    try {
      return delegate.getString(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public boolean getBoolean(final String parameterName) throws SQLException {
    try {
      return delegate.getBoolean(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public byte getByte(final String parameterName) throws SQLException {
    try {
      return delegate.getByte(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public short getShort(final String parameterName) throws SQLException {
    try {
      return delegate.getShort(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public int getInt(final String parameterName) throws SQLException {
    try {
      return delegate.getInt(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public long getLong(final String parameterName) throws SQLException {
    try {
      return delegate.getLong(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public float getFloat(final String parameterName) throws SQLException {
    try {
      return delegate.getFloat(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public double getDouble(final String parameterName) throws SQLException {
    try {
      return delegate.getDouble(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public byte[] getBytes(final String parameterName) throws SQLException {
    try {
      return delegate.getBytes(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Date getDate(final String parameterName) throws SQLException {
    try {
      return delegate.getDate(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Time getTime(final String parameterName) throws SQLException {
    try {
      return delegate.getTime(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Timestamp getTimestamp(final String parameterName) throws SQLException {
    try {
      return delegate.getTimestamp(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Object getObject(final String parameterName) throws SQLException {
    try {
      return delegate.getObject(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public BigDecimal getBigDecimal(final String parameterName) throws SQLException {
    try {
      return delegate.getBigDecimal(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Object getObject(final String parameterName, final Map<String, Class<?>> map)
      throws SQLException {
    try {
      return delegate.getObject(parameterName, map);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Ref getRef(final String parameterName) throws SQLException {
    try {
      return delegate.getRef(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Blob getBlob(final String parameterName) throws SQLException {
    try {
      return delegate.getBlob(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Clob getClob(final String parameterName) throws SQLException {
    try {
      return delegate.getClob(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Array getArray(final String parameterName) throws SQLException {
    try {
      return delegate.getArray(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Date getDate(final String parameterName, final Calendar cal) throws SQLException {
    try {
      return delegate.getDate(parameterName, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Time getTime(final String parameterName, final Calendar cal) throws SQLException {
    try {
      return delegate.getTime(parameterName, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Timestamp getTimestamp(final String parameterName, final Calendar cal)
      throws SQLException {
    try {
      return delegate.getTimestamp(parameterName, cal);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public URL getURL(final String parameterName) throws SQLException {
    try {
      return delegate.getURL(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public RowId getRowId(final int parameterIndex) throws SQLException {
    try {
      return delegate.getRowId(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public RowId getRowId(final String parameterName) throws SQLException {
    try {
      return delegate.getRowId(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setRowId(final String parameterName, final RowId x) throws SQLException {
    try {
      delegate.setRowId(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNString(final String parameterName, final String value) throws SQLException {
    try {
      delegate.setNString(parameterName, value);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNCharacterStream(final String parameterName, final Reader value, final long length)
      throws SQLException {
    try {
      delegate.setNCharacterStream(parameterName, value, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNClob(final String parameterName, final NClob value) throws SQLException {
    try {
      delegate.setNClob(parameterName, value);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setClob(final String parameterName, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.setClob(parameterName, reader, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBlob(final String parameterName, final InputStream inputStream, final long length)
      throws SQLException {
    try {
      delegate.setBlob(parameterName, inputStream, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNClob(final String parameterName, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.setNClob(parameterName, reader, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public NClob getNClob(final int parameterIndex) throws SQLException {
    try {
      return delegate.getNClob(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public NClob getNClob(final String parameterName) throws SQLException {
    try {
      return delegate.getNClob(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
    try {
      delegate.setSQLXML(parameterName, xmlObject);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
    try {
      return delegate.getSQLXML(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public SQLXML getSQLXML(final String parameterName) throws SQLException {
    try {
      return delegate.getSQLXML(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public String getNString(final int parameterIndex) throws SQLException {
    try {
      return delegate.getNString(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public String getNString(final String parameterName) throws SQLException {
    try {
      return delegate.getNString(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
    try {
      return delegate.getNCharacterStream(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Reader getNCharacterStream(final String parameterName) throws SQLException {
    try {
      return delegate.getNCharacterStream(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Reader getCharacterStream(final int parameterIndex) throws SQLException {
    try {
      return delegate.getCharacterStream(parameterIndex);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public Reader getCharacterStream(final String parameterName) throws SQLException {
    try {
      return delegate.getCharacterStream(parameterName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBlob(final String parameterName, final Blob x) throws SQLException {
    try {
      delegate.setBlob(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setClob(final String parameterName, final Clob x) throws SQLException {
    try {
      delegate.setClob(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setAsciiStream(final String parameterName, final InputStream x, final long length)
      throws SQLException {
    try {
      delegate.setAsciiStream(parameterName, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBinaryStream(final String parameterName, final InputStream x, final long length)
      throws SQLException {
    try {
      delegate.setBinaryStream(parameterName, x, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setCharacterStream(final String parameterName, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.setCharacterStream(parameterName, reader, length);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
    try {
      delegate.setAsciiStream(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
    try {
      delegate.setBinaryStream(parameterName, x);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setCharacterStream(final String parameterName, final Reader reader)
      throws SQLException {
    try {
      delegate.setCharacterStream(parameterName, reader);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNCharacterStream(final String parameterName, final Reader value)
      throws SQLException {
    try {
      delegate.setNCharacterStream(parameterName, value);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setClob(final String parameterName, final Reader reader) throws SQLException {
    try {
      delegate.setClob(parameterName, reader);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setBlob(final String parameterName, final InputStream inputStream)
      throws SQLException {
    try {
      delegate.setBlob(parameterName, inputStream);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setNClob(final String parameterName, final Reader reader) throws SQLException {
    try {
      delegate.setNClob(parameterName, reader);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public <T> T getObject(final int parameterIndex, final Class<T> type) throws SQLException {
    try {
      return delegate.getObject(parameterIndex, type);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public <T> T getObject(final String parameterName, final Class<T> type) throws SQLException {
    try {
      return delegate.getObject(parameterName, type);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(
      final String parameterName,
      final Object x,
      final SQLType targetSqlType,
      final int scaleOrLength)
      throws SQLException {
    try {
      delegate.setObject(parameterName, x, targetSqlType, scaleOrLength);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void setObject(final String parameterName, final Object x, final SQLType targetSqlType)
      throws SQLException {
    try {
      delegate.setObject(parameterName, x, targetSqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(final int parameterIndex, final SQLType sqlType)
      throws SQLException {
    try {
      delegate.registerOutParameter(parameterIndex, sqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(final int parameterIndex, final SQLType sqlType, final int scale)
      throws SQLException {
    try {
      delegate.registerOutParameter(parameterIndex, sqlType, scale);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(
      final int parameterIndex, final SQLType sqlType, final String typeName) throws SQLException {
    try {
      delegate.registerOutParameter(parameterIndex, sqlType, typeName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(final String parameterName, final SQLType sqlType)
      throws SQLException {
    try {
      delegate.registerOutParameter(parameterName, sqlType);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(
      final String parameterName, final SQLType sqlType, final int scale) throws SQLException {
    try {
      delegate.registerOutParameter(parameterName, sqlType, scale);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }

  @Override
  public void registerOutParameter(
      final String parameterName, final SQLType sqlType, final String typeName)
      throws SQLException {
    try {
      delegate.registerOutParameter(parameterName, sqlType, typeName);
    } catch (final SQLException e) {
      throw Util.handleStatementFatalSQL(xa, this.delegate, e);
    }
  }
}
