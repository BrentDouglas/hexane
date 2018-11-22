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

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class BasePoolTest extends Assert {

  private Connection conn;
  private AutoCloseable closer;
  private BasePool<Connection> pool;
  private Config config;

  @Before
  public void setUp() throws Exception {
    FixedClock.setTime(0);
    Clock.INSTANCE = new FixedClock();
    conn = mock(Connection.class);
    when(conn.isValid(anyInt())).thenReturn(true);
    closer = mock(AutoCloseable.class);
    config =
        Hexane.builder()
            .setMaintenanceExecutor(Runnable::run)
            .setCorePoolSize(2)
            .setMaxPoolSize(4)
            .setConnectionTimeout(10, TimeUnit.SECONDS)
            .setIdleTimeout(10, Clock.getUnit())
            .setLifetimeTimeout(200, Clock.getUnit())
            .getConfig();
    pool =
        new BasePool<Connection>(config, Defaults.create(config, conn)) {
          @Override
          protected Connection getConnection() {
            return conn;
          }

          @Override
          protected Connection getConnection(final Connection item) {
            return item;
          }

          @Override
          protected AutoCloseable getCloser(final Connection item) {
            return closer;
          }
        };
  }

  @Test
  public void expire() throws Exception {
    pool.refill();

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());

    final Pooled<Connection> a = pool.take();
    final Pooled<Connection> b = pool.take();
    final Pooled<Connection> c = pool.take();

    assertEquals(3, pool.getTotal());
    assertEquals(0, pool.getFree());

    a.close(false);
    b.close(false);
    c.close(false);

    assertEquals(3, pool.getTotal());
    assertEquals(3, pool.getFree());

    FixedClock.setTime(11);

    pool.refill();

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());
  }

  @Test
  public void addNew() throws Exception {
    pool.refill();

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());

    pool.addNew(3, config.getMaxPoolSize(), 0);

    // 3 because once after the cas fails it should reload total from the real total
    // and realise we can still fit it into the pool
    assertEquals(3, pool.getTotal());
    assertEquals(3, pool.getFree());

    pool.addNew(4, config.getMaxPoolSize(), 0);

    // 4 because once after the cas fails it should reload total from the real total
    // and realise we can still fit it into the pool
    assertEquals(4, pool.getTotal());
    assertEquals(4, pool.getFree());

    pool.addNew(5, config.getMaxPoolSize(), 0);

    // 4 because once after the cas fails it should reload total from the real total
    // and realise it is full
    assertEquals(4, pool.getTotal());
    assertEquals(4, pool.getFree());
  }

  @Test
  public void lifetime() throws Exception {
    pool.refill();

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());

    final Pooled<Connection> a = pool.take();

    assertEquals(2, pool.getTotal());
    assertEquals(1, pool.getFree());

    FixedClock.setTime(201);
    pool.refill();

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());

    a.close(false);

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());
  }

  @Test
  public void close() throws Exception {
    pool.refill();
    final Pooled<Connection> item = pool.take();
    item.enlist(closer);
    item.close(false);

    pool.close();

    verify(closer).close();
    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());
  }

  @Test
  public void closeWhileTaken() throws Exception {
    pool.refill();
    pool.take().enlist(closer);

    pool.close();

    verify(closer).close();
    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());
  }

  @Test
  public void closeThrows() throws Exception {
    pool.refill();
    doThrow(TestUtil.getNormal()).when(closer).close();
    pool.take().enlist(closer);

    try {
      pool.close();
    } catch (final SQLException e) {
      assertEquals(0, pool.getTotal());
      assertEquals(0, pool.getFree());
    }
  }

  @Test(expected = SQLNonTransientException.class)
  public void takeThrowsWhenClosed() throws Exception {
    pool.close();

    pool.take();
  }

  @Test
  public void removeTotalRightWhenClosed() throws Exception {
    final Pooled<Connection> x = pool.take();

    pool.close();
    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());

    pool.remove(x);
    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());
  }

  @Test
  public void refillIgnoredWhenClosed() throws Exception {
    pool.close();
    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());

    pool.refill();
    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());
  }

  @Test
  public void takeWrapsInterruptAsSQLException() throws Exception {
    final Config config =
        Hexane.builder()
            .setMaintenanceExecutor(
                cmd -> {
                  // Ignore
                })
            .setCorePoolSize(2)
            .setMaxPoolSize(4)
            .setConnectionTimeout(10, TimeUnit.SECONDS)
            .setIdleTimeout(10, Clock.getUnit())
            .setLifetimeTimeout(200, Clock.getUnit())
            .getConfig();
    pool =
        new BasePool<Connection>(config, Defaults.create(config, conn)) {
          @Override
          protected Connection getConnection() {
            return conn;
          }

          @Override
          protected Connection getConnection(final Connection item) {
            return conn;
          }

          @Override
          protected AutoCloseable getCloser(final Connection item) {
            return closer;
          }
        };
    final CountDownLatch start = new CountDownLatch(1);
    final AtomicBoolean finish = new AtomicBoolean(false);
    final Ref<SQLException> exception = new Ref<>();
    final Thread worker =
        new Thread(
            () -> {
              try {
                pool.take();
              } catch (final SQLException e) {
                exception.setVal(e);
              }
              start.countDown();
              while (!finish.get()) {}
            });
    worker.start();
    Thread.sleep(100);
    worker.interrupt();
    start.await(1, TimeUnit.SECONDS);

    assertNotNull(exception.getVal());
    assertTrue(worker.isInterrupted());

    finish.set(true);
  }

  @Test
  public void getConnectionInvalid() throws SQLException {
    when(conn.isValid(anyInt())).thenReturn(false);
    final int total = pool.getTotal();
    final int free = pool.getFree();

    pool.addNew(total, total + 1, 0);

    assertEquals(total, pool.getTotal());
    assertEquals(free, pool.getFree());
  }

  @Test
  public void getConnectionIsValidFails() throws SQLException {
    when(conn.isValid(anyInt())).thenThrow(TestUtil.getNormal());
    final int total = pool.getTotal();
    final int free = pool.getFree();

    pool.addNew(total, total + 1, 0);

    assertEquals(total, pool.getTotal());
    assertEquals(free, pool.getFree());
  }

  @Test
  public void getConnectionFails() throws SQLException {
    pool =
        new BasePool<Connection>(config, Defaults.create(config, conn)) {
          @Override
          protected Connection getConnection() {
            return conn;
          }

          @Override
          protected Connection getConnection(final Connection item) {
            return null;
          }

          @Override
          protected AutoCloseable getCloser(final Connection item) {
            return closer;
          }
        };
    final int total = pool.getTotal();
    final int free = pool.getFree();

    pool.addNew(total, total + 1, 0);

    assertEquals(total, pool.getTotal());
    assertEquals(free, pool.getFree());
  }
}
