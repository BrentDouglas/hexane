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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEvent;
import javax.sql.StatementEventListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public abstract class PooledConnectionTestBase<
        C extends PooledConnection, T extends Terminal & PooledConnection>
    extends Assert {

  BasePool<C> pool;
  Pooled<C> pooled;
  T conn;
  C delegate;
  AutoCloseable close;
  Connection real;
  Config config;
  Defaults defaults;

  @Before
  public void setUp() throws Exception {
    defaults = new Defaults.Builder().build();
    config = Hexane.builder().getConfig();
    pool = mock(BasePool.class);
    when(pool.getConfig()).thenReturn(config);
    close = mock(AutoCloseable.class);
    real = mock(Connection.class);
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
    final ConnectionEventListener listener =
        new ConnectionEventListener() {
          @Override
          public void connectionClosed(final ConnectionEvent event) {}

          @Override
          public void connectionErrorOccurred(final ConnectionEvent event) {}
        };

    conn.addConnectionEventListener(listener);

    verify(delegate).addConnectionEventListener(listener);
  }

  @Test
  public void removeConnectionEventListener() throws SQLException {
    final ConnectionEventListener listener =
        new ConnectionEventListener() {
          @Override
          public void connectionClosed(final ConnectionEvent event) {}

          @Override
          public void connectionErrorOccurred(final ConnectionEvent event) {}
        };

    conn.removeConnectionEventListener(listener);

    verify(delegate).removeConnectionEventListener(listener);
  }

  @Test
  public void addStatementEventListener() throws SQLException {
    final StatementEventListener listener =
        new StatementEventListener() {
          @Override
          public void statementClosed(final StatementEvent event) {}

          @Override
          public void statementErrorOccurred(final StatementEvent event) {}
        };

    conn.addStatementEventListener(listener);

    verify(delegate).addStatementEventListener(listener);
  }

  @Test
  public void removeStatementEventListener() throws SQLException {
    final StatementEventListener listener =
        new StatementEventListener() {
          @Override
          public void statementClosed(final StatementEvent event) {}

          @Override
          public void statementErrorOccurred(final StatementEvent event) {}
        };

    conn.removeStatementEventListener(listener);

    verify(delegate).removeStatementEventListener(listener);
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
