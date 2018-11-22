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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Assert;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class StatementKeyTest extends Assert {

  @Test
  public void remove() throws SQLException {
    final StatementKey key =
        StatementKey.create(
            "  select 1  ",
            true,
            ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_READ_ONLY,
            ResultSet.HOLD_CURSORS_OVER_COMMIT,
            Statement.RETURN_GENERATED_KEYS,
            new String[] {"a"},
            new int[1]);

    assertEquals(
        "StatementKey{"
            + "type=CallableStatement,"
            + " resultSetType=TYPE_FORWARD_ONLY,"
            + " resultSetConcurrency=CONCUR_READ_ONLY,"
            + " resultSetHoldability=HOLD_CURSORS_OVER_COMMIT,"
            + " keys=RETURN_GENERATED_KEYS,"
            + " sqlLength=8,"
            + " columnNames=String[1],"
            + " columnIndices=int[1],"
            + " hash=pzW-GdRnJ2f2XASgyDLSuFeqMRE="
            + "}",
        key.toString());
  }
}
