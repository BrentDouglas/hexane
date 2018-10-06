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
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class CheckedResultSet implements ResultSet {
  private final Terminal xa;
  private final CheckedStatement<?> statement;
  private final ResultSet delegate;

  CheckedResultSet(final Terminal xa, final CheckedStatement<?> statement, final ResultSet delegate)
      throws SQLException {
    this.xa = xa;
    this.statement = statement;
    this.delegate = delegate;
    xa.enlist(this);
  }

  @Override
  public boolean next() throws SQLException {
    try {
      return delegate.next();
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
  public boolean wasNull() throws SQLException {
    try {
      return delegate.wasNull();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getString(final int columnIndex) throws SQLException {
    try {
      return delegate.getString(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean getBoolean(final int columnIndex) throws SQLException {
    try {
      return delegate.getBoolean(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public byte getByte(final int columnIndex) throws SQLException {
    try {
      return delegate.getByte(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public short getShort(final int columnIndex) throws SQLException {
    try {
      return delegate.getShort(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getInt(final int columnIndex) throws SQLException {
    try {
      return delegate.getInt(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long getLong(final int columnIndex) throws SQLException {
    try {
      return delegate.getLong(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public float getFloat(final int columnIndex) throws SQLException {
    try {
      return delegate.getFloat(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public double getDouble(final int columnIndex) throws SQLException {
    try {
      return delegate.getDouble(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  @Deprecated
  public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException {
    try {
      return delegate.getBigDecimal(columnIndex, scale);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public byte[] getBytes(final int columnIndex) throws SQLException {
    try {
      return delegate.getBytes(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Date getDate(final int columnIndex) throws SQLException {
    try {
      return delegate.getDate(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Time getTime(final int columnIndex) throws SQLException {
    try {
      return delegate.getTime(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Timestamp getTimestamp(final int columnIndex) throws SQLException {
    try {
      return delegate.getTimestamp(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public InputStream getAsciiStream(final int columnIndex) throws SQLException {
    try {
      return delegate.getAsciiStream(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  @Deprecated
  public InputStream getUnicodeStream(final int columnIndex) throws SQLException {
    try {
      return delegate.getUnicodeStream(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public InputStream getBinaryStream(final int columnIndex) throws SQLException {
    try {
      return delegate.getBinaryStream(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getString(final String columnLabel) throws SQLException {
    try {
      return delegate.getString(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean getBoolean(final String columnLabel) throws SQLException {
    try {
      return delegate.getBoolean(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public byte getByte(final String columnLabel) throws SQLException {
    try {
      return delegate.getByte(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public short getShort(final String columnLabel) throws SQLException {
    try {
      return delegate.getShort(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getInt(final String columnLabel) throws SQLException {
    try {
      return delegate.getInt(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public long getLong(final String columnLabel) throws SQLException {
    try {
      return delegate.getLong(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public float getFloat(final String columnLabel) throws SQLException {
    try {
      return delegate.getFloat(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public double getDouble(final String columnLabel) throws SQLException {
    try {
      return delegate.getDouble(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  @Deprecated
  public BigDecimal getBigDecimal(final String columnLabel, final int scale) throws SQLException {
    try {
      return delegate.getBigDecimal(columnLabel, scale);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public byte[] getBytes(final String columnLabel) throws SQLException {
    try {
      return delegate.getBytes(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Date getDate(final String columnLabel) throws SQLException {
    try {
      return delegate.getDate(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Time getTime(final String columnLabel) throws SQLException {
    try {
      return delegate.getTime(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Timestamp getTimestamp(final String columnLabel) throws SQLException {
    try {
      return delegate.getTimestamp(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public InputStream getAsciiStream(final String columnLabel) throws SQLException {
    try {
      return delegate.getAsciiStream(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  @Deprecated
  public InputStream getUnicodeStream(final String columnLabel) throws SQLException {
    try {
      return delegate.getUnicodeStream(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public InputStream getBinaryStream(final String columnLabel) throws SQLException {
    try {
      return delegate.getBinaryStream(columnLabel);
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
  public String getCursorName() throws SQLException {
    try {
      return delegate.getCursorName();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    try {
      return new CheckedResultSetMetaData(xa, delegate.getMetaData());
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Object getObject(final int columnIndex) throws SQLException {
    try {
      return delegate.getObject(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Object getObject(final String columnLabel) throws SQLException {
    try {
      return delegate.getObject(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int findColumn(final String columnLabel) throws SQLException {
    try {
      return delegate.findColumn(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Reader getCharacterStream(final int columnIndex) throws SQLException {
    try {
      return delegate.getCharacterStream(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Reader getCharacterStream(final String columnLabel) throws SQLException {
    try {
      return delegate.getCharacterStream(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
    try {
      return delegate.getBigDecimal(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public BigDecimal getBigDecimal(final String columnLabel) throws SQLException {
    try {
      return delegate.getBigDecimal(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isBeforeFirst() throws SQLException {
    try {
      return delegate.isBeforeFirst();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isAfterLast() throws SQLException {
    try {
      return delegate.isAfterLast();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isFirst() throws SQLException {
    try {
      return delegate.isFirst();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean isLast() throws SQLException {
    try {
      return delegate.isLast();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void beforeFirst() throws SQLException {
    try {
      delegate.beforeFirst();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void afterLast() throws SQLException {
    try {
      delegate.afterLast();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean first() throws SQLException {
    try {
      return delegate.first();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean last() throws SQLException {
    try {
      return delegate.last();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getRow() throws SQLException {
    try {
      return delegate.getRow();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean absolute(final int row) throws SQLException {
    try {
      return delegate.absolute(row);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean relative(final int rows) throws SQLException {
    try {
      return delegate.relative(rows);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean previous() throws SQLException {
    try {
      return delegate.previous();
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
  public int getType() throws SQLException {
    try {
      return delegate.getType();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public int getConcurrency() throws SQLException {
    try {
      return delegate.getConcurrency();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean rowUpdated() throws SQLException {
    try {
      return delegate.rowUpdated();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean rowInserted() throws SQLException {
    try {
      return delegate.rowInserted();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public boolean rowDeleted() throws SQLException {
    try {
      return delegate.rowDeleted();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNull(final int columnIndex) throws SQLException {
    try {
      delegate.updateNull(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
    try {
      delegate.updateBoolean(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateByte(final int columnIndex, final byte x) throws SQLException {
    try {
      delegate.updateByte(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateShort(final int columnIndex, final short x) throws SQLException {
    try {
      delegate.updateShort(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateInt(final int columnIndex, final int x) throws SQLException {
    try {
      delegate.updateInt(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateLong(final int columnIndex, final long x) throws SQLException {
    try {
      delegate.updateLong(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateFloat(final int columnIndex, final float x) throws SQLException {
    try {
      delegate.updateFloat(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateDouble(final int columnIndex, final double x) throws SQLException {
    try {
      delegate.updateDouble(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
    try {
      delegate.updateBigDecimal(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateString(final int columnIndex, final String x) throws SQLException {
    try {
      delegate.updateString(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
    try {
      delegate.updateBytes(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateDate(final int columnIndex, final Date x) throws SQLException {
    try {
      delegate.updateDate(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateTime(final int columnIndex, final Time x) throws SQLException {
    try {
      delegate.updateTime(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
    try {
      delegate.updateTimestamp(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateAsciiStream(final int columnIndex, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.updateAsciiStream(columnIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBinaryStream(final int columnIndex, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.updateBinaryStream(columnIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateCharacterStream(final int columnIndex, final Reader x, final int length)
      throws SQLException {
    try {
      delegate.updateCharacterStream(columnIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateObject(final int columnIndex, final Object x, final int scaleOrLength)
      throws SQLException {
    try {
      delegate.updateObject(columnIndex, x, scaleOrLength);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateObject(final int columnIndex, final Object x) throws SQLException {
    try {
      delegate.updateObject(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNull(final String columnLabel) throws SQLException {
    try {
      delegate.updateNull(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBoolean(final String columnLabel, final boolean x) throws SQLException {
    try {
      delegate.updateBoolean(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateByte(final String columnLabel, final byte x) throws SQLException {
    try {
      delegate.updateByte(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateShort(final String columnLabel, final short x) throws SQLException {
    try {
      delegate.updateShort(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateInt(final String columnLabel, final int x) throws SQLException {
    try {
      delegate.updateInt(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateLong(final String columnLabel, final long x) throws SQLException {
    try {
      delegate.updateLong(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateFloat(final String columnLabel, final float x) throws SQLException {
    try {
      delegate.updateFloat(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateDouble(final String columnLabel, final double x) throws SQLException {
    try {
      delegate.updateDouble(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBigDecimal(final String columnLabel, final BigDecimal x) throws SQLException {
    try {
      delegate.updateBigDecimal(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateString(final String columnLabel, final String x) throws SQLException {
    try {
      delegate.updateString(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBytes(final String columnLabel, final byte[] x) throws SQLException {
    try {
      delegate.updateBytes(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateDate(final String columnLabel, final Date x) throws SQLException {
    try {
      delegate.updateDate(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateTime(final String columnLabel, final Time x) throws SQLException {
    try {
      delegate.updateTime(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateTimestamp(final String columnLabel, final Timestamp x) throws SQLException {
    try {
      delegate.updateTimestamp(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateAsciiStream(final String columnLabel, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.updateAsciiStream(columnLabel, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBinaryStream(final String columnLabel, final InputStream x, final int length)
      throws SQLException {
    try {
      delegate.updateBinaryStream(columnLabel, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateCharacterStream(final String columnLabel, final Reader reader, final int length)
      throws SQLException {
    try {
      delegate.updateCharacterStream(columnLabel, reader, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateObject(final String columnLabel, final Object x, final int scaleOrLength)
      throws SQLException {
    try {
      delegate.updateObject(columnLabel, x, scaleOrLength);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateObject(final String columnLabel, final Object x) throws SQLException {
    try {
      delegate.updateObject(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void insertRow() throws SQLException {
    try {
      delegate.insertRow();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateRow() throws SQLException {
    try {
      delegate.updateRow();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void deleteRow() throws SQLException {
    try {
      delegate.deleteRow();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void refreshRow() throws SQLException {
    try {
      delegate.refreshRow();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void cancelRowUpdates() throws SQLException {
    try {
      delegate.cancelRowUpdates();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void moveToInsertRow() throws SQLException {
    try {
      delegate.moveToInsertRow();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void moveToCurrentRow() throws SQLException {
    try {
      delegate.moveToCurrentRow();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Statement getStatement() throws SQLException {
    return statement;
  }

  @Override
  public Object getObject(final int columnIndex, final Map<String, Class<?>> map)
      throws SQLException {
    try {
      return delegate.getObject(columnIndex, map);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Ref getRef(final int columnIndex) throws SQLException {
    try {
      return delegate.getRef(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Blob getBlob(final int columnIndex) throws SQLException {
    try {
      return delegate.getBlob(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Clob getClob(final int columnIndex) throws SQLException {
    try {
      return delegate.getClob(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Array getArray(final int columnIndex) throws SQLException {
    try {
      return delegate.getArray(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Object getObject(final String columnLabel, final Map<String, Class<?>> map)
      throws SQLException {
    try {
      return delegate.getObject(columnLabel, map);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Ref getRef(final String columnLabel) throws SQLException {
    try {
      return delegate.getRef(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Blob getBlob(final String columnLabel) throws SQLException {
    try {
      return delegate.getBlob(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Clob getClob(final String columnLabel) throws SQLException {
    try {
      return delegate.getClob(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Array getArray(final String columnLabel) throws SQLException {
    try {
      return delegate.getArray(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
    try {
      return delegate.getDate(columnIndex, cal);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Date getDate(final String columnLabel, final Calendar cal) throws SQLException {
    try {
      return delegate.getDate(columnLabel, cal);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
    try {
      return delegate.getTime(columnIndex, cal);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Time getTime(final String columnLabel, final Calendar cal) throws SQLException {
    try {
      return delegate.getTime(columnLabel, cal);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException {
    try {
      return delegate.getTimestamp(columnIndex, cal);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Timestamp getTimestamp(final String columnLabel, final Calendar cal) throws SQLException {
    try {
      return delegate.getTimestamp(columnLabel, cal);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public URL getURL(final int columnIndex) throws SQLException {
    try {
      return delegate.getURL(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public URL getURL(final String columnLabel) throws SQLException {
    try {
      return delegate.getURL(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateRef(final int columnIndex, final Ref x) throws SQLException {
    try {
      delegate.updateRef(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateRef(final String columnLabel, final Ref x) throws SQLException {
    try {
      delegate.updateRef(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBlob(final int columnIndex, final Blob x) throws SQLException {
    try {
      delegate.updateBlob(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBlob(final String columnLabel, final Blob x) throws SQLException {
    try {
      delegate.updateBlob(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateClob(final int columnIndex, final Clob x) throws SQLException {
    try {
      delegate.updateClob(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateClob(final String columnLabel, final Clob x) throws SQLException {
    try {
      delegate.updateClob(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateArray(final int columnIndex, final Array x) throws SQLException {
    try {
      delegate.updateArray(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateArray(final String columnLabel, final Array x) throws SQLException {
    try {
      delegate.updateArray(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public RowId getRowId(final int columnIndex) throws SQLException {
    try {
      return delegate.getRowId(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public RowId getRowId(final String columnLabel) throws SQLException {
    try {
      return delegate.getRowId(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateRowId(final int columnIndex, final RowId x) throws SQLException {
    try {
      delegate.updateRowId(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateRowId(final String columnLabel, final RowId x) throws SQLException {
    try {
      delegate.updateRowId(columnLabel, x);
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
  public boolean isClosed() throws SQLException {
    try {
      return delegate.isClosed();
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNString(final int columnIndex, final String nString) throws SQLException {
    try {
      delegate.updateNString(columnIndex, nString);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNString(final String columnLabel, final String nString) throws SQLException {
    try {
      delegate.updateNString(columnLabel, nString);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException {
    try {
      delegate.updateNClob(columnIndex, nClob);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNClob(final String columnLabel, final NClob nClob) throws SQLException {
    try {
      delegate.updateNClob(columnLabel, nClob);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public NClob getNClob(final int columnIndex) throws SQLException {
    try {
      return delegate.getNClob(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public NClob getNClob(final String columnLabel) throws SQLException {
    try {
      return delegate.getNClob(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public SQLXML getSQLXML(final int columnIndex) throws SQLException {
    try {
      return delegate.getSQLXML(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public SQLXML getSQLXML(final String columnLabel) throws SQLException {
    try {
      return delegate.getSQLXML(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException {
    try {
      delegate.updateSQLXML(columnIndex, xmlObject);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException {
    try {
      delegate.updateSQLXML(columnLabel, xmlObject);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getNString(final int columnIndex) throws SQLException {
    try {
      return delegate.getNString(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public String getNString(final String columnLabel) throws SQLException {
    try {
      return delegate.getNString(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Reader getNCharacterStream(final int columnIndex) throws SQLException {
    try {
      return delegate.getNCharacterStream(columnIndex);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public Reader getNCharacterStream(final String columnLabel) throws SQLException {
    try {
      return delegate.getNCharacterStream(columnLabel);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNCharacterStream(final int columnIndex, final Reader x, final long length)
      throws SQLException {
    try {
      delegate.updateNCharacterStream(columnIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNCharacterStream(
      final String columnLabel, final Reader reader, final long length) throws SQLException {
    try {
      delegate.updateNCharacterStream(columnLabel, reader, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateAsciiStream(final int columnIndex, final InputStream x, final long length)
      throws SQLException {
    try {
      delegate.updateAsciiStream(columnIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBinaryStream(final int columnIndex, final InputStream x, final long length)
      throws SQLException {
    try {
      delegate.updateBinaryStream(columnIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateCharacterStream(final int columnIndex, final Reader x, final long length)
      throws SQLException {
    try {
      delegate.updateCharacterStream(columnIndex, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateAsciiStream(final String columnLabel, final InputStream x, final long length)
      throws SQLException {
    try {
      delegate.updateAsciiStream(columnLabel, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBinaryStream(final String columnLabel, final InputStream x, final long length)
      throws SQLException {
    try {
      delegate.updateBinaryStream(columnLabel, x, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateCharacterStream(
      final String columnLabel, final Reader reader, final long length) throws SQLException {
    try {
      delegate.updateCharacterStream(columnLabel, reader, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBlob(final int columnIndex, final InputStream inputStream, final long length)
      throws SQLException {
    try {
      delegate.updateBlob(columnIndex, inputStream, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBlob(final String columnLabel, final InputStream inputStream, final long length)
      throws SQLException {
    try {
      delegate.updateBlob(columnLabel, inputStream, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateClob(final int columnIndex, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.updateClob(columnIndex, reader, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateClob(final String columnLabel, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.updateClob(columnLabel, reader, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNClob(final int columnIndex, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.updateNClob(columnIndex, reader, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNClob(final String columnLabel, final Reader reader, final long length)
      throws SQLException {
    try {
      delegate.updateNClob(columnLabel, reader, length);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException {
    try {
      delegate.updateNCharacterStream(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNCharacterStream(final String columnLabel, final Reader reader)
      throws SQLException {
    try {
      delegate.updateNCharacterStream(columnLabel, reader);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException {
    try {
      delegate.updateAsciiStream(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException {
    try {
      delegate.updateBinaryStream(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException {
    try {
      delegate.updateCharacterStream(columnIndex, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException {
    try {
      delegate.updateAsciiStream(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBinaryStream(final String columnLabel, final InputStream x)
      throws SQLException {
    try {
      delegate.updateBinaryStream(columnLabel, x);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateCharacterStream(final String columnLabel, final Reader reader)
      throws SQLException {
    try {
      delegate.updateCharacterStream(columnLabel, reader);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException {
    try {
      delegate.updateBlob(columnIndex, inputStream);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateBlob(final String columnLabel, final InputStream inputStream)
      throws SQLException {
    try {
      delegate.updateBlob(columnLabel, inputStream);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateClob(final int columnIndex, final Reader reader) throws SQLException {
    try {
      delegate.updateClob(columnIndex, reader);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateClob(final String columnLabel, final Reader reader) throws SQLException {
    try {
      delegate.updateClob(columnLabel, reader);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNClob(final int columnIndex, final Reader reader) throws SQLException {
    try {
      delegate.updateNClob(columnIndex, reader);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateNClob(final String columnLabel, final Reader reader) throws SQLException {
    try {
      delegate.updateNClob(columnLabel, reader);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public <T> T getObject(final int columnIndex, final Class<T> type) throws SQLException {
    try {
      return delegate.getObject(columnIndex, type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public <T> T getObject(final String columnLabel, final Class<T> type) throws SQLException {
    try {
      return delegate.getObject(columnLabel, type);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateObject(
      final int columnIndex, final Object x, final SQLType targetSqlType, final int scaleOrLength)
      throws SQLException {
    try {
      delegate.updateObject(columnIndex, x, targetSqlType, scaleOrLength);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateObject(
      final String columnLabel,
      final Object x,
      final SQLType targetSqlType,
      final int scaleOrLength)
      throws SQLException {
    try {
      delegate.updateObject(columnLabel, x, targetSqlType, scaleOrLength);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateObject(final int columnIndex, final Object x, final SQLType targetSqlType)
      throws SQLException {
    try {
      delegate.updateObject(columnIndex, x, targetSqlType);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(xa, e);
    }
  }

  @Override
  public void updateObject(final String columnLabel, final Object x, final SQLType targetSqlType)
      throws SQLException {
    try {
      delegate.updateObject(columnLabel, x, targetSqlType);
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
