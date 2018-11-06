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
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class Defaults {
  private final boolean autoCommit;
  private final int holdability;
  private final boolean readOnly;
  private final int transactionIsolation;
  private final String catalog;
  private final String schema;
  private final Map<String, Class<?>> typeMap;
  private final Properties clientInfo;
  private final int networkTimeout;
  private final Executor networkTimeoutExecutor;

  private final int supported;

  Defaults(final Builder builder) {
    this.autoCommit = builder.autoCommit;
    this.holdability = builder.holdability;
    this.readOnly = builder.readOnly;
    this.transactionIsolation = builder.transactionIsolation;
    this.catalog = builder.catalog;
    this.schema = builder.schema;
    this.typeMap = builder.typeMap;
    this.clientInfo = builder.clientInfo;
    this.networkTimeout = builder.networkTimeout;
    this.networkTimeoutExecutor = builder.networkTimeoutExecutor;
    this.supported = builder.supported;
  }

  static Defaults create(final Config config, final Connection conn) throws SQLException {
    final Executor networkTimeoutExecutor = config.getNetworkTimeoutExecutor();
    int supported = 0;

    boolean autoCommit = false;
    try {
      autoCommit = conn.getAutoCommit();
      conn.setAutoCommit(autoCommit);
      supported |= Flags.AUTO_COMMIT;
    } catch (final Throwable e) {
      //
    }
    if (config.isAutoCommit() != null) {
      autoCommit = config.isAutoCommit();
    }

    boolean readOnly = false;
    try {
      readOnly = conn.isReadOnly();
      conn.setReadOnly(readOnly);
      supported |= Flags.READ_ONLY;
    } catch (final Throwable e) {
      //
    }
    if (config.isReadOnly() != null) {
      readOnly = config.isReadOnly();
    }

    int holdability = -1;
    try {
      holdability = conn.getHoldability();
      conn.setHoldability(holdability);
      supported |= Flags.HOLDABILITY;
    } catch (final Throwable e) {
      //
    }
    if (config.getHoldability() != null) {
      holdability = config.getHoldability();
      switch (holdability) {
        case ResultSet.CLOSE_CURSORS_AT_COMMIT:
        case ResultSet.HOLD_CURSORS_OVER_COMMIT:
          break;
        default:
          throw new SQLNonTransientException(Msg.HOLDABILITY);
      }
    }

    int transactionIsolation = -1;
    try {
      transactionIsolation = conn.getTransactionIsolation();
      conn.setTransactionIsolation(transactionIsolation);
      supported |= Flags.TRANSACTION_ISOLATION;
    } catch (final Throwable e) {
      //
    }
    if (config.getTransactionIsolation() != null) {
      transactionIsolation = config.getTransactionIsolation();
      switch (transactionIsolation) {
        case Connection.TRANSACTION_NONE:
        case Connection.TRANSACTION_READ_UNCOMMITTED:
        case Connection.TRANSACTION_READ_COMMITTED:
        case Connection.TRANSACTION_REPEATABLE_READ:
        case Connection.TRANSACTION_SERIALIZABLE:
          break;
        default:
          throw new SQLNonTransientException(Msg.TRANSACTION_ISOLATION);
      }
    }

    int networkTimeout = -1;
    try {
      networkTimeout = conn.getNetworkTimeout();
      conn.setNetworkTimeout(networkTimeoutExecutor, networkTimeout);
      supported |= Flags.NETWORK_TIMEOUT;
    } catch (final Throwable e) {
      //
    }
    if (config.getNetworkTimeout() != null) {
      networkTimeout = config.getNetworkTimeout();
    }

    String catalog = null;
    try {
      catalog = conn.getCatalog();
      conn.setCatalog(catalog);
      supported |= Flags.CATALOG;
    } catch (final Throwable e) {
      //
    }
    if (config.getCatalog() != null) {
      catalog = config.getCatalog();
    }

    String schema = null;
    try {
      schema = conn.getSchema();
      conn.setSchema(schema);
      supported |= Flags.SCHEMA;
    } catch (final Throwable e) {
      //
    }
    if (config.getSchema() != null) {
      schema = config.getSchema();
    }

    Map<String, Class<?>> typeMap = null;
    try {
      typeMap = conn.getTypeMap();
      conn.setTypeMap(typeMap);
      supported |= Flags.TYPE_MAP;
    } catch (final Throwable e) {
      //
    }
    if (config.getTypeMap() != null) {
      typeMap = config.getTypeMap();
    }

    Properties clientInfo = null;
    try {
      clientInfo = conn.getClientInfo();
      conn.setClientInfo(clientInfo);
      supported |= Flags.CLIENT_INFO;
    } catch (final Throwable e) {
      //
    }
    if (config.getClientInfo() != null) {
      clientInfo = config.getClientInfo();
    }
    return new Builder()
        .setAutoCommit(autoCommit)
        .setHoldability(holdability)
        .setReadOnly(readOnly)
        .setTransactionIsolation(transactionIsolation)
        .setCatalog(catalog)
        .setSchema(schema)
        .setTypeMap(typeMap)
        .setClientInfo(clientInfo)
        .setNetworkTimeout(networkTimeout)
        .setNetworkTimeoutExecutor(networkTimeoutExecutor)
        .setSupported(supported)
        .build();
  }

  void initialize(final Connection conn) throws SQLException {
    final int supported = this.supported;
    if (Flags.isSupported(Flags.AUTO_COMMIT, supported)) {
      conn.setAutoCommit(this.autoCommit);
    }
    if (Flags.isSupported(Flags.HOLDABILITY, supported)) {
      conn.setHoldability(this.holdability);
    }
    if (Flags.isSupported(Flags.NETWORK_TIMEOUT, supported)) {
      conn.setNetworkTimeout(this.networkTimeoutExecutor, this.networkTimeout);
    }
    if (Flags.isSupported(Flags.READ_ONLY, supported)) {
      conn.setReadOnly(this.readOnly);
    }
    if (Flags.isSupported(Flags.TRANSACTION_ISOLATION, supported)) {
      conn.setTransactionIsolation(this.transactionIsolation);
    }
    if (Flags.isSupported(Flags.CATALOG, supported)) {
      conn.setCatalog(this.catalog);
    }
    if (Flags.isSupported(Flags.TYPE_MAP, supported)) {
      conn.setTypeMap(this.typeMap);
    }
    if (Flags.isSupported(Flags.SCHEMA, supported)) {
      conn.setSchema(this.schema);
    }
    if (Flags.isSupported(Flags.CLIENT_INFO, supported)) {
      conn.setClientInfo(this.clientInfo);
    }
    if (!conn.getAutoCommit()) {
      conn.rollback();
    }
  }

  int setAutoCommit(final Connection conn, final int flags, final boolean autoCommit)
      throws SQLException {
    conn.setAutoCommit(autoCommit);
    if (autoCommit == this.autoCommit) {
      return flags & Flags.AUTO_COMMIT;
    } else {
      return flags | Flags.AUTO_COMMIT;
    }
  }

  int setReadOnly(final Connection conn, final int flags, final boolean readOnly)
      throws SQLException {
    conn.setReadOnly(readOnly);
    if (readOnly == this.readOnly) {
      return flags & Flags.READ_ONLY;
    } else {
      return flags | Flags.READ_ONLY;
    }
  }

  int setHoldability(final Connection conn, final int flags, final int holdability)
      throws SQLException {
    conn.setHoldability(holdability);
    if (holdability == this.holdability) {
      return flags & Flags.HOLDABILITY;
    } else {
      return flags | Flags.HOLDABILITY;
    }
  }

  int setTransactionIsolation(
      final Connection conn, final int flags, final int transactionIsolation) throws SQLException {
    conn.setTransactionIsolation(transactionIsolation);
    if (transactionIsolation == this.transactionIsolation) {
      return flags & Flags.TRANSACTION_ISOLATION;
    } else {
      return flags | Flags.TRANSACTION_ISOLATION;
    }
  }

  int setNetworkTimeout(
      final Connection conn, final int flags, final Executor executor, final int networkTimeout)
      throws SQLException {
    conn.setNetworkTimeout(executor, networkTimeout);
    if (networkTimeout == this.networkTimeout) {
      return flags & Flags.NETWORK_TIMEOUT;
    } else {
      return flags | Flags.NETWORK_TIMEOUT;
    }
  }

  int setCatalog(final Connection conn, final int flags, final String catalog) throws SQLException {
    conn.setCatalog(catalog);
    if (Objects.equals(catalog, this.catalog)) {
      return flags & Flags.CATALOG;
    } else {
      return flags | Flags.CATALOG;
    }
  }

  int setSchema(final Connection conn, final int flags, final String schema) throws SQLException {
    conn.setSchema(schema);
    if (Objects.equals(schema, this.schema)) {
      return flags & Flags.SCHEMA;
    } else {
      return flags | Flags.SCHEMA;
    }
  }

  int setTypeMap(final Connection conn, final int flags, final Map<String, Class<?>> typeMap)
      throws SQLException {
    conn.setTypeMap(typeMap);
    if (Objects.equals(typeMap, this.typeMap)) {
      return flags & Flags.TYPE_MAP;
    } else {
      return flags | Flags.TYPE_MAP;
    }
  }

  int setClientInfo(final Connection conn, final int flags, final Properties clientInfo)
      throws SQLClientInfoException {
    conn.setClientInfo(clientInfo);
    if (Objects.equals(clientInfo, this.clientInfo)) {
      return flags & Flags.CLIENT_INFO;
    } else {
      return flags | Flags.CLIENT_INFO;
    }
  }

  int setClientInfo(final Connection conn, final int flags, final String key, final String value)
      throws SQLClientInfoException {
    conn.setClientInfo(key, value);
    return flags | Flags.CLIENT_INFO;
  }

  int reset(final Connection conn, final int flags) throws SQLException {
    final int supported = this.supported;
    if (!conn.getAutoCommit()) {
      conn.rollback();
    }
    conn.clearWarnings();
    if (flags == 0) {
      return 0;
    }
    if (Flags.isSet(Flags.AUTO_COMMIT, flags, supported)) {
      conn.setAutoCommit(this.autoCommit);
    }
    if (Flags.isSet(Flags.HOLDABILITY, flags, supported)) {
      conn.setHoldability(this.holdability);
    }
    if (Flags.isSet(Flags.NETWORK_TIMEOUT, flags, supported)) {
      conn.setNetworkTimeout(this.networkTimeoutExecutor, this.networkTimeout);
    }
    if (Flags.isSet(Flags.READ_ONLY, flags, supported)) {
      conn.setReadOnly(this.readOnly);
    }
    if (Flags.isSet(Flags.TRANSACTION_ISOLATION, flags, supported)) {
      conn.setTransactionIsolation(this.transactionIsolation);
    }
    if (Flags.isSet(Flags.CATALOG, flags, supported)) {
      conn.setCatalog(this.catalog);
    }
    if (Flags.isSet(Flags.TYPE_MAP, flags, supported)) {
      conn.setTypeMap(this.typeMap);
    }
    if (Flags.isSet(Flags.SCHEMA, flags, supported)) {
      conn.setSchema(this.schema);
    }
    if (Flags.isSet(Flags.CLIENT_INFO, flags, supported)) {
      conn.setClientInfo(this.clientInfo);
    }
    return 0;
  }

  boolean isAutoCommit() {
    return autoCommit;
  }

  int getHoldability() {
    return holdability;
  }

  boolean isReadOnly() {
    return readOnly;
  }

  int getTransactionIsolation() {
    return transactionIsolation;
  }

  String getCatalog() {
    return catalog;
  }

  String getSchema() {
    return schema;
  }

  Map<String, Class<?>> getTypeMap() {
    return typeMap;
  }

  Properties getClientInfo() {
    return clientInfo;
  }

  int getNetworkTimeout() {
    return networkTimeout;
  }

  Executor getNetworkTimeoutExecutor() {
    return networkTimeoutExecutor;
  }

  int getSupported() {
    return supported;
  }

  public static final class Builder {
    private boolean autoCommit = false;
    private int holdability = Config.UNSET;
    private boolean readOnly = false;
    private int transactionIsolation = Config.UNSET;
    private String catalog;
    private String schema;
    private Map<String, Class<?>> typeMap;
    private Properties clientInfo;
    private int networkTimeout = Config.UNSET;
    private Executor networkTimeoutExecutor;
    private int supported = Config.UNSET;

    Builder setAutoCommit(boolean autoCommit) {
      this.autoCommit = autoCommit;
      return this;
    }

    Builder setHoldability(int holdability) {
      this.holdability = holdability;
      return this;
    }

    Builder setReadOnly(boolean readOnly) {
      this.readOnly = readOnly;
      return this;
    }

    Builder setTransactionIsolation(int transactionIsolation) {
      this.transactionIsolation = transactionIsolation;
      return this;
    }

    Builder setCatalog(String catalog) {
      this.catalog = catalog;
      return this;
    }

    Builder setSchema(String schema) {
      this.schema = schema;
      return this;
    }

    Builder setTypeMap(Map<String, Class<?>> typeMap) {
      this.typeMap = typeMap;
      return this;
    }

    Builder setClientInfo(Properties clientInfo) {
      this.clientInfo = clientInfo;
      return this;
    }

    Builder setNetworkTimeout(int networkTimeout) {
      this.networkTimeout = networkTimeout;
      return this;
    }

    Builder setNetworkTimeoutExecutor(Executor networkTimeoutExecutor) {
      this.networkTimeoutExecutor = networkTimeoutExecutor;
      return this;
    }

    Builder setSupported(int supported) {
      this.supported = supported;
      return this;
    }

    Defaults build() {
      return new Defaults(this);
    }
  }
}
