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

import java.util.logging.Level;

/** @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a> */
abstract class LoggerFactory {

  abstract Logger getLogger(final Class<?> clazz);

  private LoggerFactory() {}

  static final Logger NOOP_LOGGER =
      new Logger() {
        @Override
        public void info(final String msg) {}

        @Override
        public void warn(final String msg, final Throwable e) {}

        @Override
        public void error(final String msg, final Throwable e) {}
      };

  static final LoggerFactory NONE =
      new LoggerFactory() {
        @Override
        Logger getLogger(final Class<?> clazz) {
          return NOOP_LOGGER;
        }
      };

  static final LoggerFactory JUL =
      new LoggerFactory() {
        @Override
        Logger getLogger(final Class<?> clazz) {
          return new Logger() {
            final java.util.logging.Logger LOG =
                java.util.logging.Logger.getLogger(clazz.getName());

            @Override
            public void info(final String msg) {
              LOG.log(Level.INFO, msg);
            }

            @Override
            public void warn(final String msg, final Throwable e) {
              LOG.log(Level.WARNING, msg, e);
            }

            @Override
            public void error(final String msg, final Throwable e) {
              LOG.log(Level.SEVERE, msg, e);
            }
          };
        }
      };

  static final LoggerFactory LOG4J =
      new LoggerFactory() {
        @Override
        Logger getLogger(final Class<?> clazz) {
          return new Logger() {
            final org.apache.logging.log4j.Logger LOG =
                org.apache.logging.log4j.LogManager.getLogger(clazz.getName());

            @Override
            public void info(final String msg) {
              LOG.info(msg);
            }

            @Override
            public void warn(final String msg, final Throwable e) {
              LOG.warn(msg, e);
            }

            @Override
            public void error(final String msg, final Throwable e) {
              LOG.error(msg, e);
            }
          };
        }
      };

  static final LoggerFactory SLF4J =
      new LoggerFactory() {
        @Override
        Logger getLogger(final Class<?> clazz) {
          return new Logger() {
            final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(clazz.getName());

            @Override
            public void info(final String msg) {
              LOG.info(msg);
            }

            @Override
            public void warn(final String msg, final Throwable e) {
              LOG.warn(msg, e);
            }

            @Override
            public void error(final String msg, final Throwable e) {
              LOG.error(msg, e);
            }
          };
        }
      };

  static final LoggerFactory JBOSS_LOGGING =
      new LoggerFactory() {
        @Override
        Logger getLogger(final Class<?> clazz) {
          return new Logger() {
            final org.jboss.logging.Logger LOG =
                org.jboss.logging.Logger.getLogger(clazz.getName());

            @Override
            public void info(final String msg) {
              LOG.info(msg);
            }

            @Override
            public void warn(final String msg, final Throwable e) {
              LOG.warn(msg, e);
            }

            @Override
            public void error(final String msg, final Throwable e) {
              LOG.error(msg, e);
            }
          };
        }
      };
}
