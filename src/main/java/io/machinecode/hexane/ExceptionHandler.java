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

import java.sql.SQLException;

/**
 * Hooks to let the
 *
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
interface ExceptionHandler {
  /**
   * Decide if the exception thrown from the underlying connection means that it needs to be evicted
   * from the pool
   *
   * @param e The error thrown by the underlying conection.
   */
  default boolean isConnectionErrorFatal(final SQLException e) {
    final String state = e.getSQLState();
    return state == null || state.length() < 2 || state.startsWith(Util.CONNECTION_ERROR);
  }

  /**
   * Decide if the exception thrown from the underlying statement means that it needs to be evicted
   * from the pool
   *
   * @param e The error thrown by the underlying statement.
   */
  default boolean isStatementErrorFatal(final SQLException e) {
    return false;
  }
}
