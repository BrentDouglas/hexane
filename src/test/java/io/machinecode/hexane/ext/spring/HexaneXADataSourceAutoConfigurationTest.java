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

import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.junit.BeforeClass;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
@SpringBootTest(
    classes = {
      HexaneXADataSourceAutoConfiguration.class,
      JtaAutoConfiguration.class,
    },
    properties = {
      "spring.datasource.type=io.machinecode.hexane.HexaneXADataSource",
      "spring.datasource.hexane.dataSourceClassName=org.hsqldb.jdbc.pool.JDBCXADataSource",
      "spring.datasource.hexane.validationTimeout=1000",
      "spring.datasource.hexane.validationTimeoutUnit=MILLISECONDS",
      "spring.datasource.hexane.maxPoolSize=2",
      "spring.datasource.hexane.corePoolSize=1",
      "spring.datasource.hexane.url=jdbc:hsqldb:mem:HexaneXADataSourceAutoConfigurationTest",
      "spring.jta.atomikos.datasource.unique-resource-name=test",
    })
public class HexaneXADataSourceAutoConfigurationTest extends SpringTestBase {

  @Inject DataSource db;

  @BeforeClass
  public static void beforeClass() throws Exception {
    final String path = System.getProperty("spring.datasource.hexane.jndi-name");
    if (path != null) {
      when(Factory.INSTANCE.lookup(path)).thenReturn(new JDBCXADataSource());
    }
  }

  @Override
  Connection getConnection() throws SQLException {
    return db.getConnection();
  }
}
