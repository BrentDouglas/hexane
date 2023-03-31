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
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.h2.jdbc.JdbcPreparedStatement;
import org.junit.Test;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class UncheckedPreparedStatementTest
    extends UncheckedTestBase<PreparedStatement, UncheckedPreparedStatement> {

  public UncheckedPreparedStatementTest() {
    super(PreparedStatement.class, UncheckedPreparedStatement.class);
  }

  Connection connection;

  @Override
  protected UncheckedPreparedStatement create(
      final Terminal terminal, final PreparedStatement delegate) throws SQLException {
    return new UncheckedPreparedStatement<>(
        terminal, connection = mock(Connection.class), delegate);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(delegate.isWrapperFor(PreparedStatement.class));
    assertTrue(delegate.isWrapperFor(UncheckedPreparedStatement.class));

    when(real.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(JdbcPreparedStatement.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(delegate, delegate.unwrap(PreparedStatement.class));
    assertEquals(delegate, delegate.unwrap(UncheckedPreparedStatement.class));

    when(real.unwrap(any())).thenReturn(real);

    assertEquals(real, delegate.unwrap(JdbcPreparedStatement.class));
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
