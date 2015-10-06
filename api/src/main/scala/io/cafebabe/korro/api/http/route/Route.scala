/*
 * Copyright (C) 2015  Vladimir Konstantinov, Yuriy Gintsyak
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.cafebabe.korro.api.http.route

import scala.concurrent.duration.FiniteDuration

/**
 * TODO: Add description.
 *
 * @author Vladimir Konstantinov
 */
sealed trait Route {
  def path: String
  def actor: String
}

/**
 * TODO: Add description.
 *
 * @author Vladimir Konstantinov
 */
case class HttpRoute(
  path: String,
  requestTimeout: FiniteDuration,
  actor: String
) extends Route

/**
 * TODO: Add description.
 *
 * @author Vladimir Konstantinov
 */
case class WsRoute(
  path: String,
  maxFramePayloadLength: Int,
  compression: Boolean,
  actor: String
) extends Route