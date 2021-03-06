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
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

/**
 * A datasource backed by a {@link DataSource} that pools {@link Connection}'s but exposes them as
 * {@link PooledConnection}.
 *
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public final class HexaneManagedDataSource
    extends BaseDataSource<Connection, HexanePool, DataSource>
    implements ConnectionPoolDataSource, AutoCloseable {
  private final Defaults defaults;

  HexaneManagedDataSource(
      final HexanePool pool, final DataSource dataSource, final Defaults defaults) {
    super(pool, dataSource);
    this.defaults = defaults;
  }

  @Override
  public PooledConnection getPooledConnection() throws SQLException {
    final Pooled<Connection> val = pool.take();
    return new HexaneManagedConnection(pool.getConfig(), val, defaults);
  }

  @Override
  public PooledConnection getPooledConnection(final String user, final String password)
      throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return getPooledConnection().getConnection();
  }
}
