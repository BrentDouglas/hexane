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

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import java.lang.reflect.Method;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.derby.jdbc.EmbeddedXADataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.hsqldb.jdbc.JDBCDataSource;
import org.hsqldb.jdbc.pool.JDBCPooledDataSource;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.mariadb.jdbc.MariaDbDataSource;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.xa.PGXADataSource;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class TestBase extends Assert {

  static List<Setup> dataSources;

  @BeforeClass
  public static void beforeClass() throws Exception {
    dataSources = loadSetup(dataSources, System.getProperties());
  }

  public static List<Setup> loadSetup(final List<Setup> dataSources, final Properties props)
      throws Exception {
    if (dataSources != null) {
      return dataSources;
    }
    final Map<String, Map<String, String>> vals = new HashMap<>();
    for (final String key : props.stringPropertyNames()) {
      if (!key.startsWith("test.db.")) {
        continue;
      }
      final String[] parts = key.split("\\.", 4);
      final String name = parts[2];
      final String prop = parts[3];
      vals.computeIfAbsent(name, k -> new HashMap<>()).put(prop, props.getProperty(key));
    }
    // Remove any root props with versions
    final Map<String, Map<String, String>> merged = new HashMap<>();
    for (final String name : new ArrayList<>(vals.keySet())) {
      final int ver = name.indexOf('_');
      final String type;
      if (ver != -1) {
        type = name.substring(0, ver);
        if (vals.containsKey(type)) {
          merged.put(type, vals.remove(type));
        }
      }
    }
    final List<Setup> ret = new ArrayList<>();
    for (final Map.Entry<String, Map<String, String>> e : vals.entrySet()) {
      final String name = e.getKey();
      final Map<String, String> dbProps = e.getValue();
      final int ver = name.indexOf('_');
      final String type;
      if (ver == -1) {
        type = name;
      } else {
        type = name.substring(0, ver);
        if (merged.containsKey(type)) {
          dbProps.putAll(merged.get(type));
        }
      }
      ret.add(new Setup(name, type, dbProps.get("user"), dbProps.get("password"), dbProps));
    }
    ret.sort(Comparator.comparing(it -> it.name));
    return Collections.unmodifiableList(ret);
  }

  public static Setup loadSetup(final String value, final Properties props) throws Exception {
    final Map<String, Map<String, String>> vals = new HashMap<>();
    for (final String key : props.stringPropertyNames()) {
      if (!key.startsWith("test.db.")) {
        continue;
      }
      final String[] parts = key.split("\\.", 4);
      final String name = parts[2];
      final String prop = parts[3];
      vals.computeIfAbsent(name, k -> new HashMap<>()).put(prop, props.getProperty(key));
    }
    // Remove any root props with versions
    final Map<String, Map<String, String>> merged = new HashMap<>();
    for (final String name : new ArrayList<>(vals.keySet())) {
      final int ver = name.indexOf('_');
      final String type;
      if (ver != -1) {
        type = name.substring(0, ver);
        if (vals.containsKey(type)) {
          merged.put(type, vals.remove(type));
        }
      }
    }
    final Map<String, String> dbProps = vals.get(value);
    final int ver = value.indexOf('_');
    final String type;
    if (ver == -1) {
      type = value;
    } else {
      type = value.substring(0, ver);
      if (merged.containsKey(type)) {
        dbProps.putAll(merged.get(type));
      }
    }
    return new Setup(value, type, dbProps.get("user"), dbProps.get("password"), dbProps);
  }

  private static XADataSource getXA(final String type, final Map<String, String> props)
      throws Exception {
    final XADataSource ret;
    switch (type) {
      case "pg":
        ret = new PGXADataSource();
        DriverManager.registerDriver(new org.postgresql.Driver());
        break;
      case "mysql":
        ret = new MysqlXADataSource();
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        break;
      case "mariadb":
        ret = new MariaDbDataSource();
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        break;
      case "h2":
        ret = new JdbcDataSource();
        DriverManager.registerDriver(new org.h2.Driver());
        break;
      case "hsqldb":
        ret = new JDBCXADataSource();
        DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
        break;
      case "derby":
        ret = new EmbeddedXADataSource();
        //        DriverManager.registerDriver(new org.apache.derby.jdbc.Driver42());
        break;
      default:
        throw new AssertionError();
    }
    setProps(props, ret);
    return ret;
  }

  private static ConnectionPoolDataSource getPooled(
      final String type, final Map<String, String> props) throws Exception {
    final ConnectionPoolDataSource ret;
    switch (type) {
      case "pg":
        ret = new PGConnectionPoolDataSource();
        DriverManager.registerDriver(new org.postgresql.Driver());
        break;
      case "mysql":
        ret = new MysqlConnectionPoolDataSource();
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        break;
      case "mariadb":
        ret = new MariaDbDataSource();
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        break;
      case "h2":
        ret = new JdbcDataSource();
        DriverManager.registerDriver(new org.h2.Driver());
        break;
      case "hsqldb":
        ret = new JDBCPooledDataSource();
        DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
        break;
      case "derby":
        ret = new EmbeddedConnectionPoolDataSource();
        //        DriverManager.registerDriver(new org.apache.derby.jdbc.Driver42());
        break;
      default:
        throw new AssertionError();
    }
    setProps(props, ret);
    return ret;
  }

  private static DataSource getNormal(final String type, final Map<String, String> props)
      throws Exception {
    final DataSource ret;
    switch (type) {
      case "pg":
        ret = new PGSimpleDataSource();
        DriverManager.registerDriver(new org.postgresql.Driver());
        break;
      case "mysql":
        ret = new MysqlDataSource();
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        break;
      case "mariadb":
        ret = new MariaDbDataSource();
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        break;
      case "h2":
        ret = new JdbcDataSource();
        DriverManager.registerDriver(new org.h2.Driver());
        break;
      case "hsqldb":
        ret = new JDBCDataSource();
        DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
        break;
      case "derby":
        ret = new EmbeddedDataSource();
        //        DriverManager.registerDriver(new org.apache.derby.jdbc.Driver42());
        break;
      default:
        throw new AssertionError();
    }
    setProps(props, ret);
    return ret;
  }

  private static void setProps(final Map<String, String> props, final Object ret) throws Exception {
    for (final Method method : ret.getClass().getMethods()) {
      if (!method.getName().startsWith("set")) {
        continue;
      }
      final String prop =
          method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
      final String value = props.get(prop);
      if (value != null) {
        method.invoke(ret, value);
      }
    }
  }

  public static class Setup {
    public final String name;
    public final String type;
    public final String user;
    public final String password;
    private final Map<String, String> props;
    private DataSource dataSource;
    private XADataSource xaDataSource;
    private ConnectionPoolDataSource pooledDataSource;

    private Setup(
        final String name,
        final String type,
        final String user,
        final String password,
        final Map<String, String> props) {
      this.name = name;
      this.type = type;
      this.user = user;
      this.password = password;
      this.props = props;
    }

    public XADataSource getXaDataSource() throws Exception {
      return xaDataSource == null ? xaDataSource = getXA(type, props) : xaDataSource;
    }

    public ConnectionPoolDataSource getPooledDataSource() throws Exception {
      return pooledDataSource == null
          ? pooledDataSource = getPooled(type, props)
          : pooledDataSource;
    }

    public DataSource getDataSource() throws Exception {
      return dataSource == null ? dataSource = getNormal(type, props) : dataSource;
    }

    public String getUrl() {
      for (final String value : props.values()) {
        if (value.contains("jdbc:")) {
          return value;
        }
      }
      return null;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
