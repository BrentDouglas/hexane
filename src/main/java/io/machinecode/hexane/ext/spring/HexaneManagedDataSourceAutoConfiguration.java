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

import io.machinecode.hexane.Config.Builder;
import io.machinecode.hexane.HexaneDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DataSourceProperties.class, HexaneProperties.class})
@ConditionalOnClass(HexaneDataSource.class)
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(
    name = "spring.datasource.type",
    havingValue = "io.machinecode.hexane.HexaneManagedDataSource",
    matchIfMissing = true)
class HexaneManagedDataSourceAutoConfiguration {

  @Bean
  public DataSource dataSource(final HexaneProperties properties)
      throws SQLException, NamingException, ClassNotFoundException {
    final Builder builder = SpringUtil.create(properties);
    if (properties.getJndiName() != null) {
      final InitialContext ctx = new InitialContext();
      final Object ret = ctx.lookup(properties.getJndiName());
      return builder.buildManagedDataSource(SpringUtil.bind((DataSource) ret, properties));
    } else {
      return builder.buildManagedDataSource(SpringUtil.getDriverDataSource(properties));
    }
  }
}
