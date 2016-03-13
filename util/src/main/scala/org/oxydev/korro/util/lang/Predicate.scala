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
package org.oxydev.korro.util.lang

object Predicate {

  def apply[A](test: A => Boolean): Predicate1[A] = new Predicate1[A] {
    override def apply(a: A): Boolean = test(a)
  }
}

trait Predicate1[A] extends (A => Boolean) { self =>

  def &&(other: Predicate1[A]): Predicate1[A] = new Predicate1[A] {
    override def apply(a: A): Boolean = self(a) && other(a)
  }

  def ||(other: Predicate1[A]): Predicate1[A] = new Predicate1[A] {
    override def apply(a: A): Boolean = self(a) || other(a)
  }

  def unary_! : Predicate1[A] = new Predicate1[A] {
    override def apply(a: A): Boolean = !self(a)
  }
}
