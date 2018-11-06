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

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
@BenchmarkMode({Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1)
@Measurement(iterations = 10)
@Timeout(time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class SelectOneThroughputBench extends BenchBase {

  @Param({
      //    "pg_9_3",
      //    "pg_9_4",
      //    "pg_9_5",
      //    "pg_9_6",
      //      "pg_10_5",
      "pg_11_0",
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

  @Param({"hexane", "hikari", "agroal", "dbcp2", "c3p0", "tomcat"})
  String poolType;

  @Param({"100"})
  int conns;

  @Param({"1"})
  int statements;

  @Param({"1"})
  int results;

  @Override
  protected String getName() {
    return name;
  }

  @Override
  protected String getPoolType() {
    return poolType;
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
