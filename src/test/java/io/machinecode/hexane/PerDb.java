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

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
@RunWith(Parameterized.class)
public class PerDb extends TestBase {

  @Parameters(name = "{0}")
  public static List<Setup[]> getDataSources() throws Exception {
    dataSources = loadSetup(dataSources, System.getProperties());
    return dataSources.stream().map(it -> new Setup[] {it}).collect(Collectors.toList());
  }

  @Parameter public Setup db;

  Connection getConnection() throws Exception {
    return db.getXaDataSource().getXAConnection().getConnection();
  }

  boolean isType(final String... names) {
    for (final String name : names) {
      if (db.name.startsWith(name)) {
        return true;
      }
    }
    return false;
  }
}
