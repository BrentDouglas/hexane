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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.jdbc.JdbcResultSet;
import org.junit.Test;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class CheckedResultSetTest extends CheckedTestBase<ResultSet, CheckedResultSet> {

  public CheckedResultSetTest() {
    super(ResultSet.class, CheckedResultSet.class);
  }

  Connection connection;
  CheckedStatement statement;

  @Override
  protected CheckedResultSet create(final Terminal terminal, final ResultSet delegate)
      throws SQLException {
    statement = new CheckedStatement<>(terminal, connection = mock(Connection.class), null);
    return new CheckedResultSet(terminal, statement, delegate);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(delegate.isWrapperFor(ResultSet.class));
    assertTrue(delegate.isWrapperFor(CheckedResultSet.class));

    when(real.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(JdbcResultSet.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(delegate, delegate.unwrap(ResultSet.class));
    assertEquals(delegate, delegate.unwrap(CheckedResultSet.class));

    when(real.unwrap(any())).thenReturn(real);

    assertEquals(real, delegate.unwrap(JdbcResultSet.class));
  }

  @Test
  public void isWrapperForFatalCallsKill() throws SQLException {
    when(real.isWrapperFor(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.isWrapperFor(JdbcResultSet.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void unwrapFatalCallsKill() throws SQLException {
    when(real.unwrap(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.unwrap(JdbcResultSet.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void getStatement() throws SQLException {
    assertEquals(statement, delegate.getStatement());
  }

  @Override
  protected String[] getIgnored() {
    return new String[] {
      "getStatement", "isWrapperFor", "unwrap",
    };
  }
}
