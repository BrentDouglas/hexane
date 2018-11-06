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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class HexaneXAPoolTest extends Assert {

  XADataSource dataSource;
  XAConnection pooled;
  HexaneXAPool pool;
  String user;
  Connection conn;
  Defaults defaults;
  List<ConnectionEventListener> connectionListeners;

  @Before
  public void setUp() throws Exception {
    connectionListeners = new ArrayList<>();
    dataSource = mock(XADataSource.class);
    pooled = mock(XAConnection.class);
    conn = mock(Connection.class);
    defaults = new Defaults.Builder().build();
    when(dataSource.getXAConnection()).thenReturn(pooled);
    when(dataSource.getXAConnection("test", null)).thenReturn(pooled);
    when(pooled.getConnection()).thenReturn(conn);
    when(conn.isValid(anyInt())).thenReturn(true);
    doAnswer(inv -> connectionListeners.add((ConnectionEventListener) inv.getArguments()[0]))
        .when(pooled)
        .addConnectionEventListener(any());
    doAnswer(inv -> connectionListeners.remove((ConnectionEventListener) inv.getArguments()[0]))
        .when(pooled)
        .removeConnectionEventListener(any());
    pool =
        new HexaneXAPool(
            Hexane.builder().setUser(user).setMaintenanceExecutor(Runnable::run).getConfig(),
            defaults,
            dataSource);
  }

  @Test
  public void getConnection() throws SQLException {
    assertNotNull(pool.getConnection());
  }

  @Test
  public void getConnectionUser() throws SQLException {
    pool = new HexaneXAPool(Hexane.builder().setUser("test").getConfig(), defaults, dataSource);

    assertNotNull(pool.getConnection());
  }

  @Test
  public void getConnectionThrows() throws SQLException {
    when(dataSource.getXAConnection()).thenThrow(TestUtil.getNormal());

    assertNull(pool.getConnection());
  }

  @Test
  public void getConnectionThrowsNonTransient() throws SQLException {
    when(dataSource.getXAConnection()).thenThrow(TestUtil.getFatalNoConnection());

    assertNull(pool.getConnection());
  }

  @Test
  public void connectionListenerReturnsConnection() throws Exception {
    pool.refill();

    assertEquals(2, connectionListeners.size());

    final Pooled<XAConnection> a = pool.take();
    final Pooled<XAConnection> b = pool.take();
    final Pooled<XAConnection> c = pool.take();
    final Pooled<XAConnection> d = pool.take();

    assertEquals(4, connectionListeners.size());
    assertEquals(0, pool.getFree());

    final ArrayList<ConnectionEventListener> oldListeners = new ArrayList<>(connectionListeners);
    for (final ConnectionEventListener listener : oldListeners) {
      listener.connectionClosed(new ConnectionEvent(pooled, null));

      assertTrue(connectionListeners.contains(listener));
    }

    assertEquals(4, connectionListeners.size());
    assertEquals(4, pool.getFree());
  }

  @Test
  public void connectionListenerInvalidatesConnection() throws Exception {
    pool.refill();

    assertEquals(2, connectionListeners.size());

    final ArrayList<ConnectionEventListener> oldListeners = new ArrayList<>(connectionListeners);
    for (final ConnectionEventListener listener : oldListeners) {
      listener.connectionErrorOccurred(new ConnectionEvent(pooled, new SQLException()));

      assertFalse(connectionListeners.contains(listener));
    }
  }
}
