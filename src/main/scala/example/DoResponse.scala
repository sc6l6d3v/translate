package example

import com.azimo.{Response, Translation}

object DoResponse extends App {
  val response = Response("translation.key.1", "translation.key.2")
  val translations = List(
    Translation("translation.key.1", "Translation 1"),
    Translation("translation.key.2", "Translation 2")
  )
  response.copy(
    text = translations.find(_.key == response.text).map(_.value).getOrElse(response.text),
    label = translations.find(_.key == response.label).map(_.value).getOrElse(response.label)
  )

  println(response)
}
