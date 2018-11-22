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
package io.machinecode.hexane.bench;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.ConnectionEventListener;
import javax.sql.DataSource;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class TestXAConnection implements XAConnection {
  private final DataSource db;

  public TestXAConnection(final DataSource db) {
    this.db = db;
  }

  @Override
  public XAResource getXAResource() throws SQLException {
    return null;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return db.getConnection();
  }

  @Override
  public void close() throws SQLException {}

  @Override
  public void addConnectionEventListener(final ConnectionEventListener listener) {}

  @Override
  public void removeConnectionEventListener(final ConnectionEventListener listener) {}

  @Override
  public void addStatementEventListener(final StatementEventListener listener) {}

  @Override
  public void removeStatementEventListener(final StatementEventListener listener) {}
}
