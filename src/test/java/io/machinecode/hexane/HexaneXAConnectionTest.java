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

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class HexaneXAConnectionTest
    extends PooledConnectionTestBase<XAConnection, HexaneXAConnection> {

  XAResource resource;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    delegate = mock(XAConnection.class);
    resource = mock(XAResource.class);
    when(delegate.getConnection()).thenReturn(real);
    when(delegate.getXAResource()).thenReturn(resource);
    pooled = new Pooled<>(pool, delegate, real, close, StatementCache.INSTANCE);
    conn = new HexaneXAConnection(config, pooled, defaults);
  }

  @Test
  public void getXAResource() throws Exception {
    final XAResource resource = conn.getXAResource();

    assertSame(this.resource, resource);
  }
}
