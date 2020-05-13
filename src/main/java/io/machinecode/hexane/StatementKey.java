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

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Base64;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
final class StatementKey {

  final byte[] value;

  StatementKey(final byte[] value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final StatementKey that = (StatementKey) o;
    return Arrays.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(value);
  }

  @Override
  public String toString() {
    final StringBuilder ret = new StringBuilder("StatementKey{type=");
    final byte[] value = this.value;
    final boolean callable = value[0] == 2;
    ret.append(callable ? "CallableStatement" : "PreparedStatement");
    final byte type = value[1];
    ret.append(", resultSetType=");
    switch (type) {
      case 1:
        ret.append("TYPE_FORWARD_ONLY");
        break;
      case 2:
        ret.append("TYPE_SCROLL_INSENSITIVE");
        break;
      case 3:
        ret.append("TYPE_SCROLL_SENSITIVE");
        break;
      default:
        throw new IllegalStateException();
    }
    final byte concurrency = value[2];
    ret.append(", resultSetConcurrency=");
    switch (concurrency) {
      case 1:
        ret.append("CONCUR_READ_ONLY");
        break;
      case 2:
        ret.append("CONCUR_UPDATABLE");
        break;
      default:
        throw new IllegalStateException();
    }
    final byte holdability = value[3];
    ret.append(", resultSetHoldability=");
    switch (holdability) {
      case 1:
        ret.append("CLOSE_CURSORS_AT_COMMIT");
        break;
      case 2:
        ret.append("HOLD_CURSORS_OVER_COMMIT");
        break;
      default:
        throw new IllegalStateException();
    }
    final byte flags = value[4];
    ret.append(", keys=");
    switch (flags) {
      case 1:
        ret.append("RETURN_GENERATED_KEYS");
        break;
      case 2:
        ret.append("NO_GENERATED_KEYS");
        break;
      default:
        throw new IllegalStateException();
    }
    final Bits bits = Bits.getBits();
    final int sqlLen = bits.get32(value, 5);
    ret.append(", sqlLength=");
    ret.append(sqlLen);
    final int nameLen = bits.get32(value, 9);
    ret.append(", columnNames=");
    if (nameLen == Config.UNSET) {
      ret.append("null");
    } else {
      ret.append("String[");
      ret.append(nameLen);
      ret.append("]");
    }
    final int indexLen = bits.get32(value, 13);
    ret.append(", columnIndices=");
    if (indexLen == Config.UNSET) {
      ret.append("null");
    } else {
      ret.append("int[");
      ret.append(indexLen);
      ret.append("]");
    }
    ret.append(", hash=");
    final byte[] bytes = new byte[value.length - 17];
    System.arraycopy(value, 17, bytes, 0, bytes.length);
    final String hash = Base64.getUrlEncoder().encodeToString(bytes);
    ret.append(hash);
    return ret.append("}").toString();
  }

  static StatementKey create(
      String sql,
      boolean callable,
      int resultSetType,
      int resultSetConcurrency,
      int resultSetHoldability,
      final int flags,
      String[] columnNames,
      int[] columnIndexes)
      throws SQLException {
    try {
      final byte statementType = (byte) (callable ? 2 : 1);
      final byte type;
      switch (resultSetType) {
        case ResultSet.TYPE_FORWARD_ONLY:
          type = 1;
          break;
        case ResultSet.TYPE_SCROLL_INSENSITIVE:
          type = 2;
          break;
        case ResultSet.TYPE_SCROLL_SENSITIVE:
          type = 3;
          break;
        default:
          throw new SQLException(); // TODO
      }
      final byte concurrency;
      switch (resultSetConcurrency) {
        case ResultSet.CONCUR_READ_ONLY:
          concurrency = 1;
          break;
        case ResultSet.CONCUR_UPDATABLE:
          concurrency = 2;
          break;
        default:
          throw new SQLException(); // TODO
      }
      final byte holdability;
      switch (resultSetHoldability) {
        case ResultSet.CLOSE_CURSORS_AT_COMMIT:
          holdability = 1;
          break;
        case ResultSet.HOLD_CURSORS_OVER_COMMIT:
          holdability = 2;
          break;
        default:
          throw new SQLException(); // TODO
      }
      final byte flag;
      switch (flags) {
        case Statement.RETURN_GENERATED_KEYS:
          flag = 1;
          break;
        case Statement.NO_GENERATED_KEYS:
          flag = 2;
          break;
        default:
          flag = 0;
      }
      final MessageDigest digest = MessageDigest.getInstance("SHA-1");
      if (columnNames != null) {
        for (final String columnName : columnNames) {
          digest.update(columnName.getBytes(StandardCharsets.UTF_8));
        }
      }
      final Bits bits = Bits.getBits();
      if (columnIndexes != null) {
        for (final int columnIndex : columnIndexes) {
          bits.update(digest, columnIndex);
        }
      }
      final String trim = sql.trim();
      digest.update(trim.getBytes(StandardCharsets.UTF_8));
      final byte[] bytes = digest.digest();
      final byte[] ret = new byte[17 + bytes.length];
      ret[0] = statementType;
      ret[1] = type;
      ret[2] = concurrency;
      ret[3] = holdability;
      ret[4] = flag;
      bits.set32(ret, 5, trim.length());
      bits.set32(ret, 9, columnNames == null ? Config.UNSET : columnNames.length);
      bits.set32(ret, 13, columnIndexes == null ? Config.UNSET : columnIndexes.length);
      System.arraycopy(bytes, 0, ret, 17, bytes.length);
      return new StatementKey(ret);
    } catch (final NoSuchAlgorithmException e) {
      throw new AssertionError(e);
    }
  }

  abstract static class Bits {

    static Bits getBits() {
      return ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? LEBits.INSTANCE : BEBits.INSTANCE;
    }

    abstract int get32(final byte[] input, final int idx);

    abstract void set32(final byte[] output, final int idx, final int val);

    abstract void update(final MessageDigest output, final int val);

    static class LEBits extends Bits {
      static final LEBits INSTANCE = new LEBits();

      public int get32(final byte[] input, final int idx) {
        final int c0 = input[idx];
        final int c1 = input[idx + 1];
        final int c2 = input[idx + 2];
        final int c3 = input[idx + 3];
        return c0 | (c1 << 8) | (c2 << 16) | (c3 << 24);
      }

      public void set32(final byte[] output, final int idx, final int val) {
        output[idx] = (byte) val;
        output[idx + 1] = (byte) (val >> 8);
        output[idx + 2] = (byte) (val >> 16);
        output[idx + 3] = (byte) (val >> 24);
      }

      public void update(final MessageDigest output, final int val) {
        output.update((byte) val);
        output.update((byte) (val >> 8));
        output.update((byte) (val >> 16));
        output.update((byte) (val >> 24));
      }
    }

    static class BEBits extends Bits {
      static final BEBits INSTANCE = new BEBits();

      public int get32(final byte[] input, final int idx) {
        final int c0 = input[idx + 3];
        final int c1 = input[idx + 2];
        final int c2 = input[idx + 1];
        final int c3 = input[idx];
        return c0 | (c1 << 8) | (c2 << 16) | (c3 << 24);
      }

      public void set32(final byte[] output, final int idx, final int val) {
        output[idx + 3] = (byte) val;
        output[idx + 2] = (byte) (val >> 8);
        output[idx + 1] = (byte) (val >> 16);
        output[idx] = (byte) (val >> 24);
      }

      public void update(final MessageDigest output, final int val) {
        output.update((byte) (val >> 24));
        output.update((byte) (val >> 16));
        output.update((byte) (val >> 8));
        output.update((byte) val);
      }
    }
  }
}
