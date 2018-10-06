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

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class Msg {
  static final String CONNECTION_CLOSED = "Connection is closed";
  static final String EXCEPTION_OPENING_CONNECTION =
      "The datasource threw while establishing a connection";
  static final String EXCEPTION_CLOSING_CONNECTION =
      "The pooled connection threw while closing resources";
  static final String POOL_IS_CLOSED = "The pool is closed";
  static final String POOL_TIMEOUT = "No connection available";
  static final String HOLDABILITY =
      "Holdability may only be set to one of"
          + " ResultSet#CLOSE_CURSORS_AT_COMMIT"
          + " or ResultSet#HOLD_CURSORS_OVER_COMMIT";
  static final String TRANSACTION_ISOLATION =
      "Transaction Isolation may only be set to one of"
          + " Connection#TRANSACTION_NONE, "
          + " Connection#TRANSACTION_READ_UNCOMMITTED, "
          + " Connection#TRANSACTION_READ_COMMITTED, "
          + " Connection#TRANSACTION_REPEATABLE_READ or"
          + " Connection#TRANSACTION_SERIALIZABLE";
  static final String VALIDATION_TIMEOUT = "validationTimeout must be set";
  static final String MAX_POOL_SIZE = "maxCoreSize must be set";
  static final String CORE_POOL_SIZE = "corePoolSize must be set";

  private Msg() {}
}
