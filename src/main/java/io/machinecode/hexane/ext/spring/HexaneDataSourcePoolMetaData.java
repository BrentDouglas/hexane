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

import io.machinecode.hexane.HexaneDataSource;
import org.springframework.boot.jdbc.metadata.AbstractDataSourcePoolMetadata;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
final class HexaneDataSourcePoolMetaData extends AbstractDataSourcePoolMetadata<HexaneDataSource> {
  public HexaneDataSourcePoolMetaData(final HexaneDataSource dataSource) {
    super(dataSource);
  }

  @Override
  public Integer getActive() {
    return null;
  }

  @Override
  public Integer getMax() {
    return null;
  }

  @Override
  public Integer getMin() {
    return null;
  }

  @Override
  public String getValidationQuery() {
    return null;
  }

  @Override
  public Boolean getDefaultAutoCommit() {
    return null;
  }
}
