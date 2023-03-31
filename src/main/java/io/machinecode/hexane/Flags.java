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

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
final class Flags {
  static int AUTO_COMMIT = 1;
  static int READ_ONLY = 1 << 1;
  static int HOLDABILITY = 1 << 2;
  static int NETWORK_TIMEOUT = 1 << 3;
  static int TRANSACTION_ISOLATION = 1 << 4;
  static int CATALOG = 1 << 5;
  static int TYPE_MAP = 1 << 6;
  static int CLIENT_INFO = 1 << 7;
  static int SCHEMA = 1 << 8;

  private Flags() {}

  static boolean isSet(final int mask, final int flags, final int supported) {
    return (flags & mask) != 0 && (supported & mask) != 0;
  }

  static boolean isSupported(final int mask, final int supported) {
    return (supported & mask) != 0;
  }
}
