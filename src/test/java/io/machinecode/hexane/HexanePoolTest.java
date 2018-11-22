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

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class HexanePoolTest extends Assert {

  DataSource dataSource;
  HexanePool pool;
  String user;
  Connection conn;
  Defaults defaults;

  @Before
  public void setUp() throws Exception {
    dataSource = mock(DataSource.class);
    conn = mock(Connection.class);
    when(conn.isValid(anyInt())).thenReturn(true);
    defaults = new Defaults.Builder().build();
    pool =
        new HexanePool(
            Hexane.builder().setUser(user).setMaintenanceExecutor(it -> {}).getConfig(),
            defaults,
            dataSource);
  }

  @Test
  public void getConnection() throws SQLException {
    when(dataSource.getConnection()).thenReturn(conn);

    assertNotNull(pool.getConnection());
  }

  @Test
  public void getConnectionUser() throws SQLException {
    when(dataSource.getConnection("test", null)).thenReturn(conn);
    pool = new HexanePool(Hexane.builder().setUser("test").getConfig(), defaults, dataSource);

    assertNotNull(pool.getConnection());
  }

  @Test
  public void getConnectionThrows() throws SQLException {
    when(dataSource.getConnection()).thenThrow(TestUtil.getNormal());

    assertNull(pool.getConnection());
  }

  @Test
  public void getConnectionThrowsNonTransient() throws SQLException {
    when(dataSource.getConnection()).thenThrow(TestUtil.getFatalNoConnection());

    assertNull(pool.getConnection());
  }
}
