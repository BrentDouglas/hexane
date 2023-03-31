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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public abstract class CheckedTestBase<R, D extends R> extends UncheckedTestBase<R, D> {

  public CheckedTestBase(final Class<R> realType, final Class<D> delegateType) {
    super(realType, delegateType);
  }

  @Test
  public void testFatalCallsKill() throws Throwable {
    final Set<String> ignore = new HashSet<>(Arrays.asList(getIgnored()));
    for (final Method method : realType.getMethods()) {
      if (ignore.contains(method.getName()) || method.isDefault()) {
        continue;
      }
      setUp();

      final Object[] args = new Object[method.getParameterCount()];
      for (int i = 0; i < args.length; i++) {
        args[i] = getDefault(method.getParameterTypes()[i], realType);
      }
      for (final Class<?> type : method.getExceptionTypes()) {
        if (type.equals(SQLException.class)) {
          method.invoke(doThrow(TestUtil.getFatalState()).when(real), args);
          break;
        }
        if (type.equals(SQLClientInfoException.class)) {
          method.invoke(doThrow(TestUtil.getFatalClientInfoState()).when(real), args);
          break;
        }
      }

      try {
        method.invoke(delegate, args);
        fail(method.getName());
      } catch (final AssertionError e) {
        throw e;
      } catch (final InvocationTargetException e) {
        method.invoke(verify(real), args);
        if (e.getTargetException() instanceof SQLException) {
          assertKillException((SQLException) e.getTargetException());
        } else {
          throw e.getTargetException();
        }
      }
    }
  }

  void assertKillException(final SQLException e) {
    verify(terminal).kill(e);
  }
}
