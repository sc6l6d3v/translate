package com.azimo

import shapeless.{Generic, HList, HNil}


case class Translation(key: String, value: String)

trait Translate[T] {
  def translate(toTranslate: T, translations: List[Translation])
}

object Translate {
  def apply[T](implicit translate: Translate[T]): Translate[T] = translate

  implicit def translateProduct[Product, H <: HList](
                                                      implicit generic: Generic.Aux[Product, H],
                                                      hTranslate: Translate[H]
                                                    ): Translate[Product] = (toTranslate: Product, translations: List[Translation]) => {
    generic.from(hTranslate.translate(generic.to(toTranslate), translations))
  }

  implicit def hnil = new Translate[HNil] {
    override def translate(toTranslate: HNil, translations: List[Translation]): HNil = HNil
  }

  implicit def hcons[Head, Tail <: HList](
                                           implicit head: Translate[Head],
                                           tail: Translate[Tail]
                                         ): Translate[Head :: Tail] = (toTranslate: Head :: Tail, translations: List[Translation]) => {
    head.translate(toTranslate.head, translations) :: tail.translate(toTranslate.tail, translations)
  }


  implicit def translateString: Translate[String] = (toTranslate: String, translations: List[Translation]) => {
    translations.find(_.key == toTranslate).map(_.value).getOrElse(toTranslate)
  }

}