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
package org.oxydev.korro.http

import org.oxydev.korro.http.internal.server.actor.KorroHttpServerActor

import akka.actor.Props
import com.typesafe.config.Config

/**
 * Props provider for the main actor that starts all configured http servers as its child actors.
 */
object KorroHttpServers {

  /**
   * Creates props with which you can start servers' parent actor by your name using your ActorRefFactory.
   * @param config Configuration of all HTTP servers that need to be started.
   * @return Props for actor creation.
   */
  def props(config: Config): Props = Props(new KorroHttpServerActor(config))
}
