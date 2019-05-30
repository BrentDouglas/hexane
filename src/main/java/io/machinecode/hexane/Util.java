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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Wrapper;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class Util {

  static final String CONNECTION_ERROR = "08";

  private Util() {}

  static <T> T unwrap(final Class<T> as, final Wrapper ours, final Wrapper theirs)
      throws SQLException {
    if (as.isInstance(ours)) {
      return as.cast(ours);
    }
    if (as.isInstance(theirs)) {
      return as.cast(theirs);
    }
    return theirs.unwrap(as);
  }

  static <T> boolean isWrapperFor(final Class<T> as, final Wrapper ours, final Wrapper theirs)
      throws SQLException {
    if (as.isAssignableFrom(ours.getClass())) {
      return true;
    }
    if (as.isAssignableFrom(theirs.getClass())) {
      return true;
    }
    return theirs.isWrapperFor(as);
  }

  static void throwNullable(final SQLException e) throws SQLException {
    if (e == null) {
      return;
    }
    throw e;
  }

  static void cleanupConnection(final Terminal xa, final Pooled<?> val, final SQLException ex)
      throws SQLException {
    if (ex != null) {
      if (xa.getConfig().getExceptionHandler().isConnectionErrorFatal(ex)) {
        xa.kill(ex);
      } else {
        final SQLException ge = val.close(false);
        if (ge != null) {
          ex.addSuppressed(ge);
        }
      }
      throw ex;
    } else {
      Util.throwNullable(val.close(false));
    }
  }

  static <E extends SQLException> E handleStatementFatalSQL(
      final Terminal xa, final PreparedStatement statement, final E e) {
    final ExceptionHandler exceptionHandler = xa.getConfig().getExceptionHandler();
    if (exceptionHandler.isStatementErrorFatal(e)) {
      xa.evict(statement, e);
    }
    return Util.handleFatalSQL(xa, e);
  }

  static <E extends SQLException> E handleFatalSQL(final Terminal xa, final E e) {
    if (xa.getConfig().getExceptionHandler().isConnectionErrorFatal(e)) {
      xa.kill(e);
    }
    return e;
  }

  static <E extends SQLException> E handleFatalSQL(final BasePool<?> pool, final E e) {
    try {
      if (pool.getConfig().getExceptionHandler().isConnectionErrorFatal(e)) {
        pool.close();
      }
    } catch (final Exception se) {
      e.addSuppressed(se);
    }
    return e;
  }

  static SQLException close(
      final Config config, SQLException exception, final BooleanRef ret, final AutoCloseable it) {
    final boolean oldFatal = ret.getVal();
    try {
      it.close();
    } catch (final Exception e) {
      final SQLException se;
      final boolean newFatal;
      if (e instanceof SQLException) {
        se = (SQLException) e;
        newFatal = config.getExceptionHandler().isConnectionErrorFatal(se);
      } else {
        se = new SQLException(e);
        // TODO Maybe but if its throwing something other than an SQLException this is probably
        // reasonable
        newFatal = true;
      }
      if (exception == null) {
        exception = se;
      } else {
        if (newFatal && !oldFatal) {
          se.addSuppressed(exception);
          exception = se;
        } else {
          exception.addSuppressed(se);
        }
      }
      ret.setVal(oldFatal || newFatal);
    }
    return exception;
  }

  static SQLException close(final AutoCloseable conn, SQLException se) {
    if (conn == null) {
      return se;
    }
    try {
      conn.close();
    } catch (final Exception e) {
      if (se == null) {
        se = new SQLException(e);
      } else {
        se.addSuppressed(e);
      }
    }
    return se;
  }

  static void close(final AutoCloseable conn) {
    if (conn == null) {
      return;
    }
    try {
      conn.close();
    } catch (final Exception e) {
      //
    }
  }

  static <T> T defaultIfNull(final T a, final T b) {
    return a == null ? b : a;
  }
}
