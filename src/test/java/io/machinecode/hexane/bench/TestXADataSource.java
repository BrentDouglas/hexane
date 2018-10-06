package io.machinecode.hexane.bench;

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public class TestXADataSource implements XADataSource {

  private final DataSource db;

  public TestXADataSource(final DataSource db) {
    this.db = db;
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return null;
  }

  @Override
  public void setLogWriter(final PrintWriter out) throws SQLException {}

  @Override
  public void setLoginTimeout(final int seconds) throws SQLException {}

  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }

  @Override
  public XAConnection getXAConnection() throws SQLException {
    return new TestXAConnection(db);
  }

  @Override
  public XAConnection getXAConnection(final String user, final String password)
      throws SQLException {
    return null;
  }
}
