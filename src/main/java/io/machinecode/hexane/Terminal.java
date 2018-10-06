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

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
abstract class Terminal {

  final Config config;

  protected Terminal(final Config config) {
    this.config = config;
  }

  Config getConfig() {
    return config;
  }

  abstract void kill(final SQLException e);

  abstract void evict(final PreparedStatement statement, final SQLException e);

  abstract void enlist(final AutoCloseable it) throws SQLException;

  abstract void delist(final AutoCloseable it) throws SQLException;

  abstract void registerChecked(final CheckedPreparedStatement it, final PreparedStatement real)
      throws SQLException;
}
