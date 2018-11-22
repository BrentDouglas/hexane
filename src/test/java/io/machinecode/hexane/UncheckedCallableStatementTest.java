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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbc.JdbcCallableStatement;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class UncheckedCallableStatementTest
    extends UncheckedTestBase<CallableStatement, UncheckedCallableStatement> {

  public UncheckedCallableStatementTest() {
    super(CallableStatement.class, UncheckedCallableStatement.class);
  }

  Connection connection;

  @Override
  protected UncheckedCallableStatement create(
      final Terminal terminal, final CallableStatement delegate) throws SQLException {
    return new UncheckedCallableStatement(terminal, connection = mock(Connection.class), delegate);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(delegate.isWrapperFor(CallableStatement.class));
    assertTrue(delegate.isWrapperFor(UncheckedCallableStatement.class));

    when(real.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(JdbcCallableStatement.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(delegate, delegate.unwrap(CallableStatement.class));
    assertEquals(delegate, delegate.unwrap(UncheckedCallableStatement.class));

    when(real.unwrap(any())).thenReturn(real);

    assertEquals(real, delegate.unwrap(JdbcCallableStatement.class));
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
