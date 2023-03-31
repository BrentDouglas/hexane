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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
final class LRUStatementCache extends StatementCache {

  final Map<PreparedStatement, StatementKey> ids;
  final LRUCache<StatementKey, PreparedStatement> cache;
  final InternalListener listener;

  LRUStatementCache(final int limit, final InternalListener listener) {
    this.ids = new IdentityHashMap<>(limit);
    this.cache = new LRUCache<>(limit);
    this.listener = listener;
  }

  boolean remove(final PreparedStatement statement) {
    final StatementKey key = this.ids.remove(statement);
    if (key == null) {
      return false;
    }
    return this.cache.remove(key) != null;
  }

  final PreparedStatement prepareStatement(final Connection conn, String sql) throws SQLException {
    final int resultSetType = ResultSet.TYPE_FORWARD_ONLY;
    final int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
    final int resultSetHoldability = conn.getHoldability();
    final StatementKey key =
        StatementKey.create(
            sql,
            false,
            resultSetType,
            resultSetConcurrency,
            resultSetHoldability,
            Config.UNSET,
            null,
            null);
    PreparedStatement statement = this.cache.get(key);
    if (statement == null) {
      statement = conn.prepareStatement(sql);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  final PreparedStatement prepareStatement(
      final Connection conn, String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    final int resultSetHoldability = conn.getHoldability();
    final StatementKey key =
        StatementKey.create(
            sql,
            false,
            resultSetType,
            resultSetConcurrency,
            resultSetHoldability,
            Config.UNSET,
            null,
            null);
    PreparedStatement statement = this.cache.get(key);
    if (statement == null) {
      statement = conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  PreparedStatement prepareStatement(
      final Connection conn,
      String sql,
      int resultSetType,
      int resultSetConcurrency,
      int resultSetHoldability)
      throws SQLException {
    final StatementKey key =
        StatementKey.create(
            sql,
            false,
            resultSetType,
            resultSetConcurrency,
            resultSetHoldability,
            Config.UNSET,
            null,
            null);
    PreparedStatement statement = this.cache.get(key);
    if (statement == null) {
      statement =
          conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  PreparedStatement prepareStatement(final Connection conn, String sql, int autoGeneratedKeys)
      throws SQLException {
    final StatementKey key =
        StatementKey.create(
            sql,
            false,
            ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_READ_ONLY,
            conn.getHoldability(),
            autoGeneratedKeys,
            null,
            null);
    PreparedStatement statement = this.cache.get(key);
    if (statement == null) {
      statement = conn.prepareStatement(sql, autoGeneratedKeys);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  PreparedStatement prepareStatement(final Connection conn, String sql, int[] columnIndexes)
      throws SQLException {
    final StatementKey key =
        StatementKey.create(
            sql,
            false,
            ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_READ_ONLY,
            conn.getHoldability(),
            Config.UNSET,
            null,
            columnIndexes);
    PreparedStatement statement = this.cache.get(key);
    if (statement == null) {
      statement = conn.prepareStatement(sql, columnIndexes);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  PreparedStatement prepareStatement(final Connection conn, String sql, String[] columnNames)
      throws SQLException {
    final StatementKey key =
        StatementKey.create(
            sql,
            false,
            ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_READ_ONLY,
            conn.getHoldability(),
            Config.UNSET,
            columnNames,
            null);
    PreparedStatement statement = this.cache.get(key);
    if (statement == null) {
      statement = conn.prepareStatement(sql, columnNames);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  CallableStatement prepareCall(final Connection conn, String sql) throws SQLException {
    final int resultSetType = ResultSet.TYPE_FORWARD_ONLY;
    final int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
    final int resultSetHoldability = conn.getHoldability();
    final StatementKey key =
        StatementKey.create(
            sql,
            true,
            resultSetType,
            resultSetConcurrency,
            resultSetHoldability,
            Config.UNSET,
            null,
            null);
    CallableStatement statement = (CallableStatement) this.cache.get(key);
    if (statement == null) {
      statement = conn.prepareCall(sql);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  CallableStatement prepareCall(
      final Connection conn, String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    final int resultSetHoldability = conn.getHoldability();
    final StatementKey key =
        StatementKey.create(
            sql,
            true,
            resultSetType,
            resultSetConcurrency,
            resultSetHoldability,
            Config.UNSET,
            null,
            null);
    CallableStatement statement = (CallableStatement) this.cache.get(key);
    if (statement == null) {
      statement = conn.prepareCall(sql, resultSetType, resultSetConcurrency);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  CallableStatement prepareCall(
      final Connection conn,
      String sql,
      int resultSetType,
      int resultSetConcurrency,
      int resultSetHoldability)
      throws SQLException {

    final StatementKey key =
        StatementKey.create(
            sql,
            true,
            resultSetType,
            resultSetConcurrency,
            resultSetHoldability,
            Config.UNSET,
            null,
            null);
    CallableStatement statement = (CallableStatement) this.cache.get(key);
    if (statement == null) {
      statement = conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      this.cache.put(key, statement);
      this.ids.put(statement, key);
      listener.onStatementCacheMiss();
    } else {
      listener.onStatementCacheHit();
    }
    return statement;
  }

  static final class LRUCache<K, V> extends LinkedHashMap<K, V> {

    final int limit;

    private LRUCache(final int limit) {
      super(limit + 1, 1F, true);
      this.limit = limit;
    }

    @Override
    protected boolean removeEldestEntry(final Entry<K, V> eldest) {
      return super.size() > this.limit;
    }
  }
}
