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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import org.junit.Assert;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class ConfigTest extends Assert {

  @Test
  public void addOptionsWork() {
    final Config config =
        Hexane.builder()
            .setConnectionTimeout(1, TimeUnit.MILLISECONDS)
            .setIdleTimeout(1, TimeUnit.MILLISECONDS)
            .setLifetimeTimeout(1, TimeUnit.MILLISECONDS)
            .setValidationTimeout(1, TimeUnit.MILLISECONDS)
            .setMaxPoolSize(2)
            .setCorePoolSize(2)
            .setUser("foo")
            .setPassword("bar")
            .setMaintenanceExecutor(Runnable::run)
            .setAutoCommit(true)
            .setHoldability(HoldabilityType.CLOSE_CURSORS_AT_COMMIT)
            .setReadOnly(true)
            .setTransactionIsolation(TransactionIsolationType.TRANSACTION_READ_COMMITTED)
            .setCatalog("test")
            .setSchema("test")
            .setTypeMap(Collections.emptyMap())
            .setClientInfo(new Properties())
            .setNetworkTimeout(1_000)
            .setNetworkTimeoutExecutor(Runnable::run)
            .getConfig();
  }

  @Test(expected = NullPointerException.class)
  public void setConnectionTimeoutRequiresUnit() {
    final Config config = Hexane.builder().setConnectionTimeout(1, null).getConfig();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setConnectionTimeoutWrongNumber() {
    final Config config =
        Hexane.builder().setConnectionTimeout(-1, TimeUnit.MILLISECONDS).getConfig();
  }

  @Test(expected = NullPointerException.class)
  public void setIdleTimeoutRequiresUnit() {
    final Config config = Hexane.builder().setIdleTimeout(1, null).getConfig();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setIdleTimeoutWrongNumber() {
    final Config config = Hexane.builder().setIdleTimeout(-1, TimeUnit.MILLISECONDS).getConfig();
  }

  @Test(expected = NullPointerException.class)
  public void setValidationTimeoutRequiresUnit() {
    final Config config = Hexane.builder().setValidationTimeout(1, null).getConfig();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setValidationTimeoutWrongNumber() {
    final Config config =
        Hexane.builder().setValidationTimeout(-1, TimeUnit.MILLISECONDS).getConfig();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setValidationTimeoutTooHigh() {
    final Config config =
        Hexane.builder().setValidationTimeout(Integer.MAX_VALUE, TimeUnit.DAYS).getConfig();
  }

  @Test(expected = NullPointerException.class)
  public void setLifetimeTimeoutRequiresUnit() {
    final Config config = Hexane.builder().setLifetimeTimeout(1, null).getConfig();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setLifetimeTimeoutWrongNumber() {
    final Config config =
        Hexane.builder().setLifetimeTimeout(-1, TimeUnit.MILLISECONDS).getConfig();
  }

  @Test(expected = SQLNonTransientException.class)
  public void buildDataSourceRequiresValidationTimeout() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(mock(Connection.class));
    final DataSource ret =
        Hexane.builder().setCorePoolSize(2).setMaxPoolSize(4).buildDataSource(dataSource);
  }

  @Test(expected = SQLNonTransientException.class)
  public void buildDataSourceRequiresCorePoolSize() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(mock(Connection.class));
    final DataSource ret =
        Hexane.builder()
            .setValidationTimeout(1, TimeUnit.SECONDS)
            .setMaxPoolSize(4)
            .buildDataSource(dataSource);
  }

  @Test(expected = SQLNonTransientException.class)
  public void buildDataSourceRequiresMaxPoolSize() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(mock(Connection.class));
    final DataSource ret =
        Hexane.builder()
            .setValidationTimeout(1, TimeUnit.SECONDS)
            .setCorePoolSize(2)
            .buildDataSource(dataSource);
  }

  @Test
  public void buildDataSource() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(mock(Connection.class));
    final DataSource ret =
        Hexane.builder()
            .setValidationTimeout(1, TimeUnit.SECONDS)
            .setCorePoolSize(2)
            .setMaxPoolSize(4)
            .buildDataSource(dataSource);
  }

  @Test
  public void buildManagedDataSource() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(mock(Connection.class));
    final ConnectionPoolDataSource ret =
        Hexane.builder()
            .setValidationTimeout(1, TimeUnit.SECONDS)
            .setCorePoolSize(2)
            .setMaxPoolSize(4)
            .buildManagedDataSource(dataSource);
  }

  @Test
  public void buildConnectionPoolDataSource() throws SQLException {
    final ConnectionPoolDataSource dataSource = mock(ConnectionPoolDataSource.class);
    final PooledConnection conn = mock(PooledConnection.class);
    when(dataSource.getPooledConnection()).thenReturn(conn);
    when(conn.getConnection()).thenReturn(mock(Connection.class));
    final ConnectionPoolDataSource ret =
        Hexane.builder()
            .setValidationTimeout(1, TimeUnit.SECONDS)
            .setCorePoolSize(2)
            .setMaxPoolSize(4)
            .buildConnectionPoolDataSource(dataSource);
  }

  @Test
  public void buildXADataSource() throws SQLException {
    final XADataSource dataSource = mock(XADataSource.class);
    final XAConnection conn = mock(XAConnection.class);
    when(dataSource.getXAConnection()).thenReturn(conn);
    when(conn.getConnection()).thenReturn(mock(Connection.class));
    final XADataSource ret =
        Hexane.builder()
            .setValidationTimeout(1, TimeUnit.SECONDS)
            .setCorePoolSize(2)
            .setMaxPoolSize(4)
            .buildXADataSource(dataSource);
  }
}
