package com.example.app.http
import scalaj.http.{Http, HttpOptions}

class HttpClient() {
  val url = "https://jsonplaceholder.typicode.com/"

  private
  def getResponse(url: String): String = {
  Http(url).option(HttpOptions.followRedirects(true)).asString.throwError.throwServerError.body
  }
  def getAll(contentType:String) = {
    getResponse(url + contentType)
  }

  def getOne(contentType:String, id:String) = {
    getResponse(url + s"${contentType}/${id}")
  }
}

object HttpClient {
  def apply() = new HttpClient()
}
