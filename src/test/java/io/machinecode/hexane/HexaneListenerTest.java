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

import io.machinecode.hexane.Config.Builder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class HexaneListenerTest extends Assert {

  private Connection conn;
  private AutoCloseable closer;
  private BasePool<Connection> pool;
  private Config config;
  private HexaneListener listener;
  private Builder builder;

  @Before
  public void setUp() throws Exception {
    FixedClock.setTime(0);
    Clock.INSTANCE = new FixedClock();
    conn = mock(Connection.class);
    closer = mock(AutoCloseable.class);
    builder =
        Hexane.builder()
            .setMaintenanceExecutor(Runnable::run)
            .setCorePoolSize(2)
            .setMaxPoolSize(4)
            .setMaintenanceExecutor(cmd -> {})
            .setListener(listener = mock(HexaneListener.class))
            .setConnectionTimeout(10, Clock.getUnit())
            .setIdleTimeout(10, Clock.getUnit())
            .setLifetimeTimeout(200, Clock.getUnit());
    config = builder.getConfig();
    pool =
        new BasePool<Connection>(config) {
          @Override
          protected Connection getConnection() {
            return conn;
          }

          @Override
          protected AutoCloseable getCloser(final Connection item) {
            return closer;
          }
        };
    Clock.INSTANCE = new FixedClock();
  }

  @Test
  public void onDataSourceCreationBuildDataSource() throws Exception {
    FixedClock.setTime(0L);

    final DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(mock(Connection.class));

    builder.buildDataSource(dataSource);

    verify(listener).onDataSourceCreation();
  }

  @Test
  public void onDataSourceCreationBuildManagedDataSource() throws Exception {
    FixedClock.setTime(0L);

    final DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(mock(Connection.class));

    builder.buildManagedDataSource(dataSource);

    verify(listener).onDataSourceCreation();
  }

  @Test
  public void onDataSourceCreationBuildConnectionPoolDataSource() throws Exception {
    FixedClock.setTime(0L);

    final ConnectionPoolDataSource dataSource = mock(ConnectionPoolDataSource.class);
    final PooledConnection xaConn = mock(PooledConnection.class);
    when(dataSource.getPooledConnection()).thenReturn(xaConn);
    when(xaConn.getConnection()).thenReturn(mock(Connection.class));

    builder.buildConnectionPoolDataSource(dataSource);

    verify(listener).onDataSourceCreation();
  }

  @Test
  public void onDataSourceCreationBuildXADataSource() throws Exception {
    FixedClock.setTime(0L);

    final XADataSource dataSource = mock(XADataSource.class);
    final XAConnection xaConn = mock(XAConnection.class);
    when(dataSource.getXAConnection()).thenReturn(xaConn);
    when(xaConn.getConnection()).thenReturn(mock(Connection.class));

    builder.buildXADataSource(dataSource);

    verify(listener).onDataSourceCreation();
  }

  @Test
  public void onConnectionCreation() throws Exception {
    FixedClock.setTime(0L);

    pool.refill();

    verify(listener, times(2)).onConnectionCreation(0L, Clock.getUnit());

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());
  }

  @Test
  public void onConnectionAcquired() throws Exception {
    FixedClock.setTime(0L);

    pool.refill();

    final Pooled<Connection> a = pool.take();

    verify(listener).onConnectionAcquired(0L, Clock.getUnit());

    assertEquals(2, pool.getTotal());
    assertEquals(1, pool.getFree());
  }

  @Test
  public void onConnectionTimeout() throws Exception {
    FixedClock.setTime(0L);

    try {
      final Pooled<Connection> a = pool.take();
      fail();
    } catch (final SQLException e) {
      // OK
    }

    verify(listener).onConnectionTimeout(0L, Clock.getUnit());

    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());
  }

  @Test
  public void onConnectionReturned() throws Exception {
    FixedClock.setTime(0L);

    pool.refill();

    final Pooled<Connection> a = pool.take();

    verify(listener).onConnectionAcquired(0L, Clock.getUnit());

    assertEquals(2, pool.getTotal());
    assertEquals(1, pool.getFree());

    FixedClock.setTime(10L);

    pool.give(a);

    verify(listener).onConnectionReturned(10L, Clock.getUnit());

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());
  }

  @Test
  public void onConnectionIdleEviction() throws Exception {
    FixedClock.setTime(0L);

    pool.refill();
    pool.addNew(2, config.getMaxPoolSize(), 1L);

    final long time = config.getIdleTimeout() + 1;
    FixedClock.setTime(time);
    pool.refill();

    verify(listener).onConnectionIdleEviction();

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());
  }

  @Test
  public void onConnectionLifetimeEviction() throws Exception {
    FixedClock.setTime(0L);

    pool.refill();

    final long time = config.getLifetimeTimeout() + 1;
    FixedClock.setTime(time);
    pool.refill();

    verify(listener, times(2)).onConnectionLifetimeEviction();

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());
  }

  @Test
  public void onConnectionBrokenEviction() throws Exception {
    FixedClock.setTime(0L);

    pool.refill();

    final Pooled<Connection> a = pool.take();
    pool.remove(a);

    verify(listener).onConnectionErrorEviction();

    assertEquals(1, pool.getTotal());
    assertEquals(1, pool.getFree());
  }

  @Test
  public void onDataSourceClosed() throws Exception {
    FixedClock.setTime(0L);

    pool.refill();
    pool.close();

    verify(listener, times(2)).onDataSourceClosedEviction();

    verify(listener).onDataSourceClosed();

    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());
  }
}
