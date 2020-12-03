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
package io.machinecode.hexane.bench;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.agroal.api.configuration.supplier.AgroalConnectionPoolConfigurationSupplier;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import io.agroal.pool.DataSource;
import io.machinecode.hexane.Hexane;
import io.machinecode.hexane.HexaneXADataSource;
import io.machinecode.hexane.TestBase;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.sql.XADataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DataSourceConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
abstract class BenchBase {

  XADataSource db;
  AutoCloseable close;

  protected abstract String getName();

  protected abstract String getPoolType();

  @Setup(Level.Trial)
  public void setUp() throws Exception {
    final String name = getName();
    final String poolType = getPoolType();
    final TestBase.Setup setup = TestBase.loadSetup(name, System.getProperties());
    switch (poolType) {
      case "hexane":
        {
          final HexaneXADataSource db =
              Hexane.builder()
                  .setUser(setup.user)
                  .setPassword(setup.password)
                  .setConnectionTimeout(1000, TimeUnit.MILLISECONDS)
                  .setValidationTimeout(1000, TimeUnit.MILLISECONDS)
                  .setMaxPoolSize(20)
                  .setCorePoolSize(10)
                  .buildXADataSource(setup.getXaDataSource());
          this.db = db;
          this.close = db;
          break;
        }
      case "hikari":
        {
          final HikariConfig config = new HikariConfig();
          config.setConnectionTimeout(1000);
          config.setValidationTimeout(1000);
          config.setMaximumPoolSize(20);
          config.setDataSource(setup.getDataSource());
          final HikariDataSource db = new HikariDataSource(config);
          this.db = new TestXADataSource(db);
          this.close = db;
          break;
        }
      case "agroal":
        {
          final AgroalDataSourceConfigurationSupplier config =
              new AgroalDataSourceConfigurationSupplier();
          final AgroalConnectionPoolConfigurationSupplier pool =
              config.connectionPoolConfiguration();
          pool.maxSize(20);
          pool.initialSize(10);
          pool.acquisitionTimeout(Duration.ofMillis(1000));
          pool.validationTimeout(Duration.ofMillis(1000));
          pool.connectionFactoryConfiguration()
              .principal(new NamePrincipal(setup.user))
              .credential(new SimplePassword(setup.password))
              .jdbcUrl(setup.getUrl());
          final DataSource db = new DataSource(config.get());
          this.db = new TestXADataSource(db);
          this.close = db;
          break;
        }
      case "c3p0":
        {
          final ComboPooledDataSource db = new ComboPooledDataSource();
          db.setJdbcUrl(setup.getUrl());
          db.setUser(setup.user);
          db.setPassword(setup.password);
          db.setMaxPoolSize(20);
          db.setInitialPoolSize(10);
          db.setCheckoutTimeout(1000);
          db.setLoginTimeout(1000);
          this.db = new TestXADataSource(db);
          this.close = db::close;
          break;
        }
      case "dbcp2":
        {
          final ConnectionFactory connectionFactory =
              new DataSourceConnectionFactory(setup.getDataSource());
          final PoolableConnectionFactory poolableConnectionFactory =
              new PoolableConnectionFactory(connectionFactory, null);
          final GenericObjectPool<PoolableConnection> connectionPool =
              new GenericObjectPool<>(poolableConnectionFactory);
          connectionPool.setMaxTotal(20);
          connectionPool.setMaxWaitMillis(1000);
          poolableConnectionFactory.setPool(connectionPool);
          final PoolingDataSource<PoolableConnection> db = new PoolingDataSource<>(connectionPool);
          this.db = new TestXADataSource(db);
          this.close = db;
          break;
        }
      case "tomcat":
        {
          final org.apache.tomcat.jdbc.pool.DataSource db =
              new org.apache.tomcat.jdbc.pool.DataSource();
          db.setUrl(setup.getUrl());
          db.setUsername(setup.user);
          db.setPassword(setup.password);
          db.setMinIdle(10);
          db.setMaxActive(20);
          db.setMaxWait(1000);
          db.setLoginTimeout(1000);
          this.db = new TestXADataSource(db);
          this.close = db::close;
          break;
        }
    }
  }

  @TearDown(Level.Trial)
  public void tearDown() throws Exception {
    close.close();
  }
}
