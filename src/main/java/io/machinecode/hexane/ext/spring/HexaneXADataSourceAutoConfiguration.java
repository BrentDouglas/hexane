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

import io.machinecode.hexane.HexaneXADataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.XADataSourceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class, XADataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DataSourceProperties.class, HexaneProperties.class})
@ConditionalOnClass({XADataSource.class, TransactionManager.class})
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(
    name = "spring.datasource.type",
    havingValue = "io.machinecode.hexane.HexaneXADataSource",
    matchIfMissing = true)
public class HexaneXADataSourceAutoConfiguration {

  @Bean
  public DataSource dataSource(final XADataSourceWrapper wrapper, final HexaneProperties properties)
      throws Exception {
    final XADataSource xaDataSource = createXaDataSource(properties);
    // TODO How to make spring close this
    final HexaneXADataSource pool = SpringUtil.create(properties).buildXADataSource(xaDataSource);
    return wrapper.wrapDataSource(pool);
  }

  private XADataSource createXaDataSource(final HexaneProperties properties)
      throws NamingException, ClassNotFoundException {
    if (properties.getJndiName() != null) {
      final InitialContext ctx = new InitialContext();
      final Object ret = ctx.lookup(properties.getJndiName());
      return SpringUtil.bind((XADataSource) ret, properties);
    } else if (properties.getDataSourceClassName() != null) {
      final String className = properties.getDataSourceClassName();
      Assert.state(StringUtils.hasLength(className), "No XA DataSource class name specified");
      final XADataSource dataSource =
          (XADataSource) SpringUtil.newInstance(properties.getClassLoader(), className);
      return SpringUtil.bind(dataSource, properties);
    }
    throw new IllegalStateException();
  }
}
