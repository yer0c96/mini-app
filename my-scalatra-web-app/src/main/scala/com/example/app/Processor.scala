package com.example.app

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import com.example.app.http.HttpClient
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization._
import com.example.app.models._
import org.json4s.jackson.Serialization

import scala.util.{Try, Success, Failure}

// todo create enum for contentType

object Processor {
  implicit val formats = Serialization.formats(NoTypeHints) + new NumberSerializer()
  def getItems[T](contentType: String)(implicit m: Manifest[T]): List[T] = {
    val rawJSON = HttpClient().getAll(contentType)
    val myObj = parse(rawJSON).asInstanceOf[JArray].camelizeKeys
    myObj.children.filter(item => {
      val jsonObject = Try(item.extract[T])
      jsonObject match{
        case Success(obj) => true
        case Failure(e) =>false
      }
    }).map(_.extract[T])
  }

  def getItem[T](contentType: String, id: Int)(implicit  m: Manifest[T]): T = {
    val rawJSON = HttpClient().getOne(contentType, id.toString())
    val myObj = parse(rawJSON).asInstanceOf[JObject].camelizeKeys
    myObj.extract[T]
  }
}


class NumberSerializer extends CustomSerializer[Int](format => (
  {
    case JInt(x) => Try(x.toInt).getOrElse(0)
    case JDouble(x) => Try(x.toInt).getOrElse(0)
    case JString(x) => Try(x.toInt).getOrElse(0)
  },
  {
    case x: Int => JInt(x)
  }
))