package com.azimo

case class Translation(key: String, value: String)

trait Translate[T] {
  def translate(toTranslate: T, translations: List[Translation])
}

object Translate {
  def apply[T](implicit translate: Translate[T]): Translate[T] = translate
}