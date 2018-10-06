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

import org.h2.jdbc.JdbcStatement;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class UncheckedStatementTest extends UncheckedTestBase<Statement, UncheckedStatement> {

  public UncheckedStatementTest() {
    super(Statement.class, UncheckedStatement.class);
  }

  Connection connection;

  @Override
  protected UncheckedStatement create(final Terminal terminal, final Statement delegate)
      throws SQLException {
    return new UncheckedStatement<>(terminal, connection = mock(Connection.class), delegate);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(delegate.isWrapperFor(Statement.class));
    assertTrue(delegate.isWrapperFor(UncheckedStatement.class));

    when(real.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(JdbcStatement.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(delegate, delegate.unwrap(Statement.class));
    assertEquals(delegate, delegate.unwrap(UncheckedStatement.class));

    when(real.unwrap(any())).thenReturn(real);

    assertEquals(real, delegate.unwrap(JdbcStatement.class));
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
