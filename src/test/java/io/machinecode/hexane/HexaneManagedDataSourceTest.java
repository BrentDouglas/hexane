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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.machinecode.hexane.Defaults.Builder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class HexaneManagedDataSourceTest
    extends CommonDataSourceTestBase<DataSource, Connection, HexaneManagedDataSource, HexanePool> {

  @Before
  public void setUp() throws Exception {
    final Defaults defaults = new Builder().build();
    delegate = mock(DataSource.class);
    value = mock(Connection.class);
    when(delegate.getConnection()).thenReturn(value);
    when(value.isValid(anyInt())).thenReturn(true);
    pool =
        new HexanePool(
            Hexane.builder().setMaintenanceExecutor(Runnable::run).getConfig(), defaults, delegate);
    dataSource = new HexaneManagedDataSource(pool, delegate, defaults);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(dataSource.isWrapperFor(DataSource.class));
    assertTrue(dataSource.isWrapperFor(HexaneManagedDataSource.class));

    when(delegate.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(JdbcDataSource.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(dataSource, dataSource.unwrap(DataSource.class));
    assertEquals(dataSource, dataSource.unwrap(HexaneManagedDataSource.class));

    when(delegate.unwrap(any())).thenReturn(delegate);

    assertEquals(delegate, dataSource.unwrap(JdbcDataSource.class));
  }

  @Test
  public void isWrapperForFatalCallsKill() throws SQLException {
    when(delegate.isWrapperFor(any())).thenThrow(TestUtil.getFatalState());
    try {
      dataSource.isWrapperFor(JdbcDataSource.class);
      fail();
    } catch (final SQLException e) {
      assertTrue(pool.isClosed());
    }
  }

  @Test
  public void unwrapFatalCallsKill() throws SQLException {
    when(delegate.unwrap(any())).thenThrow(TestUtil.getFatalState());
    try {
      dataSource.unwrap(JdbcDataSource.class);
      fail();
    } catch (final SQLException e) {
      assertTrue(pool.isClosed());
    }
  }

  @Test
  public void getPooledConnection() throws SQLException {
    final PooledConnection conn = dataSource.getPooledConnection();
    try {
      assertNotSame(value, conn);
    } finally {
      conn.close();
    }
  }

  @Test(expected = SQLFeatureNotSupportedException.class)
  public void getPooledConnectionUserNotSupported() throws SQLException {
    dataSource.getPooledConnection("any", null);
  }
}
