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

import io.machinecode.hexane.HexaneConnection.ClosedConnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class CheckedConnectionTest extends CheckedTestBase<Connection, CheckedConnection> {

  public CheckedConnectionTest() {
    super(Connection.class, CheckedConnection.class);
  }

  BasePool pool;
  Pooled<?> pooled;

  @Override
  protected CheckedConnection create(final Terminal terminal, final Connection delegate) {
    pool = mock(BasePool.class);
    when(pool.getConfig()).thenReturn(config);
    return new CheckedConnection(
        terminal,
        pooled = new Pooled<>(pool, delegate, mock(AutoCloseable.class), StatementCache.INSTANCE),
        delegate,
        defaults);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(delegate.isWrapperFor(Connection.class));
    assertTrue(delegate.isWrapperFor(CheckedConnection.class));

    when(real.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(ClosedConnection.class));

    final CheckedConnection x = create(terminal, ClosedConnection.INSTANCE);
    assertTrue(x.isWrapperFor(ClosedConnection.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(delegate, delegate.unwrap(Connection.class));
    assertEquals(delegate, delegate.unwrap(CheckedConnection.class));

    when(real.unwrap(any())).thenReturn(real);

    assertEquals(real, delegate.unwrap(ClosedConnection.class));

    final CheckedConnection x = create(terminal, ClosedConnection.INSTANCE);
    assertEquals(ClosedConnection.INSTANCE, x.unwrap(ClosedConnection.class));
  }

  @Test
  public void isWrapperForFatalCallsKill() throws SQLException {
    when(real.isWrapperFor(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.isWrapperFor(ClosedConnection.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void unwrapFatalCallsKill() throws SQLException {
    when(real.unwrap(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.unwrap(ClosedConnection.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void close() throws SQLException {
    delegate.close();

    verify(pool).give(pooled);
  }

  @Test
  public void closeFatal() throws SQLException {
    pooled.enlist(
        () -> {
          throw TestUtil.getFatalState();
        });
    try {
      delegate.close();
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void closeNormal() throws SQLException {
    pooled.enlist(
        () -> {
          throw TestUtil.getNormal();
        });
    try {
      delegate.close();
      fail();
    } catch (final SQLException e) {
      verify(pool).give(pooled);
    }
  }

  @Override
  protected String[] getIgnored() {
    return new String[] {
      "close", "isWrapperFor", "unwrap",
    };
  }
}
