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
class UncheckedCallableStatement extends UncheckedPreparedStatement<CallableStatement>
    implements CallableStatement {

  UncheckedCallableStatement(
      final Terminal xa, final Connection conn, final CallableStatement delegate)
      throws SQLException {
    super(xa, conn, delegate);
  }

  @Override
  public void registerOutParameter(final int parameterIndex, final int sqlType)
      throws SQLException {
    delegate.registerOutParameter(parameterIndex, sqlType);
  }

  @Override
  public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale)
      throws SQLException {
    delegate.registerOutParameter(parameterIndex, sqlType, scale);
  }

  @Override
  public boolean wasNull() throws SQLException {
    return delegate.wasNull();
  }

  @Override
  public String getString(final int parameterIndex) throws SQLException {
    return delegate.getString(parameterIndex);
  }

  @Override
  public boolean getBoolean(final int parameterIndex) throws SQLException {
    return delegate.getBoolean(parameterIndex);
  }

  @Override
  public byte getByte(final int parameterIndex) throws SQLException {
    return delegate.getByte(parameterIndex);
  }

  @Override
  public short getShort(final int parameterIndex) throws SQLException {
    return delegate.getShort(parameterIndex);
  }

  @Override
  public int getInt(final int parameterIndex) throws SQLException {
    return delegate.getInt(parameterIndex);
  }

  @Override
  public long getLong(final int parameterIndex) throws SQLException {
    return delegate.getLong(parameterIndex);
  }

  @Override
  public float getFloat(final int parameterIndex) throws SQLException {
    return delegate.getFloat(parameterIndex);
  }

  @Override
  public double getDouble(final int parameterIndex) throws SQLException {
    return delegate.getDouble(parameterIndex);
  }

  @Override
  @Deprecated
  public BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException {
    return delegate.getBigDecimal(parameterIndex, scale);
  }

  @Override
  public byte[] getBytes(final int parameterIndex) throws SQLException {
    return delegate.getBytes(parameterIndex);
  }

  @Override
  public Date getDate(final int parameterIndex) throws SQLException {
    return delegate.getDate(parameterIndex);
  }

  @Override
  public Time getTime(final int parameterIndex) throws SQLException {
    return delegate.getTime(parameterIndex);
  }

  @Override
  public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
    return delegate.getTimestamp(parameterIndex);
  }

  @Override
  public Object getObject(final int parameterIndex) throws SQLException {
    return delegate.getObject(parameterIndex);
  }

  @Override
  public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
    return delegate.getBigDecimal(parameterIndex);
  }

  @Override
  public Object getObject(final int parameterIndex, final Map<String, Class<?>> map)
      throws SQLException {
    return delegate.getObject(parameterIndex, map);
  }

  @Override
  public Ref getRef(final int parameterIndex) throws SQLException {
    return delegate.getRef(parameterIndex);
  }

  @Override
  public Blob getBlob(final int parameterIndex) throws SQLException {
    return delegate.getBlob(parameterIndex);
  }

  @Override
  public Clob getClob(final int parameterIndex) throws SQLException {
    return delegate.getClob(parameterIndex);
  }

  @Override
  public Array getArray(final int parameterIndex) throws SQLException {
    return delegate.getArray(parameterIndex);
  }

  @Override
  public Date getDate(final int parameterIndex, final Calendar cal) throws SQLException {
    return delegate.getDate(parameterIndex, cal);
  }

  @Override
  public Time getTime(final int parameterIndex, final Calendar cal) throws SQLException {
    return delegate.getTime(parameterIndex, cal);
  }

  @Override
  public Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException {
    return delegate.getTimestamp(parameterIndex, cal);
  }

  @Override
  public void registerOutParameter(
      final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
    delegate.registerOutParameter(parameterIndex, sqlType, typeName);
  }

  @Override
  public void registerOutParameter(final String parameterName, final int sqlType)
      throws SQLException {
    delegate.registerOutParameter(parameterName, sqlType);
  }

  @Override
  public void registerOutParameter(final String parameterName, final int sqlType, final int scale)
      throws SQLException {
    delegate.registerOutParameter(parameterName, sqlType, scale);
  }

  @Override
  public void registerOutParameter(
      final String parameterName, final int sqlType, final String typeName) throws SQLException {
    delegate.registerOutParameter(parameterName, sqlType, typeName);
  }

  @Override
  public URL getURL(final int parameterIndex) throws SQLException {
    return delegate.getURL(parameterIndex);
  }

  @Override
  public void setURL(final String parameterName, final URL val) throws SQLException {
    delegate.setURL(parameterName, val);
  }

  @Override
  public void setNull(final String parameterName, final int sqlType) throws SQLException {
    delegate.setNull(parameterName, sqlType);
  }

  @Override
  public void setBoolean(final String parameterName, final boolean x) throws SQLException {
    delegate.setBoolean(parameterName, x);
  }

  @Override
  public void setByte(final String parameterName, final byte x) throws SQLException {
    delegate.setByte(parameterName, x);
  }

  @Override
  public void setShort(final String parameterName, final short x) throws SQLException {
    delegate.setShort(parameterName, x);
  }

  @Override
  public void setInt(final String parameterName, final int x) throws SQLException {
    delegate.setInt(parameterName, x);
  }

  @Override
  public void setLong(final String parameterName, final long x) throws SQLException {
    delegate.setLong(parameterName, x);
  }

  @Override
  public void setFloat(final String parameterName, final float x) throws SQLException {
    delegate.setFloat(parameterName, x);
  }

  @Override
  public void setDouble(final String parameterName, final double x) throws SQLException {
    delegate.setDouble(parameterName, x);
  }

  @Override
  public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
    delegate.setBigDecimal(parameterName, x);
  }

  @Override
  public void setString(final String parameterName, final String x) throws SQLException {
    delegate.setString(parameterName, x);
  }

  @Override
  public void setBytes(final String parameterName, final byte[] x) throws SQLException {
    delegate.setBytes(parameterName, x);
  }

  @Override
  public void setDate(final String parameterName, final Date x) throws SQLException {
    delegate.setDate(parameterName, x);
  }

  @Override
  public void setTime(final String parameterName, final Time x) throws SQLException {
    delegate.setTime(parameterName, x);
  }

  @Override
  public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
    delegate.setTimestamp(parameterName, x);
  }

  @Override
  public void setAsciiStream(final String parameterName, final InputStream x, final int length)
      throws SQLException {
    delegate.setAsciiStream(parameterName, x, length);
  }

  @Override
  public void setBinaryStream(final String parameterName, final InputStream x, final int length)
      throws SQLException {
    delegate.setBinaryStream(parameterName, x, length);
  }

  @Override
  public void setObject(
      final String parameterName, final Object x, final int targetSqlType, final int scale)
      throws SQLException {
    delegate.setObject(parameterName, x, targetSqlType, scale);
  }

  @Override
  public void setObject(final String parameterName, final Object x, final int targetSqlType)
      throws SQLException {
    delegate.setObject(parameterName, x, targetSqlType);
  }

  @Override
  public void setObject(final String parameterName, final Object x) throws SQLException {
    delegate.setObject(parameterName, x);
  }

  @Override
  public void setCharacterStream(final String parameterName, final Reader reader, final int length)
      throws SQLException {
    delegate.setCharacterStream(parameterName, reader, length);
  }

  @Override
  public void setDate(final String parameterName, final Date x, final Calendar cal)
      throws SQLException {
    delegate.setDate(parameterName, x, cal);
  }

  @Override
  public void setTime(final String parameterName, final Time x, final Calendar cal)
      throws SQLException {
    delegate.setTime(parameterName, x, cal);
  }

  @Override
  public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal)
      throws SQLException {
    delegate.setTimestamp(parameterName, x, cal);
  }

  @Override
  public void setNull(final String parameterName, final int sqlType, final String typeName)
      throws SQLException {
    delegate.setNull(parameterName, sqlType, typeName);
  }

  @Override
  public String getString(final String parameterName) throws SQLException {
    return delegate.getString(parameterName);
  }

  @Override
  public boolean getBoolean(final String parameterName) throws SQLException {
    return delegate.getBoolean(parameterName);
  }

  @Override
  public byte getByte(final String parameterName) throws SQLException {
    return delegate.getByte(parameterName);
  }

  @Override
  public short getShort(final String parameterName) throws SQLException {
    return delegate.getShort(parameterName);
  }

  @Override
  public int getInt(final String parameterName) throws SQLException {
    return delegate.getInt(parameterName);
  }

  @Override
  public long getLong(final String parameterName) throws SQLException {
    return delegate.getLong(parameterName);
  }

  @Override
  public float getFloat(final String parameterName) throws SQLException {
    return delegate.getFloat(parameterName);
  }

  @Override
  public double getDouble(final String parameterName) throws SQLException {
    return delegate.getDouble(parameterName);
  }

  @Override
  public byte[] getBytes(final String parameterName) throws SQLException {
    return delegate.getBytes(parameterName);
  }

  @Override
  public Date getDate(final String parameterName) throws SQLException {
    return delegate.getDate(parameterName);
  }

  @Override
  public Time getTime(final String parameterName) throws SQLException {
    return delegate.getTime(parameterName);
  }

  @Override
  public Timestamp getTimestamp(final String parameterName) throws SQLException {
    return delegate.getTimestamp(parameterName);
  }

  @Override
  public Object getObject(final String parameterName) throws SQLException {
    return delegate.getObject(parameterName);
  }

  @Override
  public BigDecimal getBigDecimal(final String parameterName) throws SQLException {
    return delegate.getBigDecimal(parameterName);
  }

  @Override
  public Object getObject(final String parameterName, final Map<String, Class<?>> map)
      throws SQLException {
    return delegate.getObject(parameterName, map);
  }

  @Override
  public Ref getRef(final String parameterName) throws SQLException {
    return delegate.getRef(parameterName);
  }

  @Override
  public Blob getBlob(final String parameterName) throws SQLException {
    return delegate.getBlob(parameterName);
  }

  @Override
  public Clob getClob(final String parameterName) throws SQLException {
    return delegate.getClob(parameterName);
  }

  @Override
  public Array getArray(final String parameterName) throws SQLException {
    return delegate.getArray(parameterName);
  }

  @Override
  public Date getDate(final String parameterName, final Calendar cal) throws SQLException {
    return delegate.getDate(parameterName, cal);
  }

  @Override
  public Time getTime(final String parameterName, final Calendar cal) throws SQLException {
    return delegate.getTime(parameterName, cal);
  }

  @Override
  public Timestamp getTimestamp(final String parameterName, final Calendar cal)
      throws SQLException {
    return delegate.getTimestamp(parameterName, cal);
  }

  @Override
  public URL getURL(final String parameterName) throws SQLException {
    return delegate.getURL(parameterName);
  }

  @Override
  public RowId getRowId(final int parameterIndex) throws SQLException {
    return delegate.getRowId(parameterIndex);
  }

  @Override
  public RowId getRowId(final String parameterName) throws SQLException {
    return delegate.getRowId(parameterName);
  }

  @Override
  public void setRowId(final String parameterName, final RowId x) throws SQLException {
    delegate.setRowId(parameterName, x);
  }

  @Override
  public void setNString(final String parameterName, final String value) throws SQLException {
    delegate.setNString(parameterName, value);
  }

  @Override
  public void setNCharacterStream(final String parameterName, final Reader value, final long length)
      throws SQLException {
    delegate.setNCharacterStream(parameterName, value, length);
  }

  @Override
  public void setNClob(final String parameterName, final NClob value) throws SQLException {
    delegate.setNClob(parameterName, value);
  }

  @Override
  public void setClob(final String parameterName, final Reader reader, final long length)
      throws SQLException {
    delegate.setClob(parameterName, reader, length);
  }

  @Override
  public void setBlob(final String parameterName, final InputStream inputStream, final long length)
      throws SQLException {
    delegate.setBlob(parameterName, inputStream, length);
  }

  @Override
  public void setNClob(final String parameterName, final Reader reader, final long length)
      throws SQLException {
    delegate.setNClob(parameterName, reader, length);
  }

  @Override
  public NClob getNClob(final int parameterIndex) throws SQLException {
    return delegate.getNClob(parameterIndex);
  }

  @Override
  public NClob getNClob(final String parameterName) throws SQLException {
    return delegate.getNClob(parameterName);
  }

  @Override
  public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
    delegate.setSQLXML(parameterName, xmlObject);
  }

  @Override
  public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
    return delegate.getSQLXML(parameterIndex);
  }

  @Override
  public SQLXML getSQLXML(final String parameterName) throws SQLException {
    return delegate.getSQLXML(parameterName);
  }

  @Override
  public String getNString(final int parameterIndex) throws SQLException {
    return delegate.getNString(parameterIndex);
  }

  @Override
  public String getNString(final String parameterName) throws SQLException {
    return delegate.getNString(parameterName);
  }

  @Override
  public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
    return delegate.getNCharacterStream(parameterIndex);
  }

  @Override
  public Reader getNCharacterStream(final String parameterName) throws SQLException {
    return delegate.getNCharacterStream(parameterName);
  }

  @Override
  public Reader getCharacterStream(final int parameterIndex) throws SQLException {
    return delegate.getCharacterStream(parameterIndex);
  }

  @Override
  public Reader getCharacterStream(final String parameterName) throws SQLException {
    return delegate.getCharacterStream(parameterName);
  }

  @Override
  public void setBlob(final String parameterName, final Blob x) throws SQLException {
    delegate.setBlob(parameterName, x);
  }

  @Override
  public void setClob(final String parameterName, final Clob x) throws SQLException {
    delegate.setClob(parameterName, x);
  }

  @Override
  public void setAsciiStream(final String parameterName, final InputStream x, final long length)
      throws SQLException {
    delegate.setAsciiStream(parameterName, x, length);
  }

  @Override
  public void setBinaryStream(final String parameterName, final InputStream x, final long length)
      throws SQLException {
    delegate.setBinaryStream(parameterName, x, length);
  }

  @Override
  public void setCharacterStream(final String parameterName, final Reader reader, final long length)
      throws SQLException {
    delegate.setCharacterStream(parameterName, reader, length);
  }

  @Override
  public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
    delegate.setAsciiStream(parameterName, x);
  }

  @Override
  public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
    delegate.setBinaryStream(parameterName, x);
  }

  @Override
  public void setCharacterStream(final String parameterName, final Reader reader)
      throws SQLException {
    delegate.setCharacterStream(parameterName, reader);
  }

  @Override
  public void setNCharacterStream(final String parameterName, final Reader value)
      throws SQLException {
    delegate.setNCharacterStream(parameterName, value);
  }

  @Override
  public void setClob(final String parameterName, final Reader reader) throws SQLException {
    delegate.setClob(parameterName, reader);
  }

  @Override
  public void setBlob(final String parameterName, final InputStream inputStream)
      throws SQLException {
    delegate.setBlob(parameterName, inputStream);
  }

  @Override
  public void setNClob(final String parameterName, final Reader reader) throws SQLException {
    delegate.setNClob(parameterName, reader);
  }

  @Override
  public <T> T getObject(final int parameterIndex, final Class<T> type) throws SQLException {
    return delegate.getObject(parameterIndex, type);
  }

  @Override
  public <T> T getObject(final String parameterName, final Class<T> type) throws SQLException {
    return delegate.getObject(parameterName, type);
  }

  @Override
  public void setObject(
      final String parameterName,
      final Object x,
      final SQLType targetSqlType,
      final int scaleOrLength)
      throws SQLException {
    delegate.setObject(parameterName, x, targetSqlType, scaleOrLength);
  }

  @Override
  public void setObject(final String parameterName, final Object x, final SQLType targetSqlType)
      throws SQLException {
    delegate.setObject(parameterName, x, targetSqlType);
  }

  @Override
  public void registerOutParameter(final int parameterIndex, final SQLType sqlType)
      throws SQLException {
    delegate.registerOutParameter(parameterIndex, sqlType);
  }

  @Override
  public void registerOutParameter(final int parameterIndex, final SQLType sqlType, final int scale)
      throws SQLException {
    delegate.registerOutParameter(parameterIndex, sqlType, scale);
  }

  @Override
  public void registerOutParameter(
      final int parameterIndex, final SQLType sqlType, final String typeName) throws SQLException {
    delegate.registerOutParameter(parameterIndex, sqlType, typeName);
  }

  @Override
  public void registerOutParameter(final String parameterName, final SQLType sqlType)
      throws SQLException {
    delegate.registerOutParameter(parameterName, sqlType);
  }

  @Override
  public void registerOutParameter(
      final String parameterName, final SQLType sqlType, final int scale) throws SQLException {
    delegate.registerOutParameter(parameterName, sqlType, scale);
  }

  @Override
  public void registerOutParameter(
      final String parameterName, final SQLType sqlType, final String typeName)
      throws SQLException {
    delegate.registerOutParameter(parameterName, sqlType, typeName);
  }
}
