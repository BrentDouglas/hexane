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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLTransientConnectionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test simulates the refill task finishing after we poll
 *
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class BasePoolAddAfterTest extends Assert {

  AtomicInteger val = new AtomicInteger();
  BasePool<Integer> pool;
  volatile Runnable task;

  @Before
  public void setUp() throws Exception {
    final Config config =
        Hexane.builder()
            .setMaintenanceExecutor(cmd -> task = cmd)
            .setCorePoolSize(2)
            .setMaxPoolSize(4)
            .getConfig();
    final Connection conn = mock(Connection.class);
    when(conn.isValid(anyInt())).thenReturn(true);
    pool =
        new BasePool<Integer>(config, Defaults.create(config, conn)) {
          @Override
          protected Integer getConnection() {
            return val.incrementAndGet();
          }

          @Override
          protected Connection getConnection(final Integer item) {
            return conn;
          }

          @Override
          protected AutoCloseable getCloser(final Integer item) {
            return () -> {};
          }
        };
  }

  @Test
  public void refill() throws Exception {
    pool.refill();

    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());
  }

  @Test
  public void take() throws Exception {
    pool.refill();

    final Pooled<Integer> a = pool.take();

    task.run();
    assertEquals(2, pool.getTotal());
    assertEquals(1, pool.getFree());

    final Pooled<Integer> b = pool.take();

    task.run();
    assertEquals(3, pool.getTotal());
    assertEquals(1, pool.getFree());

    final Pooled<Integer> c = pool.take();

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(1, pool.getFree());

    final Pooled<Integer> d = pool.take();

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(0, pool.getFree());

    try {
      pool.take();

      fail();
    } catch (final SQLTransientConnectionException e) {
      task.run();
      assertEquals(4, pool.getTotal());
      assertEquals(0, pool.getFree());
    }

    pool.refill();

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(0, pool.getFree());

    pool.give(a);

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(1, pool.getFree());

    pool.give(b);

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(2, pool.getFree());

    pool.give(c);

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(3, pool.getFree());

    pool.give(d);

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(4, pool.getFree());
  }

  @Test
  public void remove() throws Exception {
    pool.refill();

    final Pooled<Integer> a = pool.take();

    task.run();
    assertEquals(2, pool.getTotal());
    assertEquals(1, pool.getFree());

    final Pooled<Integer> b = pool.take();

    task.run();
    assertEquals(3, pool.getTotal());
    assertEquals(1, pool.getFree());

    final Pooled<Integer> c = pool.take();

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(1, pool.getFree());

    final Pooled<Integer> d = pool.take();

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(0, pool.getFree());

    pool.remove(a);

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(1, pool.getFree());

    pool.give(b);

    task.run();
    assertEquals(4, pool.getTotal());
    assertEquals(2, pool.getFree());

    pool.remove(c);

    task.run();
    assertEquals(3, pool.getTotal());
    assertEquals(2, pool.getFree());

    pool.remove(d);

    task.run();
    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());
  }

  @Test
  public void close() throws Exception {
    pool.refill();
    assertEquals(2, pool.getTotal());
    assertEquals(2, pool.getFree());

    pool.close();

    assertEquals(0, pool.getTotal());
    assertEquals(0, pool.getFree());
  }
}
