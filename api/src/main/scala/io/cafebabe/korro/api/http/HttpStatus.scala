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
package io.cafebabe.korro.api.http

import io.cafebabe.korro.api.http.HttpParams.HttpParams

/**
 * TODO: Add description.
 *
 * @author Vladimir Konstantinov
 */
object HttpStatus {
  object Ok extends HttpStatus(200)
  object BadRequest extends HttpStatus(400)
  object NotFound extends HttpStatus(404)
  object RequestTimeout extends HttpStatus(408)
  object ServerError extends HttpStatus(500)
}

/**
 * TODO: Add description.
 *
 * @author Vladimir Konstantinov
 */
sealed abstract class HttpStatus(val code: Int) {

  def apply(content: HttpContent = EmptyHttpContent, headers: HttpParams = Map.empty): HttpResponse = {
    HttpResponse(code, content, headers)
  }

  def unapply(res: HttpResponse): Option[(Any, HttpParams)] = {
    if (code == res.status) {
      val content = res.content match {
        case TextHttpContent(text, _) => text.toString
        case JsonHttpContent(json, _) => json
        case _ => res.content
      }
      Some(content, res.headers)
    } else None
  }
}
