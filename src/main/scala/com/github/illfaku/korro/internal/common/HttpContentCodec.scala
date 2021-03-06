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
package com.github.illfaku.korro.internal.common

import com.github.illfaku.korro.dto.{BytesHttpContent, ContentType, FileHttpContent, HttpContent}

import io.netty.buffer.ByteBuf
import io.netty.channel.DefaultFileRegion
import io.netty.handler.codec.http.{DefaultLastHttpContent, HttpHeaderNames, HttpHeaders, LastHttpContent}

import java.io.File

private[internal] object HttpContentCodec {

  def encode(content: HttpContent): List[AnyRef] = content match {

    case BytesHttpContent(bytes, _) if bytes.nonEmpty =>
      List(new DefaultLastHttpContent(bytes))

    case FileHttpContent(path, size, _) if size > 0 =>
      List(new DefaultFileRegion(new File(path), 0, size), LastHttpContent.EMPTY_LAST_CONTENT)

    case _ =>
      List(LastHttpContent.EMPTY_LAST_CONTENT)
  }

  def decode(content: ByteBuf, headers: HttpHeaders): HttpContent = {
    HttpContent.Bytes(content, Option(headers.get(HttpHeaderNames.CONTENT_TYPE)).flatMap(ContentType.parse))
  }
}
