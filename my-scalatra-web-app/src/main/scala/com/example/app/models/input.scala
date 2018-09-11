package com.example.app.models

case class Post(
                 userId: Int,
                 id: Int,
                 title: String,
                 body: String
               ) //fixmemaybe id might break

case class Comment(
                    postId: Int,
                    id: Int,
                    name: String,
                    email: String,
                    body: String
                  ) //fixmemaybe id might break

case class Album(
                  userId: Int,
                  id: Int,
                  title: String
                ) //fixmemaybe id might break

case class Photo(
                  albumId: Int,
                  id: Int,
                  title: String,
                  url: String,
                  thumbnailUrl: String
                )

case class Todo(
                 userId: Int,
                 id: Int,
                 title: String,
                 completed: Boolean
               )

case class User(
                id: Int,
                name: String,
                username: String,
                email: String,
                address: Address,
                phone: String,
                website: String,
                company: Company
               )

case class Address(
                    street: String,
                    suite: String,
                    city: String,
                    zipcode: String,
                    geo: Geo
                  )

case class Geo(
                lat: String,
                lng: String
              )

case class Company(
                    name: String,
                    catchPhrase: String,
                    bs: String
                  )


case class UserOutput(
                       id: Int,
                       name: String,
                       username: String,
                       email: String,
                       address: Address,
                       phone: String,
                       website: String,
                       company: Company,
                       posts: List[Post],
                       albums: List[Album],
                       todos: List[Todo]
                     )

case class PostOutput