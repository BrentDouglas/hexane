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

import io.machinecode.hexane.HoldabilityType;
import io.machinecode.hexane.LoggerType;
import io.machinecode.hexane.TransactionIsolationType;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
@ConfigurationProperties(prefix = "spring.datasource.hexane")
class HexaneProperties implements BeanClassLoaderAware {
  private ClassLoader classLoader;
  private String username;
  private String password;
  private String jndiName;
  private String url;
  private String driverClassName;
  private String dataSourceClassName;

  private TimeUnit connectionTimeoutUnit;
  private Integer connectionTimeout;
  private TimeUnit idleTimeoutUnit;
  private Integer idleTimeout;
  private TimeUnit lifetimeTimeoutUnit;
  private Integer lifetimeTimeout;
  private TimeUnit validationTimeoutUnit;
  private Integer validationTimeout;
  private Integer maxPoolSize;
  private Integer corePoolSize;
  private Integer statementCacheSize;

  private Boolean autoCommit;
  private HoldabilityType holdability;
  private Boolean readOnly;
  private TransactionIsolationType transactionIsolation;
  private String catalog;
  private String schema;
  private Map<String, String> typeMap;
  private Map<String, String> properties;
  private Properties clientInfo;
  private Integer networkTimeout;

  private LoggerType loggerType;

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getJndiName() {
    return jndiName;
  }

  public void setJndiName(final String jndiName) {
    this.jndiName = jndiName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getDriverClassName() {
    return driverClassName;
  }

  public void setDriverClassName(final String driverClassName) {
    this.driverClassName = driverClassName;
  }

  public String getDataSourceClassName() {
    return dataSourceClassName;
  }

  public void setDataSourceClassName(final String dataSourceClassName) {
    this.dataSourceClassName = dataSourceClassName;
  }

  public TimeUnit getConnectionTimeoutUnit() {
    return connectionTimeoutUnit;
  }

  public void setConnectionTimeoutUnit(final TimeUnit connectionTimeoutUnit) {
    this.connectionTimeoutUnit = connectionTimeoutUnit;
  }

  public Integer getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(final Integer connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public TimeUnit getIdleTimeoutUnit() {
    return idleTimeoutUnit;
  }

  public void setIdleTimeoutUnit(final TimeUnit idleTimeoutUnit) {
    this.idleTimeoutUnit = idleTimeoutUnit;
  }

  public Integer getIdleTimeout() {
    return idleTimeout;
  }

  public void setIdleTimeout(final Integer idleTimeout) {
    this.idleTimeout = idleTimeout;
  }

  public TimeUnit getLifetimeTimeoutUnit() {
    return lifetimeTimeoutUnit;
  }

  public void setLifetimeTimeoutUnit(final TimeUnit lifetimeTimeoutUnit) {
    this.lifetimeTimeoutUnit = lifetimeTimeoutUnit;
  }

  public Integer getLifetimeTimeout() {
    return lifetimeTimeout;
  }

  public void setLifetimeTimeout(final Integer lifetimeTimeout) {
    this.lifetimeTimeout = lifetimeTimeout;
  }

  public TimeUnit getValidationTimeoutUnit() {
    return validationTimeoutUnit;
  }

  public void setValidationTimeoutUnit(final TimeUnit validationTimeoutUnit) {
    this.validationTimeoutUnit = validationTimeoutUnit;
  }

  public Integer getValidationTimeout() {
    return validationTimeout;
  }

  public void setValidationTimeout(final Integer validationTimeout) {
    this.validationTimeout = validationTimeout;
  }

  public Integer getMaxPoolSize() {
    return maxPoolSize;
  }

  public void setMaxPoolSize(final Integer maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }

  public Integer getCorePoolSize() {
    return corePoolSize;
  }

  public void setCorePoolSize(final Integer corePoolSize) {
    this.corePoolSize = corePoolSize;
  }

  public Integer getStatementCacheSize() {
    return statementCacheSize;
  }

  public void setStatementCacheSize(final Integer statementCacheSize) {
    this.statementCacheSize = statementCacheSize;
  }

  public Boolean getAutoCommit() {
    return autoCommit;
  }

  public void setAutoCommit(final Boolean autoCommit) {
    this.autoCommit = autoCommit;
  }

  public HoldabilityType getHoldability() {
    return holdability;
  }

  public void setHoldability(final HoldabilityType holdability) {
    this.holdability = holdability;
  }

  public Boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(final Boolean readOnly) {
    this.readOnly = readOnly;
  }

  public TransactionIsolationType getTransactionIsolation() {
    return transactionIsolation;
  }

  public void setTransactionIsolation(final TransactionIsolationType transactionIsolation) {
    this.transactionIsolation = transactionIsolation;
  }

  public String getCatalog() {
    return catalog;
  }

  public void setCatalog(final String catalog) {
    this.catalog = catalog;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(final String schema) {
    this.schema = schema;
  }

  public Map<String, String> getTypeMap() {
    return typeMap;
  }

  public void setTypeMap(final Map<String, String> typeMap) {
    this.typeMap = typeMap;
  }

  public Properties getClientInfo() {
    return clientInfo;
  }

  public void setClientInfo(final Properties clientInfo) {
    this.clientInfo = clientInfo;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(final Map<String, String> properties) {
    this.properties = properties;
  }

  public Integer getNetworkTimeout() {
    return networkTimeout;
  }

  public void setNetworkTimeout(final Integer networkTimeout) {
    this.networkTimeout = networkTimeout;
  }

  public LoggerType getLoggerType() {
    return loggerType;
  }

  public void setLoggerType(final LoggerType loggerType) {
    this.loggerType = loggerType;
  }

  public ClassLoader getClassLoader() {
    return classLoader;
  }

  @Override
  public void setBeanClassLoader(final ClassLoader classLoader) {
    this.classLoader = classLoader;
  }
}
