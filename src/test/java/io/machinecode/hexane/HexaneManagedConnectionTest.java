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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class HexaneManagedConnectionTest extends Assert {

  BasePool<Connection> pool;
  Pooled<Connection> pooled;
  HexaneManagedConnection conn;
  AutoCloseable close;
  Connection real;

  @Before
  public void setUp() throws Exception {
    final Config config = Hexane.builder().getConfig();
    pool = mock(BasePool.class);
    when(pool.getConfig()).thenReturn(config);
    close = mock(AutoCloseable.class);
    real = mock(Connection.class);
    pooled = new Pooled<>(pool, real, real, close, StatementCache.INSTANCE);
    conn = new HexaneManagedConnection(config, pooled, new Defaults.Builder().build());
  }

  @Test
  public void kill() throws SQLException {
    conn.kill(new SQLException());

    try {
      conn.getConnection();
      fail();
    } catch (final SQLException e) {
      //
    }
    try {
      conn.enlist(null);
      fail();
    } catch (final SQLException e) {
      //
    }
    try {
      conn.registerChecked(null, null);
      fail();
    } catch (final SQLException e) {
      //
    }
  }

  @Test
  public void killDoesntThrow() throws Exception {
    final AutoCloseable close = mock(AutoCloseable.class);
    doThrow(TestUtil.getFatalState()).when(close).close();
    conn.addConnectionEventListener(mock(ConnectionEventListener.class));
    conn.addStatementEventListener(mock(StatementEventListener.class));

    conn.enlist(close);

    conn.kill(new SQLException());
  }

  @Test
  public void enlist() throws SQLException {
    final Statement statement = mock(Statement.class);

    conn.enlist(statement);
    conn.close();

    verify(statement).close();
  }

  @Test
  public void getConnectionClosedReturns() throws Exception {
    try (final Connection connection = conn.getConnection()) {
      assertNotSame(this.real, connection);
    }

    verify(pool).give(pooled);
  }

  @Test
  public void close() throws SQLException {
    conn.close();

    verify(pool).give(pooled);
  }

  @Test
  public void closeFatal() throws Exception {
    conn.enlist(
        () -> {
          throw TestUtil.getFatalState();
        });

    try {
      conn.close();

      fail();
    } catch (final SQLException e) {
      verify(pool).remove(pooled);
    }
  }

  @Test
  public void closeNormal() throws Exception {
    conn.enlist(
        () -> {
          throw TestUtil.getNormal();
        });

    try {
      conn.close();

      fail();
    } catch (final SQLException e) {
      verify(pool).give(pooled);
    }
  }

  @Test
  public void addConnectionEventListener() throws SQLException {
    final ConnectionEventListener listener = mock(ConnectionEventListener.class);

    conn.addConnectionEventListener(listener);

    verify(listener, times(0)).connectionClosed(any());
    verify(listener, times(0)).connectionErrorOccurred(any());

    conn.close();

    verify(listener, times(1)).connectionClosed(any());
    verify(listener, times(0)).connectionErrorOccurred(any());
  }

  @Test
  public void addConnectionEventListenerKill() throws SQLException {
    final ConnectionEventListener listener = mock(ConnectionEventListener.class);

    conn.addConnectionEventListener(listener);

    verify(listener, times(0)).connectionClosed(any());
    verify(listener, times(0)).connectionErrorOccurred(any());

    conn.kill(new SQLException());

    verify(listener, times(0)).connectionClosed(any());
    verify(listener, times(1)).connectionErrorOccurred(any());
  }

  @Test
  public void addConnectionEventListenerCloseThrows() throws SQLException {
    final ConnectionEventListener first = mock(ConnectionEventListener.class);
    doThrow(new RuntimeException()).when(first).connectionClosed(any());

    final ConnectionEventListener second = mock(ConnectionEventListener.class);
    doThrow(new RuntimeException()).when(second).connectionClosed(any());

    conn.addConnectionEventListener(first);
    conn.addConnectionEventListener(second);

    verify(first, times(0)).connectionClosed(any());
    verify(first, times(0)).connectionErrorOccurred(any());
    verify(second, times(0)).connectionClosed(any());
    verify(second, times(0)).connectionErrorOccurred(any());

    try {
      conn.close();
    } catch (final SQLException e) {
      verify(first, times(1)).connectionClosed(any());
      verify(first, times(1)).connectionErrorOccurred(any());
      verify(second, times(1)).connectionClosed(any());
      verify(second, times(1)).connectionErrorOccurred(any());
    }
  }

  @Test
  public void addConnectionEventListenerKillThrows() throws SQLException {
    final ConnectionEventListener listener = mock(ConnectionEventListener.class);
    doThrow(new RuntimeException()).when(listener).connectionErrorOccurred(any());

    conn.addConnectionEventListener(listener);

    verify(listener, times(0)).connectionClosed(any());
    verify(listener, times(0)).connectionErrorOccurred(any());

    conn.kill(new SQLException());

    verify(listener, times(0)).connectionClosed(any());
    verify(listener, times(1)).connectionErrorOccurred(any());
  }

  @Test
  public void addConnectionEventListenerCloseBothThrow() throws SQLException {
    final ConnectionEventListener listener = mock(ConnectionEventListener.class);
    doThrow(new RuntimeException()).when(listener).connectionClosed(any());
    doThrow(new RuntimeException()).when(listener).connectionErrorOccurred(any());

    conn.addConnectionEventListener(listener);

    verify(listener, times(0)).connectionClosed(any());
    verify(listener, times(0)).connectionErrorOccurred(any());

    try {
      conn.close();
    } catch (final SQLException e) {
      verify(listener, times(1)).connectionClosed(any());
      verify(listener, times(1)).connectionErrorOccurred(any());
    }
  }

  // TODO
  @Test
  public void addStatementEventListener() throws SQLException {
    final PreparedStatement statement = mock(PreparedStatement.class);
    final CheckedPreparedStatement<PreparedStatement> wrapper =
        new CheckedPreparedStatement<>(conn, real, statement);
    final StatementEventListener listener = mock(StatementEventListener.class);

    conn.registerChecked(wrapper, statement);

    conn.addStatementEventListener(listener);

    verify(listener, times(0)).statementClosed(any());
    verify(listener, times(0)).statementErrorOccurred(any());

    conn.removeStatementEventListener(listener);

    verify(listener, times(0)).statementClosed(any());
    verify(listener, times(0)).statementErrorOccurred(any());
  }

  @Test
  public void connectionEventListenerAddRemoveIgnoredAfterClose() throws SQLException {
    conn.close();
    final ConnectionEventListener listener = mock(ConnectionEventListener.class);

    conn.addConnectionEventListener(listener);
    conn.removeConnectionEventListener(listener);
  }

  @Test
  public void statementEventListenerAddRemoveIgnoredAfterClose() throws SQLException {
    conn.close();
    final StatementEventListener listener = mock(StatementEventListener.class);

    conn.addStatementEventListener(listener);
    conn.removeStatementEventListener(listener);
  }

  @Test
  public void connectionEventListenerRemoveNotAddedIsOk() throws SQLException {
    conn.removeConnectionEventListener(mock(ConnectionEventListener.class));
  }

  @Test
  public void statementEventListenerRemoveNotAddedIsOk() throws SQLException {
    conn.removeStatementEventListener(mock(StatementEventListener.class));
  }
}
