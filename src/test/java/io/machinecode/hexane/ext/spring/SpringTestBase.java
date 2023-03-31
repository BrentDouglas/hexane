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
package io.machinecode.hexane.ext.spring;

import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
@RunWith(SpringRunner.class)
public abstract class SpringTestBase extends Assert {

  @BeforeClass
  public static void setNamingFactory() throws Exception {
    NamingManager.setInitialContextFactoryBuilder(new Factory());
  }

  @Test
  public void dataSource() throws Exception {
    try (final Connection conn = getConnection()) {
      try (final PreparedStatement stat = conn.prepareStatement("SELECT * FROM (VALUES(1))")) {
        try (final ResultSet ret = stat.executeQuery()) {
          assertTrue(ret.next());
          assertEquals(1, ret.getInt(1));
          assertFalse(ret.next());
        }
      }
    }
  }

  abstract Connection getConnection() throws SQLException;

  public static class Factory implements InitialContextFactory, InitialContextFactoryBuilder {

    static Context INSTANCE = mock(Context.class);

    @Override
    public Context getInitialContext(final Hashtable<?, ?> environment) throws NamingException {
      return INSTANCE;
    }

    @Override
    public InitialContextFactory createInitialContextFactory(final Hashtable<?, ?> environment)
        throws NamingException {
      return this;
    }
  }
}
