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
package com.github.illfaku.korro.internal.client.actor

import akka.actor.Actor
import com.typesafe.config.Config

import java.util.Collections.emptySet

/**
 * The main actor that starts all configured http clients as its child actors.
 *
 * @author Vladimir Konstantinov
 */
class KorroHttpClientActor(config: Config) extends Actor {

  config.findObject("korro.client").map(_.keySet).getOrElse(emptySet).asScala foreach { name =>
    HttpClientActor.create(new ClientConfig(name, config.getConfig(s"korro.client.$name")))
  }

  override def receive = Actor.emptyBehavior
}
