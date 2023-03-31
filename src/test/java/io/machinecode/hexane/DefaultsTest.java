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
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class DefaultsTest extends PerDb {

  @Test
  public void initialize() throws Exception {
    try (final Connection conn = getConnection()) {
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);

      defaults.initialize(conn);

      assertEquals(defaults.isAutoCommit(), conn.getAutoCommit());
      assertEquals(defaults.isReadOnly(), conn.isReadOnly());
      assertEquals(defaults.getCatalog(), conn.getCatalog());
      assertEquals(defaults.getSchema(), conn.getSchema());
      assertEquals(defaults.getHoldability(), conn.getHoldability());
      assertEquals(defaults.getClientInfo(), conn.getClientInfo());
      if (!isType(DbType.DERBY)) {
        assertEquals(defaults.getNetworkTimeout(), conn.getNetworkTimeout());
      }
      assertEquals(defaults.getTransactionIsolation(), conn.getTransactionIsolation());
      assertEquals(defaults.getTypeMap(), conn.getTypeMap());
    }
  }

  @Test
  public void initializeRollback() throws Exception {
    try (final Connection conn = getConnection()) {
      final Defaults defaults =
          Defaults.create(Hexane.builder().setAutoCommit(false).getConfig(), conn);

      defaults.initialize(conn);

      assertEquals(defaults.isAutoCommit(), conn.getAutoCommit());
      assertEquals(defaults.isReadOnly(), conn.isReadOnly());
      assertEquals(defaults.getCatalog(), conn.getCatalog());
      assertEquals(defaults.getSchema(), conn.getSchema());
      assertEquals(defaults.getHoldability(), conn.getHoldability());
      assertEquals(defaults.getClientInfo(), conn.getClientInfo());
      if (!isType(DbType.DERBY)) {
        assertEquals(defaults.getNetworkTimeout(), conn.getNetworkTimeout());
      }
      assertEquals(defaults.getTransactionIsolation(), conn.getTransactionIsolation());
      assertEquals(defaults.getTypeMap(), conn.getTypeMap());
    }
  }

  @Test
  public void setAutoCommit() throws Exception {
    try (final Connection conn = getConnection()) {
      final boolean current = conn.getAutoCommit();
      final boolean altered = !current;
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      flags = defaults.setAutoCommit(conn, flags, altered);
      assertEquals(altered, conn.getAutoCommit());

      flags = defaults.reset(conn, flags);
      assertEquals(current, conn.getAutoCommit());
    }
  }

  @Test
  public void setAutoCommitDefault() throws Exception {
    try (final Connection conn = getConnection()) {
      final boolean current = conn.getAutoCommit();
      final boolean altered = !current;
      final Defaults defaults =
          Defaults.create(Hexane.builder().setAutoCommit(altered).getConfig(), conn);
      int flags = 0;

      flags = defaults.setAutoCommit(conn, flags, altered);
      assertEquals(altered, conn.getAutoCommit());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.getAutoCommit());

      flags = defaults.setAutoCommit(conn, flags, current);
      assertEquals(current, conn.getAutoCommit());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.getAutoCommit());
    }
  }

  @Test
  public void setReadOnly() throws Exception {
    if (isType(DbType.H2)) {
      // setReadOnly is ignored
      return;
    }
    try (final Connection conn = getConnection()) {
      final boolean current = conn.isReadOnly();
      final boolean altered = !current;
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      flags = defaults.setReadOnly(conn, flags, altered);
      assertEquals(altered, conn.isReadOnly());

      flags = defaults.reset(conn, flags);
      assertEquals(current, conn.isReadOnly());
    }
  }

  @Test
  public void setReadOnlyDefault() throws Exception {
    if (isType(DbType.H2)) {
      // setReadOnly is ignored
      return;
    }
    try (final Connection conn = getConnection()) {
      final boolean current = conn.isReadOnly();
      final boolean altered = !current;
      final Defaults defaults =
          Defaults.create(Hexane.builder().setReadOnly(altered).getConfig(), conn);
      int flags = 0;

      flags = defaults.setReadOnly(conn, flags, altered);
      assertEquals(altered, conn.isReadOnly());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.isReadOnly());

      flags = defaults.setReadOnly(conn, flags, current);
      assertEquals(current, conn.isReadOnly());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.isReadOnly());
    }
  }

  @Test
  public void setHoldability() throws Exception {
    if (isType(DbType.MARIADB, DbType.MYSQL)) {
      // setHoldability is ignored
      return;
    }
    try (final Connection conn = getConnection()) {
      final int current = conn.getHoldability();
      final int altered =
          current == ResultSet.HOLD_CURSORS_OVER_COMMIT
              ? ResultSet.CLOSE_CURSORS_AT_COMMIT
              : ResultSet.HOLD_CURSORS_OVER_COMMIT;
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      flags = defaults.setHoldability(conn, flags, altered);
      assertEquals(altered, conn.getHoldability());

      flags = defaults.reset(conn, flags);
      assertEquals(current, conn.getHoldability());
    }
  }

  @Test
  public void setHoldabilityDefault() throws Exception {
    if (isType(DbType.MARIADB, DbType.MYSQL)) {
      // setHoldability is ignored
      return;
    }
    try (final Connection conn = getConnection()) {
      final int current = conn.getHoldability();
      final HoldabilityType altered =
          current == ResultSet.HOLD_CURSORS_OVER_COMMIT
              ? HoldabilityType.CLOSE_CURSORS_AT_COMMIT
              : HoldabilityType.HOLD_CURSORS_OVER_COMMIT;
      final Defaults defaults =
          Defaults.create(Hexane.builder().setHoldability(altered).getConfig(), conn);
      int flags = 0;

      flags = defaults.setHoldability(conn, flags, altered.value);
      assertEquals(altered.value, conn.getHoldability());

      flags = defaults.reset(conn, flags);
      assertEquals(altered.value, conn.getHoldability());

      flags = defaults.setHoldability(conn, flags, current);
      assertEquals(current, conn.getHoldability());

      flags = defaults.reset(conn, flags);
      assertEquals(altered.value, conn.getHoldability());
    }
  }

  @Test
  public void setTransactionIsolation() throws Exception {
    try (final Connection conn = getConnection()) {
      final int current = conn.getTransactionIsolation();
      final int altered =
          current == Connection.TRANSACTION_SERIALIZABLE
              ? Connection.TRANSACTION_READ_COMMITTED
              : Connection.TRANSACTION_SERIALIZABLE;
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      flags = defaults.setTransactionIsolation(conn, flags, altered);
      assertEquals(altered, conn.getTransactionIsolation());

      flags = defaults.reset(conn, flags);
      assertEquals(current, conn.getTransactionIsolation());
    }
  }

  @Test
  public void setTransactionIsolationDefault() throws Exception {
    try (final Connection conn = getConnection()) {
      final int current = conn.getTransactionIsolation();
      final TransactionIsolationType altered =
          current == Connection.TRANSACTION_SERIALIZABLE
              ? TransactionIsolationType.TRANSACTION_READ_COMMITTED
              : TransactionIsolationType.TRANSACTION_SERIALIZABLE;
      final Defaults defaults =
          Defaults.create(Hexane.builder().setTransactionIsolation(altered).getConfig(), conn);
      int flags = 0;

      flags = defaults.setTransactionIsolation(conn, flags, altered.value);
      assertEquals(altered.value, conn.getTransactionIsolation());

      flags = defaults.reset(conn, flags);
      assertEquals(altered.value, conn.getTransactionIsolation());

      flags = defaults.setTransactionIsolation(conn, flags, current);
      assertEquals(current, conn.getTransactionIsolation());

      flags = defaults.reset(conn, flags);
      assertEquals(altered.value, conn.getTransactionIsolation());
    }
  }

  @Test
  public void setNetworkTimeout() throws Exception {
    if (isType(DbType.DERBY, DbType.HSQLDB, DbType.H2)) {
      // setNetworkTimeout is ignored
      return;
    }
    try (final Connection conn = getConnection()) {
      final int current = conn.getNetworkTimeout();
      final int altered = current + 123;
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      flags =
          defaults.setNetworkTimeout(conn, flags, defaults.getNetworkTimeoutExecutor(), altered);
      assertEquals(altered, conn.getNetworkTimeout());

      flags = defaults.reset(conn, flags);
      assertEquals(current, conn.getNetworkTimeout());
    }
  }

  @Test
  public void setNetworkTimeoutDefault() throws Exception {
    if (isType(DbType.DERBY, DbType.HSQLDB, DbType.H2)) {
      // setNetworkTimeout is ignored
      return;
    }
    try (final Connection conn = getConnection()) {
      final int current = conn.getNetworkTimeout();
      final int altered = current + 123;
      final Defaults defaults =
          Defaults.create(Hexane.builder().setNetworkTimeout(altered).getConfig(), conn);
      int flags = 0;

      flags =
          defaults.setNetworkTimeout(conn, flags, defaults.getNetworkTimeoutExecutor(), altered);
      assertEquals(altered, conn.getNetworkTimeout());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.getNetworkTimeout());

      flags =
          defaults.setNetworkTimeout(conn, flags, defaults.getNetworkTimeoutExecutor(), current);
      assertEquals(current, conn.getNetworkTimeout());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.getNetworkTimeout());
    }
  }

  @Test
  @Ignore
  public void setCatalog() throws Exception {
    try (final Connection conn = getConnection()) {
      final String current = conn.getCatalog();
      final String altered = "other";
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      flags = defaults.setCatalog(conn, flags, altered);
      assertEquals(altered, conn.getCatalog());

      flags = defaults.reset(conn, flags);
      assertEquals(current, conn.getCatalog());
    }
  }

  @Test
  @Ignore
  public void setCatalogDefault() throws Exception {
    try (final Connection conn = getConnection()) {
      final String current = conn.getCatalog();
      final String altered = "other";
      final Defaults defaults =
          Defaults.create(Hexane.builder().setCatalog(altered).getConfig(), conn);
      int flags = 0;

      flags = defaults.setCatalog(conn, flags, altered);
      assertEquals(altered, conn.getCatalog());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.getCatalog());

      flags = defaults.setCatalog(conn, flags, current);
      assertEquals(current, conn.getCatalog());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.getCatalog());
    }
  }

  @Test
  @Ignore
  public void setSchema() throws Exception {
    try (final Connection conn = getConnection()) {
      final String current = conn.getSchema();
      final String altered = "other";
      try {
        conn.createStatement().execute("CREATE SCHEMA IF NOT EXISTS other");
        final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
        int flags = 0;

        flags = defaults.setSchema(conn, flags, altered);
        assertEquals(altered, conn.getSchema());

        flags = defaults.reset(conn, flags);
        assertEquals(current, conn.getSchema());
      } finally {
        conn.createStatement().execute("DROP SCHEMA IF EXISTS other");
      }
    }
  }

  @Test
  @Ignore
  public void setSchemaDefault() throws Exception {
    try (final Connection conn = getConnection()) {
      final String current = conn.getSchema();
      final String altered = "other";
      try {
        conn.createStatement().execute("CREATE SCHEMA IF NOT EXISTS other");
        final Defaults defaults =
            Defaults.create(Hexane.builder().setSchema(altered).getConfig(), conn);
        int flags = 0;

        flags = defaults.setSchema(conn, flags, altered);
        assertEquals(altered, conn.getSchema());

        flags = defaults.reset(conn, flags);
        assertEquals(altered, conn.getSchema());

        flags = defaults.setSchema(conn, flags, current);
        assertEquals(current, conn.getSchema());

        flags = defaults.reset(conn, flags);
        assertEquals(altered, conn.getSchema());
      } finally {
        conn.createStatement().execute("DROP SCHEMA IF EXISTS other");
      }
    }
  }

  @Test
  public void setTypeMap() throws Exception {
    if (isType(DbType.DERBY, DbType.HSQLDB, DbType.H2, DbType.MARIADB)) {
      // setTypeMap is not supported
      return;
    }
    try (final Connection conn = getConnection()) {
      final Map<String, Class<?>> current = conn.getTypeMap();
      final Map<String, Class<?>> altered = Collections.singletonMap("test", String.class);
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      flags = defaults.setTypeMap(conn, flags, altered);
      assertEquals(altered, conn.getTypeMap());

      flags = defaults.reset(conn, flags);
      assertEquals(current, conn.getTypeMap());
    }
  }

  @Test
  public void setTypeMapDefault() throws Exception {
    if (isType(DbType.DERBY, DbType.HSQLDB, DbType.H2, DbType.MARIADB)) {
      // setTypeMap is not supported
      return;
    }
    try (final Connection conn = getConnection()) {
      final Map<String, Class<?>> current = conn.getTypeMap();
      final Map<String, Class<?>> altered = Collections.singletonMap("test", String.class);
      final Defaults defaults =
          Defaults.create(Hexane.builder().setTypeMap(altered).getConfig(), conn);
      int flags = 0;

      flags = defaults.setTypeMap(conn, flags, altered);
      assertEquals(altered, conn.getTypeMap());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.getTypeMap());

      flags = defaults.setTypeMap(conn, flags, current);
      assertEquals(current, conn.getTypeMap());

      flags = defaults.reset(conn, flags);
      assertEquals(altered, conn.getTypeMap());
    }
  }

  @Test
  public void setClientInfo() throws Exception {
    if (isType(DbType.HSQLDB)) {
      // setClientInfo is not supported
      return;
    }
    try (final Connection conn = getConnection()) {
      final Properties current = conn.getClientInfo();
      final Properties altered = new Properties();
      getAlteredClientInfo(conn, altered::put);
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      flags = defaults.setClientInfo(conn, flags, altered);
      assertEquals(getExpectedClientInfo(altered), conn.getClientInfo());

      flags = defaults.reset(conn, flags);
      assertEquals(getExpectedClientInfo(current), conn.getClientInfo());
    }
  }

  @Test
  public void setClientInfoDefault() throws Exception {
    if (isType(DbType.HSQLDB)) {
      // setClientInfo is not supported
      return;
    }
    try (final Connection conn = getConnection()) {
      final Properties current = conn.getClientInfo();
      final Properties altered = new Properties();
      getAlteredClientInfo(conn, altered::put);
      final Defaults defaults =
          Defaults.create(Hexane.builder().setClientInfo(altered).getConfig(), conn);
      int flags = 0;

      flags = defaults.setClientInfo(conn, flags, altered);
      assertEquals(getExpectedClientInfo(altered), conn.getClientInfo());

      flags = defaults.reset(conn, flags);
      assertEquals(getExpectedClientInfo(altered), conn.getClientInfo());

      flags = defaults.setClientInfo(conn, flags, current);
      assertEquals(getExpectedClientInfo(current), conn.getClientInfo());

      flags = defaults.reset(conn, flags);
      assertEquals(getExpectedClientInfo(altered), conn.getClientInfo());
    }
  }

  @Test
  public void setClientInfoSingle() throws Exception {
    if (isType(DbType.HSQLDB)) {
      // setClientInfo is not supported
      return;
    }
    try (final Connection conn = getConnection()) {
      final Properties current = conn.getClientInfo();
      final Map<String, String> changed = new HashMap<>();
      getAlteredClientInfo(conn, changed::put);
      final Properties altered = new Properties();
      if (current != null) {
        altered.putAll(current);
      }
      altered.putAll(changed);
      final Defaults defaults = Defaults.create(Hexane.builder().getConfig(), conn);
      int flags = 0;

      for (final Map.Entry<String, String> e : changed.entrySet()) {
        flags = defaults.setClientInfo(conn, flags, e.getKey(), e.getValue());
      }
      assertEquals(getExpectedClientInfo(altered), conn.getClientInfo());

      flags = defaults.reset(conn, flags);
      assertEquals(getExpectedClientInfo(current), conn.getClientInfo());
    }
  }

  private Properties getExpectedClientInfo(final Properties in) {
    final Properties ret = new Properties();
    ret.putAll(in);
    if (isType(DbType.H2)) {
      ret.put("numServers", "0");
    }
    return ret;
  }

  private void getAlteredClientInfo(final Connection conn, final BiConsumer<String, String> altered)
      throws Exception {
    try (final ResultSet rs = conn.getMetaData().getClientInfoProperties()) {
      while (rs.next()) {
        final String name = rs.getString(1);
        if (isType(DbType.H2) && name.contains("erver")) {
          // H2 internal props
          continue;
        }
        final int len = rs.getInt(2);
        final String value = "testvalue".substring(0, Math.min(9, len));
        altered.accept(name, value);
      }
    }
  }
}
