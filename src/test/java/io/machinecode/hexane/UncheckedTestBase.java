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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.machinecode.hexane.Defaults.Builder;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
public abstract class UncheckedTestBase<R, D extends R> extends Assert {

  final Class<R> realType;
  final Class<D> delegateType;

  Terminal terminal;
  R real;
  D delegate;
  Defaults defaults;
  Config config;

  public UncheckedTestBase(final Class<R> realType, final Class<D> delegateType) {
    this.realType = realType;
    this.delegateType = delegateType;
  }

  @Before
  public void setUp() throws Exception {
    defaults = new Builder().build();
    terminal = mock(Terminal.class);
    config = Hexane.builder().getConfig();
    when(terminal.getConfig()).thenReturn(config);
    real = mock(realType);
    delegate = create(terminal, real);
  }

  protected abstract D create(final Terminal terminal, final R delegate) throws Exception;

  protected String[] getIgnored() {
    return new String[] {};
  }

  @Test
  public void testNormalCompletes() throws Exception {
    final Set<String> ignore = new HashSet<>(Arrays.asList(getIgnored()));
    for (final Method method : realType.getMethods()) {
      if (ignore.contains(method.getName())) {
        continue;
      }
      setUp();

      final Object[] args = new Object[method.getParameterCount()];
      for (int i = 0; i < args.length; i++) {
        args[i] = getDefault(method.getParameterTypes()[i], realType);
      }
      try {
        method.invoke(delegate, args);
        method.invoke(verify(real), args);
      } catch (final IllegalArgumentException e) {
        throw new IllegalStateException(method.getName() + " " + Arrays.toString(args));
      }
    }
  }

  @Test
  public void testNonFatalThrows() throws Throwable {
    final Set<String> ignore = new HashSet<>(Arrays.asList(getIgnored()));
    for (final Method method : realType.getMethods()) {
      if (ignore.contains(method.getName())) {
        continue;
      }
      setUp();

      final Object[] args = new Object[method.getParameterCount()];
      for (int i = 0; i < args.length; i++) {
        args[i] = getDefault(method.getParameterTypes()[i], realType);
      }
      Class<?> exceptionType = null;
      for (final Class<?> type : method.getExceptionTypes()) {
        if (type.equals(SQLException.class)) {
          exceptionType = type;
          method.invoke(doThrow(TestUtil.getNormal()).when(real), args);
          break;
        }
        if (type.equals(SQLClientInfoException.class)) {
          exceptionType = type;
          method.invoke(doThrow(TestUtil.getNormalClientInfo()).when(real), args);
          break;
        }
      }

      try {
        method.invoke(delegate, args);
        fail(method.getName());
      } catch (final AssertionError e) {
        throw e;
      } catch (final InvocationTargetException e) {
        assertEquals(exceptionType, e.getTargetException().getClass());
      }
    }
  }

  static Object getDefault(final Class<?> clazz, final Class<?> type) throws Exception {
    if (clazz.equals(int.class)) {
      return 0;
    }
    if (clazz.equals(long.class)) {
      return 0L;
    }
    if (clazz.equals(byte.class)) {
      return (byte) 0;
    }
    if (clazz.equals(short.class)) {
      return (short) 0;
    }
    if (clazz.equals(float.class)) {
      return 0F;
    }
    if (clazz.equals(double.class)) {
      return 0D;
    }
    if (clazz.equals(boolean.class)) {
      return false;
    }
    if (clazz.equals(String.class)) {
      return "";
    }
    if (clazz.equals(URL.class)) {
      return new URL("http://127.0.0.1");
    }
    if (clazz.equals(Class.class)) {
      return type;
    }
    if (clazz.isArray()) {
      return Array.newInstance(clazz.getComponentType(), 0);
    }
    if ((clazz.getModifiers() & Modifier.FINAL) != 0) {
      throw new IllegalStateException(clazz.getSimpleName());
    }
    if (!clazz.isPrimitive()) {
      return mock(clazz);
    }
    throw new IllegalStateException(clazz.getSimpleName());
  }
}
