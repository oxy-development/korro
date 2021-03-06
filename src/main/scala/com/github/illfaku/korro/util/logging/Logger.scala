/*
 * Copyright 2018 Vladimir Konstantinov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.illfaku.korro.util.logging

import akka.event.LoggingAdapter
import org.slf4j.LoggerFactory.getLogger

/**
 * Logger factory.
 */
object Logger {

  type Logger = LoggingAdapter with TraceLoggingAdapter

  def apply(clazz: Class[_]): Logger = new Slf4jLoggingAdapter(getLogger(clazz))
  def apply(name: String): Logger = new Slf4jLoggingAdapter(getLogger(name))
}
