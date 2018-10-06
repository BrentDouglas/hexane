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

import org.junit.Before;
import org.junit.Test;

import javax.sql.PooledConnection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class HexanePooledConnectionTest
    extends PooledConnectionTestBase<PooledConnection, HexanePooledConnection> {

  @Before
  public void setUp() throws Exception {
    super.setUp();
    delegate = mock(PooledConnection.class);
    when(delegate.getConnection()).thenReturn(real);
    pooled = new Pooled<>(pool, delegate, close, StatementCache.INSTANCE);
    conn = new HexanePooledConnection(config, pooled, defaults);
  }

  @Test
  public void getConnectionFatalRemoves() throws Exception {
    conn =
        new HexanePooledConnection(
            Hexane.builder()
                .setExceptionHandler(
                    new ExceptionHandler() {
                      @Override
                      public boolean isConnectionErrorFatal(final SQLException e) {
                        return e.getSQLState() == null
                            || e.getSQLState().startsWith(Util.CONNECTION_ERROR);
                      }
                    })
                .getConfig(),
            pooled,
            defaults);

    super.getConnectionFatalRemoves();
  }
}
