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

import java.util.concurrent.TimeUnit;

/**
 * Hooks to be notified of events within a running hexane pool.
 *
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
interface HexaneListener {
  /** Called after a datasource has been created */
  default void onDataSourceCreation() {}

  /**
   * Called when a pooled item has successfully been added.
   *
   * @param elapsed The duration that creating the pooled connection took.
   * @param unit The unit of the {@code elapsed} parameter.
   */
  default void onConnectionCreation(final long elapsed, final TimeUnit unit) {}

  /**
   * Called when a caller trying to acquire a connection from the pool times out.
   *
   * @param elapsed The duration that the caller spent waiting before the timeout.
   * @param unit The unit of the {@code elapsed} parameter.
   */
  default void onConnectionTimeout(final long elapsed, final TimeUnit unit) {}

  /**
   * Called when a caller acquires a connection from the pool.
   *
   * @param elapsed The duration that the caller spent waiting before the item became available.
   * @param unit The unit of the {@code elapsed} parameter.
   */
  default void onConnectionAcquired(final long elapsed, final TimeUnit unit) {}

  /**
   * Called when a previously acquired connection is returned to the pool.
   *
   * @param elapsed The duration that the connection was leased from the pool.
   * @param unit The unit of the {@code elapsed} parameter.
   */
  default void onConnectionReturned(final long elapsed, final TimeUnit unit) {}

  /** Called when a broken connection is removed from the pool. */
  default void onConnectionErrorEviction() {}

  /** Called when an idle connection is removed from the pool. */
  default void onConnectionIdleEviction() {}

  /** Called when a connection that has exceeded its maximum lifespan is removed from the pool. */
  default void onConnectionLifetimeEviction() {}

  /** Called when a connection is closed due to the datasource closing. */
  default void onDataSourceClosedEviction() {}

  /** Called when the statement cache returns a statement from the cache. */
  default void onStatementCacheHit() {}

  /** Called when the statement cache returns a statement that was not in the cache. */
  default void onStatementCacheMiss() {}

  /** Called when a statement is closed. */
  default void onStatementClosedEviction() {}

  /** Called when a connection is closed due to an error. */
  default void onStatementErrorEviction() {}

  /** Called once the datasource has completed closing. */
  default void onDataSourceClosed() {}
}
