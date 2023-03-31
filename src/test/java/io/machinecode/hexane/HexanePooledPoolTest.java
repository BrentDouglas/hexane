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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import javax.sql.StatementEvent;
import javax.sql.StatementEventListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class HexanePooledPoolTest extends Assert {

  ConnectionPoolDataSource dataSource;
  PooledConnection pooled;
  HexanePooledPool pool;
  String user;
  Connection conn;
  Defaults defaults;
  List<ConnectionEventListener> connectionListeners;
  List<StatementEventListener> statementListeners;

  int closedStatements = 0;
  int errorStatements = 0;

  @Before
  public void setUp() throws Exception {
    connectionListeners = new ArrayList<>();
    statementListeners = new ArrayList<>();
    dataSource = mock(ConnectionPoolDataSource.class);
    pooled = mock(PooledConnection.class);
    conn = mock(Connection.class);
    defaults = new Defaults.Builder().build();
    when(dataSource.getPooledConnection()).thenReturn(pooled);
    when(dataSource.getPooledConnection("test", null)).thenReturn(pooled);
    when(pooled.getConnection()).thenReturn(conn);
    when(conn.isValid(anyInt())).thenReturn(true);
    doAnswer(inv -> connectionListeners.add((ConnectionEventListener) inv.getArguments()[0]))
        .when(pooled)
        .addConnectionEventListener(any());
    doAnswer(inv -> connectionListeners.remove((ConnectionEventListener) inv.getArguments()[0]))
        .when(pooled)
        .removeConnectionEventListener(any());
    doAnswer(inv -> statementListeners.add((StatementEventListener) inv.getArguments()[0]))
        .when(pooled)
        .addStatementEventListener(any());
    doAnswer(inv -> statementListeners.remove((StatementEventListener) inv.getArguments()[0]))
        .when(pooled)
        .removeStatementEventListener(any());
    pool =
        new HexanePooledPool(
            Hexane.builder()
                .setMaintenanceExecutor(Runnable::run)
                .setUser(user)
                .setStatementCacheSize(1)
                .setListener(
                    new HexaneListener() {
                      @Override
                      public void onStatementClosedEviction() {
                        closedStatements += 1;
                      }

                      @Override
                      public void onStatementErrorEviction() {
                        errorStatements += 1;
                      }
                    })
                .getConfig(),
            defaults,
            dataSource);
  }

  @Test
  public void getConnection() throws SQLException {
    assertNotNull(pool.getConnection());
  }

  @Test
  public void getConnectionUser() throws SQLException {
    pool = new HexanePooledPool(Hexane.builder().setUser("test").getConfig(), defaults, dataSource);

    assertNotNull(pool.getConnection());
  }

  @Test
  public void getConnectionThrows() throws SQLException {
    when(dataSource.getPooledConnection()).thenThrow(TestUtil.getNormal());

    assertNull(pool.getConnection());
  }

  @Test
  public void getConnectionThrowsNonTransient() throws SQLException {
    when(dataSource.getPooledConnection()).thenThrow(TestUtil.getFatalNoConnection());

    assertNull(pool.getConnection());
  }

  @Test
  public void connectionListenerReturnsConnection() throws Exception {
    pool.refill();

    assertEquals(2, connectionListeners.size());

    final Pooled<PooledConnection> a = pool.take();
    final Pooled<PooledConnection> b = pool.take();
    final Pooled<PooledConnection> c = pool.take();
    final Pooled<PooledConnection> d = pool.take();

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

  @Test
  public void statementListenerCallsClose() throws Exception {
    pool.refill();

    assertEquals(2, statementListeners.size());

    when(conn.prepareStatement(any())).thenReturn(mock(PreparedStatement.class));
    when(conn.getHoldability()).thenReturn(ResultSet.HOLD_CURSORS_OVER_COMMIT);

    final ArrayList<StatementEventListener> oldListeners = new ArrayList<>(statementListeners);
    int expected = 1;
    for (final StatementEventListener listener : oldListeners) {
      final PreparedStatement statement = pool.take().getCache().prepareStatement(conn, "select 1");

      listener.statementClosed(new StatementEvent(pooled, statement, null));

      assertEquals(expected, closedStatements);
      assertEquals(0, errorStatements);
      expected += 1;
    }
  }

  @Test
  public void statementListenerInvalidatesConnection() throws Exception {
    pool.refill();

    assertEquals(2, statementListeners.size());

    when(conn.prepareStatement(any())).thenReturn(mock(PreparedStatement.class));
    when(conn.getHoldability()).thenReturn(ResultSet.HOLD_CURSORS_OVER_COMMIT);

    final ArrayList<StatementEventListener> oldListeners = new ArrayList<>(statementListeners);
    int expected = 1;
    for (final StatementEventListener listener : oldListeners) {
      final PreparedStatement statement = pool.take().getCache().prepareStatement(conn, "select 1");

      listener.statementErrorOccurred(new StatementEvent(pooled, statement, new SQLException()));

      assertEquals(0, closedStatements);
      assertEquals(expected, errorStatements);
      expected += 1;
    }
  }
}
