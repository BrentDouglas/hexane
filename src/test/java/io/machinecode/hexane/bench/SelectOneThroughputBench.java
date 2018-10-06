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
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DataSourceConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

import javax.sql.XADataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
@BenchmarkMode({Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1)
@Measurement(iterations = 10)
@Timeout(time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class SelectOneThroughputBench {

  @Param({
    //    "pg_9_3",
    //    "pg_9_4",
    //    "pg_9_5",
    //    "pg_9_6",
    "pg_10_5",
    //    "mysql_5_5",
    //    "mysql_5_6",
    //    "mysql_5_7",
    //    "mysql_8_0",
    //    "mariadb_5_5",
    //    "mariadb_10_0",
    //    "mariadb_10_1",
    //    "mariadb_10_2",
    //    "mariadb_10_3",
    //    "h2",
    //    "hsqldb",
    //    "derby"
  })
  String name;

  @Param({"hexane", "hikari", "agroal", "dbcp2", "c3p0"})
  String poolType;

  @Param({"100"})
  int conns;

  @Param({"1"})
  int statements;

  @Param({"1"})
  int results;

  private XADataSource db;
  private AutoCloseable close;

  @Setup(Level.Trial)
  public void setUp() throws Exception {
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
    }
  }

  @TearDown(Level.Trial)
  public void teadDown() throws Exception {
    close.close();
  }

  @Benchmark
  @Threads(value = 50)
  public long selectOne() throws SQLException {
    long ret = 0;
    for (int i = 0; i < conns; i++) {
      try (final Connection conn = db.getXAConnection().getConnection()) {
        for (int j = 0; j < statements; j++) {
          try (final PreparedStatement statement = conn.prepareStatement("SELECT 1")) {
            for (int k = 0; k < results; k++) {
              try (final ResultSet result = statement.executeQuery()) {
                ret += result.next() ? result.getInt(1) : 0;
              }
            }
          }
        }
      }
    }
    return ret;
  }
}
