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
import javax.sql.XAConnection;
import javax.sql.XADataSource;

/**
 * A datasource backed by an {@link XADataSource} that pools {@link XAConnection}'s.
 *
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public final class HexaneXADataSource
    extends BaseDataSource<XAConnection, HexaneXAPool, XADataSource>
    implements XADataSource, AutoCloseable {
  private final Defaults defaults;

  HexaneXADataSource(
      final HexaneXAPool pool, final XADataSource dataSource, final Defaults defaults) {
    super(pool, dataSource);
    this.defaults = defaults;
  }

  @Override
  public XAConnection getXAConnection() throws SQLException {
    final Pooled<XAConnection> val = pool.take();
    return new HexaneXAConnection(pool.getConfig(), val, defaults);
  }

  @Override
  public XAConnection getXAConnection(final String user, final String password)
      throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return getXAConnection().getConnection();
  }
}
