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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import javax.sql.CommonDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public abstract class CommonDataSourceTestBase<
        D extends CommonDataSource, C, B extends BaseDataSource<C, ?, D>, P extends BasePool<C>>
    extends Assert {

  D delegate;
  C value;
  B dataSource;
  P pool;

  @After
  public void tearDown() throws SQLException {
    pool.close();
  }

  @Test
  public void getConnection() throws SQLException {
    try (final Connection conn = dataSource.getConnection()) {
      assertNotSame(value, conn);
    }
  }

  @Test(expected = SQLFeatureNotSupportedException.class)
  public void getConnectionUserNotSupported() throws SQLException {
    dataSource.getConnection("any", null);
  }

  @Test
  public void getLoginTimeout() throws SQLException {
    dataSource.getLoginTimeout();

    verify(delegate).getLoginTimeout();
  }

  @Test
  public void getLoginTimeoutNormal() throws SQLException {
    when(delegate.getLoginTimeout()).thenThrow(TestUtil.getNormal());

    try {
      dataSource.getLoginTimeout();
      fail();
    } catch (final SQLException e) {
      assertFalse(pool.isClosed());
    }
  }

  @Test
  public void getLoginTimeoutFatal() throws SQLException {
    when(delegate.getLoginTimeout()).thenThrow(TestUtil.getFatalState());

    try {
      dataSource.getLoginTimeout();
      fail();
    } catch (final SQLException e) {
      assertTrue(pool.isClosed());
    }
  }

  @Test
  public void setLoginTimeout() throws SQLException {
    final int value = 1234;
    dataSource.setLoginTimeout(value);

    verify(delegate).setLoginTimeout(value);
  }

  @Test
  public void setLoginTimeoutNormal() throws SQLException {
    final int value = 1234;
    doThrow(TestUtil.getNormal()).when(delegate).setLoginTimeout(value);

    try {
      dataSource.setLoginTimeout(value);
      fail();
    } catch (final SQLException e) {
      assertFalse(pool.isClosed());
    }
  }

  @Test
  public void setLoginTimeoutFatal() throws SQLException {
    final int value = 1234;
    doThrow(TestUtil.getFatalState()).when(delegate).setLoginTimeout(value);

    try {
      dataSource.setLoginTimeout(value);
      fail();
    } catch (final SQLException e) {
      assertTrue(pool.isClosed());
    }
  }

  @Test
  public void getLogWriter() throws SQLException {
    dataSource.getLogWriter();

    verify(delegate).getLogWriter();
  }

  @Test
  public void getLogWriterNormal() throws SQLException {
    when(delegate.getLogWriter()).thenThrow(TestUtil.getNormal());

    try {
      dataSource.getLogWriter();
      fail();
    } catch (final SQLException e) {
      assertFalse(pool.isClosed());
    }
  }

  @Test
  public void getLogWriterFatal() throws SQLException {
    when(delegate.getLogWriter()).thenThrow(TestUtil.getFatalState());

    try {
      dataSource.getLogWriter();
      fail();
    } catch (final SQLException e) {
      assertTrue(pool.isClosed());
    }
  }

  @Test
  public void setLogWriter() throws SQLException {
    final PrintWriter value = mock(PrintWriter.class);
    dataSource.setLogWriter(value);

    verify(delegate).setLogWriter(value);
  }

  @Test
  public void setLogWriterNormal() throws SQLException {
    final PrintWriter value = mock(PrintWriter.class);
    doThrow(TestUtil.getNormal()).when(delegate).setLogWriter(value);

    try {
      dataSource.setLogWriter(value);
      fail();
    } catch (final SQLException e) {
      assertFalse(pool.isClosed());
    }
  }

  @Test
  public void setLogWriterFatal() throws SQLException {
    final PrintWriter value = mock(PrintWriter.class);
    doThrow(TestUtil.getFatalState()).when(delegate).setLogWriter(value);

    try {
      dataSource.setLogWriter(value);
      fail();
    } catch (final SQLException e) {
      assertTrue(pool.isClosed());
    }
  }

  @Test
  public void getParentLogger() throws SQLException {
    dataSource.getParentLogger();

    verify(delegate).getParentLogger();
  }

  @Test
  public void getParentLoggerNormal() throws SQLException {
    doThrow(TestUtil.getNormalNotSupported()).when(delegate).getParentLogger();

    try {
      dataSource.getParentLogger();
      fail();
    } catch (final SQLException e) {
      assertFalse(pool.isClosed());
    }
  }

  @Test
  public void getParentLoggerFatal() throws SQLException {
    doThrow(TestUtil.getFatalNotSupported()).when(delegate).getParentLogger();

    try {
      dataSource.getParentLogger();
      fail();
    } catch (final SQLException e) {
      assertTrue(pool.isClosed());
    }
  }

  @Test
  public void close() throws SQLException {
    dataSource.close();

    assertTrue(pool.isClosed());
  }
}
