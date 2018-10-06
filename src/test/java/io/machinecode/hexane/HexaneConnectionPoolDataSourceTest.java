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

import io.machinecode.hexane.Defaults.Builder;
import org.junit.Before;
import org.junit.Test;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class HexaneConnectionPoolDataSourceTest
    extends CommonDataSourceTestBase<
        ConnectionPoolDataSource,
        PooledConnection,
        HexaneConnectionPoolDataSource,
        HexanePooledPool> {
  Connection conn;

  @Before
  public void setUp() throws Exception {
    final Defaults defaults = new Builder().build();
    delegate = mock(ConnectionPoolDataSource.class);
    value = mock(PooledConnection.class);
    conn = mock(Connection.class);
    when(delegate.getPooledConnection()).thenReturn(value);
    when(value.getConnection()).thenReturn(conn);
    when(conn.isValid(anyInt())).thenReturn(true);
    pool =
        new HexanePooledPool(
            Hexane.builder().setMaintenanceExecutor(Runnable::run).getConfig(), defaults, delegate);
    dataSource = new HexaneConnectionPoolDataSource(pool, delegate, defaults);
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
