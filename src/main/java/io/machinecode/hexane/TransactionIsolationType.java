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

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public enum TransactionIsolationType {
  TRANSACTION_NONE(Connection.TRANSACTION_NONE),
  TRANSACTION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
  TRANSACTION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
  TRANSACTION_REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
  TRANSACTION_SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

  final int value;

  TransactionIsolationType(final int value) {
    this.value = value;
  }
}
