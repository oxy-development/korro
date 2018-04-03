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
package com.github.illfaku.korro.internal.server

import com.github.illfaku.korro.dto._
import com.github.illfaku.korro.internal.common.bytes2byteBuf

import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.{ChannelHandlerContext, DefaultFileRegion}
import io.netty.handler.codec.http.{DefaultHttpHeaders, DefaultHttpResponse}
import io.netty.handler.codec.{MessageToMessageEncoder, http => netty}

import java.io.File

@Sharable
private[server] object HttpResponseEncoder extends MessageToMessageEncoder[HttpResponse] {

  override def encode(ctx: ChannelHandlerContext, msg: HttpResponse, out: java.util.List[AnyRef]): Unit = {

    val headers = new DefaultHttpHeaders()
    msg.headers.entries foreach { case (name, value) => headers.add(name, value) }

    if (msg.content.contentLength > 0) {
      if (!headers.contains(netty.HttpHeaderNames.CONTENT_LENGTH)) {
        headers.set(netty.HttpHeaderNames.CONTENT_LENGTH, msg.content.contentLength)
      }
      msg.content.contentType foreach { t =>
        if (!headers.contains(netty.HttpHeaderNames.CONTENT_TYPE)) {
          headers.add(netty.HttpHeaderNames.CONTENT_TYPE, t.toString)
        }
      }
    }

    out add new DefaultHttpResponse(
      netty.HttpVersion.valueOf(msg.version.toString),
      netty.HttpResponseStatus.valueOf(msg.status.code, msg.status.reason),
      headers
    )

    msg.content match {

      case BytesHttpContent(bytes, _) if bytes.nonEmpty =>
        out add new netty.DefaultLastHttpContent(bytes)

      case FileHttpContent(path, size, _) if size > 0 =>
        out add new DefaultFileRegion(new File(path), 0, size)
        out add netty.LastHttpContent.EMPTY_LAST_CONTENT

      case _ =>
        out add netty.LastHttpContent.EMPTY_LAST_CONTENT
    }
  }
}