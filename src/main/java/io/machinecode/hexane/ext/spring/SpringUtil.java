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

import io.machinecode.hexane.Config;
import io.machinecode.hexane.Config.Builder;
import io.machinecode.hexane.Hexane;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
class SpringUtil {

  static DataSource getDriverDataSource(final HexaneProperties properties) throws SQLException, ClassNotFoundException {
    Driver driver = null;
    final String driverClassName = properties.getDriverClassName();
    if (driverClassName != null) {
      final Enumeration<Driver> drivers = DriverManager.getDrivers();
      while (drivers.hasMoreElements()) {
        Driver it = drivers.nextElement();
        if (it.getClass().getName().equals(driverClassName)) {
          driver = it;
          break;
        }
      }
      if (driver == null) {
        driver = (Driver) newInstance(properties.getClassLoader(), driverClassName);
      }
    }
    if (driver == null) {
      driver = DriverManager.getDriver(properties.getUrl());
    }
    if (driver == null) {
      throw new IllegalStateException();
    }
    final Properties props = new Properties();
    setProps(properties, props);
    return new DriverDataSource(driver, properties.getUrl(), props);
  }

  static Config.Builder create(final HexaneProperties properties) throws ClassNotFoundException {
    final Builder builder = Hexane.builder();
    if (properties.getConnectionTimeout() != null) {
      builder.setConnectionTimeout(
          properties.getConnectionTimeout(), properties.getConnectionTimeoutUnit());
    }
    if (properties.getIdleTimeout() != null) {
      builder.setIdleTimeout(properties.getIdleTimeout(), properties.getIdleTimeoutUnit());
    }
    if (properties.getValidationTimeout() != null) {
      builder.setValidationTimeout(
          properties.getValidationTimeout(), properties.getValidationTimeoutUnit());
    }
    if (properties.getLifetimeTimeout() != null) {
      builder.setLifetimeTimeout(
          properties.getLifetimeTimeout(), properties.getLifetimeTimeoutUnit());
    }
    if (properties.getMaxPoolSize() != null) {
      builder.setMaxPoolSize(properties.getMaxPoolSize());
    }
    if (properties.getCorePoolSize() != null) {
      builder.setCorePoolSize(properties.getCorePoolSize());
    }
    if (properties.getStatementCacheSize() != null) {
      builder.setStatementCacheSize(properties.getStatementCacheSize());
    }
    if (properties.getUsername() != null) {
      builder.setUser(properties.getUsername());
    }
    if (properties.getPassword() != null) {
      builder.setPassword(properties.getPassword());
    }
    if (properties.getAutoCommit() != null) {
      builder.setAutoCommit(properties.getAutoCommit());
    }
    if (properties.getHoldability() != null) {
      builder.setHoldability(properties.getHoldability());
    }
    if (properties.getReadOnly() != null) {
      builder.setReadOnly(properties.getReadOnly());
    }
    if (properties.getTransactionIsolation() != null) {
      builder.setTransactionIsolation(properties.getTransactionIsolation());
    }
    if (properties.getCatalog() != null) {
      builder.setCatalog(properties.getCatalog());
    }
    if (properties.getSchema() != null) {
      builder.setSchema(properties.getSchema());
    }
    final Map<String, String> typeMap = properties.getTypeMap();
    if (typeMap != null) {
      final Map<String, Class<?>> types = new HashMap<>(typeMap.size());
      for (final Entry<String, String> e : typeMap.entrySet()) {
        types.put(e.getKey(), Class.forName(e.getValue(), true, properties.getClassLoader()));
      }
      builder.setTypeMap(types);
    }
    if (properties.getClientInfo() != null) {
      builder.setClientInfo(properties.getClientInfo());
    }
    if (properties.getNetworkTimeout() != null) {
      builder.setNetworkTimeout(properties.getNetworkTimeout());
    }
    if (properties.getLoggerType() != null) {
      builder.setLoggerType(properties.getLoggerType());
    }
    //    public Builder setMaintenanceExecutor(final Executor maintenanceExecutor) {
    //    public Builder setNetworkTimeoutExecutor(final Executor networkTimeoutExecutor) {
    //    public Builder setListener(final HexaneListener listener) {
    //    public Builder setExceptionHandler(final ExceptionHandler handler) {
    return builder;
  }

  static void setProps(final HexaneProperties properties, final Properties props) {
    final String username = properties.getUsername();
    if (username != null) {
      props.put("user", username);
      props.put("username", username);
    }
    final String password = properties.getPassword();
    if (password != null) {
      props.put("password", password);
    }
    final String url = properties.getUrl();
    if (url != null) {
      props.put("url", url);
      props.put("jdbc-url", url);
    }
    if (properties.getProperties() != null) {
      props.putAll(properties.getProperties());
    }
  }

  static <T> T bind(final T dataSource, final HexaneProperties properties) {
    final Properties props = new Properties();
    SpringUtil.setProps(properties, props);
    SpringUtil.bindProperties(dataSource, props);
    return dataSource;
  }

  private static void bindProperties(final Object target, final Properties properties) {
    final Binder binder = new Binder(new MapConfigurationPropertySource(properties));
    binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(target));
  }

  static Object newInstance(final ClassLoader classLoader, final String className) throws ClassNotFoundException {
    final Class<?> dataSourceClass = ClassUtils.forName(className, classLoader);
    return BeanUtils.instantiateClass(dataSourceClass);
  }

  private static class DriverDataSource implements DataSource {

    final Driver driver;
    final String url;
    final Properties properties;

    public DriverDataSource(final Driver driver, final String url, final Properties properties) {
      this.driver = driver;
      this.url = url;
      this.properties = properties;
    }

    @Override
    public Connection getConnection() throws SQLException {
      return driver.connect(url, properties);
    }

    @Override
    public Connection getConnection(final String username, final String password)
        throws SQLException {
      throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
      return null;
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
      return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
      throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setLogWriter(final PrintWriter out) throws SQLException {
      throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setLoginTimeout(final int seconds) throws SQLException {
      throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
      throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return driver.getParentLogger();
    }
  }
}
