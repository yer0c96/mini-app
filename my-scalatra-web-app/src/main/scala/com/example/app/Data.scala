package com.example.app

import com.example.app.models._
import com.example.app.Processor._

class Data {

  class JsonPlaceHolderRepo{
    //private
    //def urlToInt  = (url: String)=> {url.split("/").last.toInt}
    def getUsers() ={
      getItems[User]("users")
    }

    def getAlbums() ={
      getItems[Album]("albums")
    }

    def getPosts() ={
      getItems[Post]("posts")
    }

    def getToDos() ={
      getItems[Todo]("todos")
    }

    def getUserDetails(id:Int) ={
      val user = getItem[User]("users", id)
      lazy val posts:List[Post] = getPosts().filter(_.userId == user.id)
      lazy val albums:List[Album] = getAlbums().filter(_.userId == user.id)
      lazy val todos:List[Todo] = getToDos().filter(_.userId == user.id)

      UserOutput(user.id, user.name, user.username, user.email, user.address, user.phone, user.website, user.company,
      posts, albums, todos)
    }
  }
  val JsonPlaceHolderRepo = new JsonPlaceHolderRepo
}
