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

import org.h2.jdbc.JdbcResultSetMetaData;
import org.junit.Test;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class CheckedResultSetMetaDataTest
    extends CheckedTestBase<ResultSetMetaData, CheckedResultSetMetaData> {

  public CheckedResultSetMetaDataTest() {
    super(ResultSetMetaData.class, CheckedResultSetMetaData.class);
  }

  @Override
  protected CheckedResultSetMetaData create(
      final Terminal terminal, final ResultSetMetaData delegate) throws SQLException {
    return new CheckedResultSetMetaData(terminal, delegate);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(delegate.isWrapperFor(ResultSetMetaData.class));
    assertTrue(delegate.isWrapperFor(CheckedResultSetMetaData.class));

    when(real.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(JdbcResultSetMetaData.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(delegate, delegate.unwrap(ResultSetMetaData.class));
    assertEquals(delegate, delegate.unwrap(CheckedResultSetMetaData.class));

    when(real.unwrap(any())).thenReturn(real);

    assertEquals(real, delegate.unwrap(JdbcResultSetMetaData.class));
  }

  @Test
  public void isWrapperForFatalCallsKill() throws SQLException {
    when(real.isWrapperFor(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.isWrapperFor(JdbcResultSetMetaData.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void unwrapFatalCallsKill() throws SQLException {
    when(real.unwrap(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.unwrap(JdbcResultSetMetaData.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Override
  protected String[] getIgnored() {
    return new String[] {
      "isWrapperFor", "unwrap",
    };
  }
}
