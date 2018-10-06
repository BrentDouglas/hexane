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

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

/**
 * A datasource backed by a {@link DataSource} that pools {@link Connection}'s.
 *
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public final class HexaneDataSource extends BaseDataSource<Connection, HexanePool, DataSource>
    implements DataSource, AutoCloseable {
  private final Defaults defaults;

  HexaneDataSource(final HexanePool pool, final DataSource dataSource, final Defaults defaults) {
    super(pool, dataSource);
    this.defaults = defaults;
  }

  @Override
  public Connection getConnection() throws SQLException {
    final Pooled<Connection> val = pool.take();
    return new HexaneConnection(pool.getConfig(), val, defaults);
  }

  @Override
  public Connection getConnection(final String user, final String password) throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public <T> T unwrap(final Class<T> iface) throws SQLException {
    try {
      return Util.unwrap(iface, this, dataSource);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }

  @Override
  public boolean isWrapperFor(final Class<?> iface) throws SQLException {
    try {
      return Util.isWrapperFor(iface, this, dataSource);
    } catch (final SQLException e) {
      throw Util.handleFatalSQL(pool, e);
    }
  }
}
