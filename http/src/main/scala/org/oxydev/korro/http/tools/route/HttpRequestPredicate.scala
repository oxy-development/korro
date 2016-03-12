/*
 * Copyright (C) 2015, 2016  Vladimir Konstantinov, Yuriy Gintsyak
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
package org.oxydev.korro.http.tools.route

import org.oxydev.korro.http.api.HttpRequest
import org.oxydev.korro.util.lang.{Predicate, Predicate1}

/**
 * Predicates for [[HttpRouter]].
 */
object HttpRequestPredicate {

  def apply(test: HttpRequest => Boolean): Predicate1[HttpRequest] = Predicate(test)

  def MethodIs(method: String) = apply(_.method.name == method)
  def MethodIs(method: HttpRequest.Method) = apply(_.method == method)
  def PathIs(path: String) = apply(_.path == path)
  def PathStartsWith(prefix: String) = apply(_.path startsWith prefix)
  def HasHeader(name: String) = apply(_.headers.get(name).isDefined)
  def HasHeaderValue(name: String, value: String) = apply(_.headers.all(name).exists(_ equalsIgnoreCase value))
}