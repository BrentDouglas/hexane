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

import org.h2.jdbc.JdbcDatabaseMetaData;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class CheckedDatabaseMetaDataTest
    extends CheckedTestBase<DatabaseMetaData, CheckedDatabaseMetaData> {

  public CheckedDatabaseMetaDataTest() {
    super(DatabaseMetaData.class, CheckedDatabaseMetaData.class);
  }

  Connection connection;

  @Override
  protected CheckedDatabaseMetaData create(final Terminal terminal, final DatabaseMetaData delegate)
      throws Exception {
    when(delegate.getAttributes(any(), any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getBestRowIdentifier(any(), any(), any(), anyInt(), anyBoolean()))
        .thenReturn(mock(ResultSet.class));
    when(delegate.getCatalogs()).thenReturn(mock(ResultSet.class));
    when(delegate.getClientInfoProperties()).thenReturn(mock(ResultSet.class));
    when(delegate.getColumnPrivileges(any(), any(), any(), any()))
        .thenReturn(mock(ResultSet.class));
    when(delegate.getColumns(any(), any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getCrossReference(any(), any(), any(), any(), any(), any()))
        .thenReturn(mock(ResultSet.class));
    when(delegate.getExportedKeys(any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getFunctionColumns(any(), any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getFunctions(any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getImportedKeys(any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getIndexInfo(any(), any(), any(), anyBoolean(), anyBoolean()))
        .thenReturn(mock(ResultSet.class));
    when(delegate.getPrimaryKeys(any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getProcedureColumns(any(), any(), any(), any()))
        .thenReturn(mock(ResultSet.class));
    when(delegate.getProcedures(any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getPseudoColumns(any(), any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getSchemas()).thenReturn(mock(ResultSet.class));
    when(delegate.getSchemas(any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getSuperTables(any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getSuperTypes(any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getTablePrivileges(any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getTables(any(), any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getTableTypes()).thenReturn(mock(ResultSet.class));
    when(delegate.getTypeInfo()).thenReturn(mock(ResultSet.class));
    when(delegate.getUDTs(any(), any(), any(), any())).thenReturn(mock(ResultSet.class));
    when(delegate.getVersionColumns(any(), any(), any())).thenReturn(mock(ResultSet.class));
    return new CheckedDatabaseMetaData(terminal, connection = mock(Connection.class), delegate);
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(delegate.isWrapperFor(DatabaseMetaData.class));
    assertTrue(delegate.isWrapperFor(CheckedDatabaseMetaData.class));

    when(real.isWrapperFor(any())).thenReturn(true);

    assertTrue(delegate.isWrapperFor(JdbcDatabaseMetaData.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(delegate, delegate.unwrap(DatabaseMetaData.class));
    assertEquals(delegate, delegate.unwrap(CheckedDatabaseMetaData.class));

    when(real.unwrap(any())).thenReturn(real);

    assertEquals(real, delegate.unwrap(JdbcDatabaseMetaData.class));
  }

  @Test
  public void isWrapperForFatalCallsKill() throws SQLException {
    when(real.isWrapperFor(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.isWrapperFor(JdbcDatabaseMetaData.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void unwrapFatalCallsKill() throws SQLException {
    when(real.unwrap(any())).thenThrow(TestUtil.getFatalState());
    try {
      delegate.unwrap(JdbcDatabaseMetaData.class);
      fail();
    } catch (final SQLException e) {
      verify(terminal).kill(e);
    }
  }

  @Test
  public void getDriverMajorVersion() throws SQLException {
    delegate.getDriverMajorVersion();
    verify(real).getDriverMajorVersion();
  }

  @Test
  public void getDriverMinorVersion() throws SQLException {
    delegate.getDriverMinorVersion();
    verify(real).getDriverMinorVersion();
  }

  @Override
  protected String[] getIgnored() {
    return new String[] {
      "getDriverMajorVersion", "getDriverMinorVersion", "isWrapperFor", "unwrap",
    };
  }
}
