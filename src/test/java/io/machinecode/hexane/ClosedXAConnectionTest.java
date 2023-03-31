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

import io.machinecode.hexane.HexaneXAConnection.ClosedXAConnection;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class ClosedXAConnectionTest extends Assert {

  @Test
  public void closeIsNoop() throws SQLException {
    ClosedXAConnection.INSTANCE.close();
  }

  @Test
  public void toStringWorks() throws SQLException {
    assertNotNull(ClosedXAConnection.INSTANCE.toString());
  }

  @Test
  public void equalsWorks() throws SQLException {
    assertEquals(ClosedXAConnection.INSTANCE, ClosedXAConnection.INSTANCE);
  }

  @Test
  public void hashCodeWorks() throws SQLException {
    ClosedXAConnection.INSTANCE.hashCode();
  }

  @Test
  public void listenersAreNoop() throws SQLException {
    ClosedXAConnection.INSTANCE.addConnectionEventListener(null);
    ClosedXAConnection.INSTANCE.removeConnectionEventListener(null);
    ClosedXAConnection.INSTANCE.addStatementEventListener(null);
    ClosedXAConnection.INSTANCE.removeStatementEventListener(null);
  }

  @Test(expected = SQLException.class)
  public void getConnectionThrows() throws SQLException {
    ClosedXAConnection.INSTANCE.getConnection();
  }

  @Test(expected = SQLException.class)
  public void getXAResourceThrows() throws SQLException {
    ClosedXAConnection.INSTANCE.getXAResource();
  }
}
