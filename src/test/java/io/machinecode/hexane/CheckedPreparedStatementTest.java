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

import org.h2.jdbc.JdbcPreparedStatement;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class CheckedPreparedStatementTest
    extends CheckedTestBase<PreparedStatement, CheckedPreparedStatement> {

  public CheckedPreparedStatementTest() {
    super(PreparedStatement.class, CheckedPreparedStatement.class);
  }

  Connection connection;

  @Override
  protected CheckedPreparedStatement create(
      final Terminal terminal, final PreparedStatement delegate) throws SQLException {
    return new CheckedPreparedStatement<>(terminal, connection = mock(Connection.class), delegate);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(delegate.isWrapperFor(PreparedStatement.class));
    assertTrue(delegate.isWrapperFor(CheckedPreparedStatement.class));

    when(real.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(JdbcPreparedStatement.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(delegate, delegate.unwrap(PreparedStatement.class));
    assertEquals(delegate, delegate.unwrap(CheckedPreparedStatement.class));

    when(real.unwrap(any())).thenReturn(real);

    assertEquals(real, delegate.unwrap(JdbcPreparedStatement.class));
  }

  @Test
  public void isWrapperForFatalCallsKill() throws SQLException {
    when(real.isWrapperFor(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.isWrapperFor(JdbcPreparedStatement.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void unwrapFatalCallsKill() throws SQLException {
    when(real.unwrap(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.unwrap(JdbcPreparedStatement.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void getConnection() throws SQLException {
    assertEquals(connection, delegate.getConnection());
  }

  @Override
  protected String[] getIgnored() {
    return new String[] {
      "getConnection", "isWrapperFor", "unwrap",
    };
  }
}
