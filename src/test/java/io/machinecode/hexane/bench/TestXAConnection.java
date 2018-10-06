package io.machinecode.hexane.bench;

import javax.sql.ConnectionEventListener;
import javax.sql.DataSource;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import java.sql.Connection;
import java.sql.SQLException;

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
