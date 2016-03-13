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
package org.oxydev.korro.util.i18n

import java.util.Locale

/**
 * Locale utilities.
 *
 * @author Vladimir Konstantinov
 */
object Locales {

  private val Pattern = """^([a-zA-Z]{1,8})(?:[_-]([a-zA-Z]{1,8}))?""".r.unanchored

  /**
   * Parses locale string to produce instance of [[java.util.Locale Locale]].
   * <p>Locale string should match the following pattern:
   * {{{
   *   ^([a-zA-Z]{1,8})(?:[_-]([a-zA-Z]{1,8}))?
   * }}}
   *
   * @param locale String representation of locale.
   * @return New instance of Locale or default Locale if string is null or is not matched by any pattern.
   */
  def parse(locale: String): Locale = locale match {
    case Pattern(language, country) => new Locale(language, Option(country).getOrElse(""))
    case _ => Locale.getDefault
  }
}
