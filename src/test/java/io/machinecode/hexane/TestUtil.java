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

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLNonTransientConnectionException;
import java.util.Collections;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
class TestUtil {

  static SQLException getNormal() {
    return new SQLException("", "00000");
  }

  static SQLException getFatalState() {
    return new SQLException("", Util.CONNECTION_ERROR + "000");
  }

  static SQLClientInfoException getFatalClientInfoState() {
    return new SQLClientInfoException("", Util.CONNECTION_ERROR + "000", Collections.emptyMap());
  }

  static SQLClientInfoException getNormalClientInfo() {
    return new SQLClientInfoException("", "00000", Collections.emptyMap());
  }

  static SQLFeatureNotSupportedException getNormalNotSupported() {
    return new SQLFeatureNotSupportedException("", "00000");
  }

  static SQLFeatureNotSupportedException getFatalNotSupported() {
    return new SQLFeatureNotSupportedException("", Util.CONNECTION_ERROR + "000");
  }

  static SQLNonTransientConnectionException getFatalNoConnection() {
    return new SQLNonTransientConnectionException("", Util.CONNECTION_ERROR + "000");
  }
}
