package com.azimo

import org.scalatest.{FlatSpec, Matchers}

class TranslationSpec extends FlatSpec with Matchers {

  "Translations" should "work for simple case" in {
    val toTranslate = Response("translation.key.1", "translation.key.2")
    val translations = List(
      Translation("translation.key.1", "Translation 1"),
      Translation("translation.key.2", "Translation 2")
    )
    val translator = Translate[Response]
    val result = translator.translate(toTranslate, translations)

    result should equal (Response("Translation 1", "Translation 2"))
  }

}
