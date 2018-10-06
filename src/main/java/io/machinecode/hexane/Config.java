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

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public final class Config {
  static final int UNSET = -1;

  private static final List<String> FATAL_PREFIXES =
      Collections.singletonList(Util.CONNECTION_ERROR);

  static final HexaneListener NOOP_LISTENER = new HexaneListener() {};
  static final ExceptionHandler EXCEPTION_HANDLER = new ExceptionHandler() {};

  private final long connectionTimeout;
  private final TimeUnit connectionTimeoutUnit;
  private final long idleTimeout;
  private final long lifetimeTimeout;
  private final int validationTimeout;
  private final int maxPoolSize;
  private final int corePoolSize;
  private final int statementCacheSize;
  private final String user;
  private final String password;
  private final Executor maintenanceExecutor;

  private final Boolean autoCommit;
  private final Integer holdability;
  private final Boolean readOnly;
  private final Integer transactionIsolation;
  private final String catalog;
  private final String schema;
  private final Map<String, Class<?>> typeMap;
  private final Properties clientInfo;
  private final Integer networkTimeout;
  private final Executor networkTimeoutExecutor;

  private final boolean checkFatal;
  private final LoggerType loggerType;
  private final InternalListener listener;
  private final ExceptionHandler exceptionHandler;

  Config(final Builder builder) {
    final TimeUnit clockUnit = Clock.getUnit();
    this.connectionTimeout = builder.connectionTimeout;
    this.connectionTimeoutUnit = builder.connectionTimeoutUnit;
    this.idleTimeout =
        builder.idleTimeoutUnit == null
            ? UNSET
            : clockUnit.convert(builder.idleTimeout, builder.idleTimeoutUnit);
    this.lifetimeTimeout =
        builder.lifetimeTimeoutUnit == null
            ? UNSET
            : clockUnit.convert(builder.lifetimeTimeout, builder.lifetimeTimeoutUnit);
    final long validation = builder.validationTimeoutUnit.toSeconds(builder.validationTimeout);
    if (validation > Integer.MAX_VALUE) {
      throw new IllegalArgumentException(); // TODO message
    }
    this.validationTimeout = (int) validation;
    this.maxPoolSize = builder.maxPoolSize;
    this.corePoolSize = builder.corePoolSize;
    this.statementCacheSize = builder.statementCacheSize;
    this.user = builder.user;
    this.password = builder.password;
    this.maintenanceExecutor = builder.maintenanceExecutor;
    this.autoCommit = builder.autoCommit;
    this.holdability = builder.holdability == null ? null : builder.holdability.value;
    this.readOnly = builder.readOnly;
    this.transactionIsolation =
        builder.transactionIsolation == null ? null : builder.transactionIsolation.value;
    this.catalog = builder.catalog;
    this.schema = builder.schema;
    this.typeMap = builder.typeMap;
    this.clientInfo = builder.clientInfo;
    this.networkTimeout = builder.networkTimeout;
    this.networkTimeoutExecutor = builder.networkTimeoutExecutor;
    this.checkFatal = builder.exceptionHandler != EXCEPTION_HANDLER;
    this.loggerType = builder.loggerType;
    if (builder.listener == NOOP_LISTENER) {
      this.listener = InternalListener.INSTANCE;
    } else {
      this.listener = new DelegateInternalListener(builder.listener);
    }
    this.exceptionHandler = builder.exceptionHandler;
  }

  long getConnectionTimeout() {
    return connectionTimeout;
  }

  TimeUnit getConnectionTimeoutUnit() {
    return connectionTimeoutUnit;
  }

  long getIdleTimeout() {
    return idleTimeout;
  }

  long getLifetimeTimeout() {
    return lifetimeTimeout;
  }

  int getValidationTimeout() {
    return validationTimeout;
  }

  int getMaxPoolSize() {
    return maxPoolSize;
  }

  int getCorePoolSize() {
    return corePoolSize;
  }

  int getStatementCacheSize() {
    return statementCacheSize;
  }

  String getUser() {
    return user;
  }

  String getPassword() {
    return password;
  }

  Executor getMaintenanceExecutor() {
    return maintenanceExecutor;
  }

  Boolean isAutoCommit() {
    return autoCommit;
  }

  Integer getHoldability() {
    return holdability;
  }

  Boolean isReadOnly() {
    return readOnly;
  }

  Integer getTransactionIsolation() {
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

  Integer getNetworkTimeout() {
    return networkTimeout;
  }

  Executor getNetworkTimeoutExecutor() {
    return networkTimeoutExecutor;
  }

  LoggerFactory getLoggerType() {
    return LoggerType.getFactory(loggerType);
  }

  boolean isCheckFatal() {
    return checkFatal;
  }

  InternalListener getListener() {
    return listener;
  }

  ExceptionHandler getExceptionHander() {
    return exceptionHandler;
  }

  public static final class Builder {
    private TimeUnit connectionTimeoutUnit;
    private int connectionTimeout = UNSET;
    private TimeUnit idleTimeoutUnit;
    private int idleTimeout = UNSET;
    private TimeUnit lifetimeTimeoutUnit;
    private int lifetimeTimeout = UNSET;
    private TimeUnit validationTimeoutUnit;
    private int validationTimeout = UNSET;
    private int maxPoolSize = UNSET;
    private int corePoolSize = UNSET;
    private int statementCacheSize = UNSET;
    private String user;
    private String password;
    private Executor maintenanceExecutor;

    private Boolean autoCommit;
    private HoldabilityType holdability;
    private Boolean readOnly;
    private TransactionIsolationType transactionIsolation;
    private String catalog;
    private String schema;
    private Map<String, Class<?>> typeMap;
    private Properties clientInfo;
    private Integer networkTimeout;
    private Executor networkTimeoutExecutor;

    private LoggerType loggerType = LoggerType.JUL;
    private HexaneListener listener = NOOP_LISTENER;
    private ExceptionHandler exceptionHandler = EXCEPTION_HANDLER;

    Builder() {}

    public Builder setConnectionTimeout(final int timeout, final TimeUnit unit) {
      Objects.requireNonNull(unit);
      if (timeout < 0) {
        throw new IllegalArgumentException(); // TODO
      }
      this.connectionTimeout = timeout;
      this.connectionTimeoutUnit = unit;
      return this;
    }

    public Builder setIdleTimeout(final int timeout, final TimeUnit unit) {
      Objects.requireNonNull(unit);
      if (timeout < 0) {
        throw new IllegalArgumentException(); // TODO
      }
      this.idleTimeout = timeout;
      this.idleTimeoutUnit = unit;
      return this;
    }

    public Builder setLifetimeTimeout(final int timeout, final TimeUnit unit) {
      Objects.requireNonNull(unit);
      if (timeout < 0) {
        throw new IllegalArgumentException(); // TODO
      }
      this.lifetimeTimeout = timeout;
      this.lifetimeTimeoutUnit = unit;
      return this;
    }

    public Builder setValidationTimeout(final int timeout, final TimeUnit unit) {
      Objects.requireNonNull(unit);
      if (timeout < 0) {
        throw new IllegalArgumentException(); // TODO
      }
      this.validationTimeout = timeout;
      this.validationTimeoutUnit = unit;
      return this;
    }

    public Builder setMaxPoolSize(final int maxPoolSize) {
      this.maxPoolSize = maxPoolSize;
      return this;
    }

    public Builder setCorePoolSize(final int corePoolSize) {
      this.corePoolSize = corePoolSize;
      return this;
    }

    public Builder setStatementCacheSize(final int statementCacheSize) {
      this.statementCacheSize = statementCacheSize;
      return this;
    }

    public Builder setUser(final String user) {
      this.user = user;
      return this;
    }

    public Builder setPassword(final String password) {
      this.password = password;
      return this;
    }

    public Builder setMaintenanceExecutor(final Executor maintenanceExecutor) {
      this.maintenanceExecutor = maintenanceExecutor;
      return this;
    }

    public Builder setAutoCommit(final boolean autoCommit) {
      this.autoCommit = autoCommit;
      return this;
    }

    public Builder setHoldability(final HoldabilityType holdability) {
      this.holdability = holdability;
      return this;
    }

    public Builder setReadOnly(final boolean readOnly) {
      this.readOnly = readOnly;
      return this;
    }

    public Builder setTransactionIsolation(final TransactionIsolationType transactionIsolation) {
      this.transactionIsolation = transactionIsolation;
      return this;
    }

    public Builder setCatalog(final String catalog) {
      this.catalog = catalog;
      return this;
    }

    public Builder setSchema(final String schema) {
      this.schema = schema;
      return this;
    }

    public Builder setTypeMap(final Map<String, Class<?>> typeMap) {
      this.typeMap = typeMap;
      return this;
    }

    public Builder setClientInfo(final Properties clientInfo) {
      this.clientInfo = clientInfo;
      return this;
    }

    public Builder setNetworkTimeout(final int networkTimeout) {
      this.networkTimeout = networkTimeout;
      return this;
    }

    public Builder setNetworkTimeoutExecutor(final Executor networkTimeoutExecutor) {
      this.networkTimeoutExecutor = networkTimeoutExecutor;
      return this;
    }

    public Builder setLoggerType(final LoggerType loggerType) {
      Objects.requireNonNull(loggerType);
      this.loggerType = loggerType;
      return this;
    }

    public Builder setListener(final HexaneListener listener) {
      Objects.requireNonNull(listener);
      this.listener = listener;
      return this;
    }

    public Builder setExceptionHandler(final ExceptionHandler handler) {
      Objects.requireNonNull(handler);
      this.exceptionHandler = handler;
      return this;
    }

    Config getConfig() {
      if (this.connectionTimeout == UNSET) {
        this.connectionTimeout = 1;
        this.connectionTimeoutUnit = TimeUnit.SECONDS;
      }
      if (this.validationTimeoutUnit == null) {
        this.validationTimeout = 1;
        this.validationTimeoutUnit = TimeUnit.SECONDS;
      }
      if (this.corePoolSize == UNSET) {
        this.corePoolSize = 2;
      }
      if (this.maxPoolSize == UNSET) {
        this.maxPoolSize = 4;
      }
      return new Config(this);
    }

    public DataSource buildDataSource(final DataSource dataSource) throws SQLException {
      assertValid();
      final Config config = new Config(this);
      try (final Connection conn = dataSource.getConnection()) {
        final Defaults defaults = Defaults.create(config, conn);
        final HexanePool pool = new HexanePool(config, defaults, dataSource);
        final HexaneDataSource ret = new HexaneDataSource(pool, dataSource, defaults);
        config.getListener().onDataSourceCreation();
        return ret;
      }
    }

    public ConnectionPoolDataSource buildManagedDataSource(final DataSource dataSource)
        throws SQLException {
      assertValid();
      final Config config = new Config(this);
      try (final Connection conn = dataSource.getConnection()) {
        final Defaults defaults = Defaults.create(config, conn);
        final HexanePool pool = new HexanePool(config, defaults, dataSource);
        final HexaneManagedDataSource ret = new HexaneManagedDataSource(pool, dataSource, defaults);
        config.getListener().onDataSourceCreation();
        return ret;
      }
    }

    public ConnectionPoolDataSource buildConnectionPoolDataSource(
        final ConnectionPoolDataSource dataSource) throws SQLException {
      assertValid();
      final Config config = new Config(this);
      try (final Connection conn = dataSource.getPooledConnection().getConnection()) {
        final Defaults defaults = Defaults.create(config, conn);
        final HexanePooledPool pool = new HexanePooledPool(config, defaults, dataSource);
        final HexaneConnectionPoolDataSource ret =
            new HexaneConnectionPoolDataSource(pool, dataSource, defaults);
        config.getListener().onDataSourceCreation();
        return ret;
      }
    }

    public HexaneXADataSource buildXADataSource(final XADataSource dataSource) throws SQLException {
      assertValid();
      final Config config = new Config(this);
      try (final Connection conn = dataSource.getXAConnection().getConnection()) {
        final Defaults defaults = Defaults.create(config, conn);
        final HexaneXAPool pool = new HexaneXAPool(config, defaults, dataSource);
        final HexaneXADataSource ret = new HexaneXADataSource(pool, dataSource, defaults);
        config.getListener().onDataSourceCreation();
        return ret;
      }
    }

    private void assertValid() throws SQLNonTransientException {
      if (this.validationTimeout == UNSET) {
        throw new SQLNonTransientException(Msg.VALIDATION_TIMEOUT);
      }
      if (this.maxPoolSize == UNSET) {
        throw new SQLNonTransientException(Msg.MAX_POOL_SIZE);
      }
      if (this.corePoolSize == UNSET) {
        throw new SQLNonTransientException(Msg.CORE_POOL_SIZE);
      }
    }
  }
}
