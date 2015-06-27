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
package io.cafebabe.http.server.impl.convert

import io.cafebabe.http.server.api.exception.BadRequestException
import io.cafebabe.http.server.api.{EmptyHttpContent, HttpContent, JsonHttpContent, TextHttpContent}
import io.cafebabe.http.server.impl.util.ByteBufUtils._
import io.cafebabe.http.server.impl.util.{StringUtils, ContentType}
import io.cafebabe.http.server.impl.util.MimeTypes.{ApplicationJson, TextPlain}
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.http.{DefaultHttpHeaders, HttpHeaders, FullHttpRequest}
import io.netty.handler.codec.http.HttpHeaders.Names._
import io.netty.util.CharsetUtil
import org.json4s.ParserUtil.ParseException
import org.json4s.native.JsonParser._

import java.nio.charset.Charset

/**
 * TODO: Add description.
 *
 * @author Vladimir Konstantinov
 */
object HttpContentConverter {

  def fromNetty(request: FullHttpRequest): HttpContent = {
    if (contentLength(request) > 0) {
      contentType(request) match {
        case (TextPlain, charset) => TextHttpContent(request.content.toString(charset))
        case (ApplicationJson, charset) =>
          try JsonHttpContent(parse(request.content.toString(charset))) catch {
            case e: ParseException => throw new BadRequestException(s"Fail to parse json content: ${e.getMessage}")
          }
        case (mime, _) => throw new BadRequestException(s"Unsupported Content-Type: $mime.")
      }
    } else EmptyHttpContent
  }

  def toNetty(content: HttpContent): (ByteBuf, HttpHeaders) = {
    val headers = new DefaultHttpHeaders
    val buf = content match {
      case TextHttpContent(text) =>
        headers.add(CONTENT_TYPE, ContentType(TextPlain))
        toByteBuf(text)
      case JsonHttpContent(json) =>
        headers.add(CONTENT_TYPE, ContentType(ApplicationJson))
        toByteBuf(StringUtils.toString(json))
      case EmptyHttpContent => emptyByteBuf
    }
    headers.add(CONTENT_LENGTH, buf.readableBytes)
    buf -> headers
  }

  private def contentLength(request: FullHttpRequest): Int = {
    val header = request.headers.get(CONTENT_LENGTH)
    if (header != null) {
      try header.toInt catch {
        case e: NumberFormatException => 0
      }
    } else 0
  }

  private def contentType(request: FullHttpRequest): (String, Charset) = {
    request.headers.get(CONTENT_TYPE) match {
      case ContentType(mime, charset) =>
        try mime -> charset.map(Charset.forName).getOrElse(CharsetUtil.UTF_8) catch {
          case e: IllegalArgumentException => throw new BadRequestException(s"Unsupported charset: $charset.")
        }
      case _ => TextPlain -> CharsetUtil.UTF_8
    }
  }
}
