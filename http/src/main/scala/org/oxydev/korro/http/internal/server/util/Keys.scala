/*
 * Copyright 2016 Vladimir Konstantinov, Yuriy Gintsyak
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
package org.oxydev.korro.http.internal.server.util

import org.oxydev.korro.http.internal.server.config.ServerConfig

import akka.actor.ActorRef
import io.netty.util.AttributeKey

object Keys {

  val config = AttributeKey.valueOf[ServerConfig]("server-config")

  object Req {
    val router = AttributeKey.valueOf[HttpRequestRouter]("req-router")
    val parent = AttributeKey.valueOf[ActorRef]("req-parent")
  }

  object Ws {
    // TODO: add WS router and parent
  }
}