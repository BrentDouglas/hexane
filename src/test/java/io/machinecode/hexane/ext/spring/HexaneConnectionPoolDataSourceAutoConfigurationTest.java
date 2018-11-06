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
package io.machinecode.hexane.ext.spring;

import io.machinecode.hexane.HexaneConnectionPoolDataSource;
import org.hsqldb.jdbc.pool.JDBCPooledDataSource;
import org.junit.After;
import org.junit.BeforeClass;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
@SpringBootTest(
    classes = {
      HexaneConnectionPoolDataSourceAutoConfiguration.class,
    },
    properties = {
      "spring.datasource.type=io.machinecode.hexane.HexaneConnectionPoolDataSource",
      "spring.datasource.hexane.dataSourceClassName=org.hsqldb.jdbc.pool.JDBCPooledDataSource",
      "spring.datasource.hexane.validationTimeout=1000",
      "spring.datasource.hexane.validationTimeoutUnit=MILLISECONDS",
      "spring.datasource.hexane.maxPoolSize=2",
      "spring.datasource.hexane.corePoolSize=1",
      "spring.datasource.hexane.url=jdbc:hsqldb:mem:HexaneConnectionPoolDataSourceAutoConfigurationTest",
    })
public class HexaneConnectionPoolDataSourceAutoConfigurationTest extends SpringTestBase {

  @Inject HexaneConnectionPoolDataSource db;

  @BeforeClass
  public static void beforeClass() throws Exception {
    final String path = System.getProperty("spring.datasource.hexane.jndi-name");
    if (path != null) {
      when(Factory.INSTANCE.lookup(path)).thenReturn(new JDBCPooledDataSource());
    }
  }

  @After
  public void tearDown() throws Exception {
    db.close();
  }

  @Override
  Connection getConnection() throws SQLException {
    return db.getConnection();
  }
}
