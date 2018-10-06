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

import io.machinecode.hexane.HexaneConnection.ClosedConnection;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class ClosedConnectionTest extends Assert {

  @Test
  public void closeIsNoop() throws SQLException {
    ClosedConnection.INSTANCE.close();
  }

  @Test
  public void abortIsNoop() throws SQLException {
    ClosedConnection.INSTANCE.abort(null);
  }

  @Test
  public void toStringWorks() throws SQLException {
    assertNotNull(ClosedConnection.INSTANCE.toString());
  }

  @Test
  public void equalsWorks() throws SQLException {
    assertEquals(ClosedConnection.INSTANCE, ClosedConnection.INSTANCE);
  }

  @Test
  public void hashCodeWorks() throws SQLException {
    ClosedConnection.INSTANCE.hashCode();
  }

  @Test
  public void isValidWorks() throws SQLException {
    assertFalse(ClosedConnection.INSTANCE.isValid(0));
  }

  @Test
  public void isWrapperFor() throws SQLException {
    assertTrue(ClosedConnection.INSTANCE.isWrapperFor(ClosedConnection.class));
    assertTrue(ClosedConnection.INSTANCE.isWrapperFor(Connection.class));
    assertFalse(ClosedConnection.INSTANCE.isWrapperFor(CheckedConnection.class));
  }

  @Test
  public void unwrap() throws SQLException {
    assertEquals(
        ClosedConnection.INSTANCE, ClosedConnection.INSTANCE.unwrap(ClosedConnection.class));
    assertEquals(ClosedConnection.INSTANCE, ClosedConnection.INSTANCE.unwrap(Connection.class));

    try {
      ClosedConnection.INSTANCE.unwrap(CheckedConnection.class);
      fail();
    } catch (final SQLException e) {
      //
    }
  }

  @Test(expected = SQLException.class)
  public void anythingElseThrows() throws SQLException {
    ClosedConnection.INSTANCE.getAutoCommit();
  }
}
