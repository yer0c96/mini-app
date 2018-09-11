package com.example.app

import com.example.app.models._
import sangria.macros.derive.{ObjectTypeDescription, ObjectTypeName, deriveObjectType}
import sangria.schema.{Argument, Field, IntType, ListType, ObjectType, OptionInputType, Schema, fields}

object JsonPlaceHolderSchema {
  val userArg: Argument[Int] = Argument("id", OptionInputType(IntType),
    description = "Get user details",
    defaultValue = 1)

  implicit val PostType: ObjectType[Data, Post] = {
    deriveObjectType[Data, Post](
      ObjectTypeName("Post"),
      ObjectTypeDescription("A post by a user")
    )
  }
  implicit val CommentType: ObjectType[Data, Comment] = {
    deriveObjectType[Data, Comment](
      ObjectTypeName("Comment"),
      ObjectTypeDescription("A user's comment on a post")
    )
  }
  implicit val AlbumType: ObjectType[Data, Album] = {
    deriveObjectType[Data, Album](
      ObjectTypeName("Album"),
      ObjectTypeDescription("A user's album")
    )
  }
  implicit val PhotoType: ObjectType[Data, Photo] = {
    deriveObjectType[Data, Photo](
      ObjectTypeName("Photo"),
      ObjectTypeDescription("A Photo belonging to an album")
    )
  }
  implicit val ToDoType: ObjectType[Data, Todo] = {
    deriveObjectType[Data, Todo](
      ObjectTypeName("Todo"),
      ObjectTypeDescription("A todo belonging to a user")
    )
  }
  implicit val UserType: ObjectType[Data, User] = {
    deriveObjectType[Data, User](
      ObjectTypeName("User"),
      ObjectTypeDescription("A user")
    )
  }
  implicit val UserOutputType: ObjectType[Data, UserOutput] = {
    deriveObjectType[Data, UserOutput](
      ObjectTypeName("UserOutPut"),
      ObjectTypeDescription("UerOutOut")
    )
  }
  implicit val AddressType: ObjectType[Data, Address] = {
    deriveObjectType[Data, Address](
      ObjectTypeName("Address"),
      ObjectTypeDescription("An address belonging to a user")
    )
  }
  implicit val GeoType: ObjectType[Data, Geo] = {
    deriveObjectType[Data, Geo](
      ObjectTypeName("Geo"),
      ObjectTypeDescription("the geo belonging to an address")
    )
  }
  implicit val CompanyType: ObjectType[Data, Company] = {
    deriveObjectType[Data, Company](
      ObjectTypeName("Company"),
      ObjectTypeDescription("A Company")
    )
  }

  private val Query:ObjectType[Data, Unit] = ObjectType[Data, Unit](
    "Query", fields[Data, Unit](
      Field(
        "Users",
        ListType(UserType),
        description = Some("Get the users"),
        arguments = Nil,
        resolve = c => c.ctx.JsonPlaceHolderRepo.getUsers()
      ))++fields[Data, Unit](
      Field(
        "Posts",
        ListType(PostType),
        description = Some("Gets a list of posts"),
        arguments = Nil,
        resolve = c => c.ctx.JsonPlaceHolderRepo.getPosts()
      ))++fields[Data, Unit](
      Field(
        "Albums",
        ListType(AlbumType),
        description = Some("Get list of albums"),
        arguments = Nil,
        resolve = c => c.ctx.JsonPlaceHolderRepo.getAlbums()
      ))++fields[Data,Unit](
      Field(
        "UserDetails",
        UserOutputType,
        description = Some("Get all things associated with a user"),
        arguments = userArg :: Nil,
        resolve = c => c.ctx.JsonPlaceHolderRepo.getUserDetails(c.arg(userArg))
      )
    ))
  val theSchema = Schema(Query)
}
