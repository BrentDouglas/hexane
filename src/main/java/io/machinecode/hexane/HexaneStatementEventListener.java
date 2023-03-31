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

import javax.sql.StatementEvent;
import javax.sql.StatementEventListener;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
class HexaneStatementEventListener<C> implements StatementEventListener {
  final BasePool<C> pool;
  final Pooled<C> item;

  HexaneStatementEventListener(final BasePool<C> pool, final Pooled<C> item) {
    this.pool = pool;
    this.item = item;
  }

  @Override
  public void statementClosed(final StatementEvent event) {
    if (item.getCache().remove(event.getStatement())) {
      pool.getConfig().getListener().onStatementClosedEviction();
    }
  }

  @Override
  public void statementErrorOccurred(final StatementEvent event) {
    if (item.getCache().remove(event.getStatement())) {
      pool.getConfig().getListener().onStatementErrorEviction();
    }
  }
}
