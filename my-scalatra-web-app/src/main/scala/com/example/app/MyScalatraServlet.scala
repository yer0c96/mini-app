package com.example.app

import org.scalatra._
import sangria.ast.Document
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError, _}
import sangria.macros.derive.{ObjectTypeDescription, ObjectTypeName, deriveObjectType}
import sangria.parser.{QueryParser, SyntaxError}
import sangria.renderer.SchemaRenderer
import sangria.schema.{Argument, Field, IntType, ListType, ObjectType, OptionInputType, Schema, fields}

import scala.concurrent.ExecutionContext.Implicits.global
import org.json4s.{DefaultFormats, Formats}
import org.json4s._
import sangria.marshalling.json4s.native._
import org.json4s.jackson.JsonMethods._
import org.scalatra.{ActionResult, BadRequest, InternalServerError, Ok}
import sangria.marshalling.json4s.native._
import sangria.parser.{QueryParser, SyntaxError}

import scala.concurrent.duration.{Duration, SECONDS}
import scala.concurrent.{Await, Future}
import scala.concurrent.Future
import scala.util.{Failure, Success}
import org.json4s.JValue

class MyScalatraServlet extends ScalatraServlet {

  implicit val formats: Formats = DefaultFormats

  get("/graphiql") {
    redirect(s"$contextPath/graphiql.html")
  }
  post("/graphql"){
    val json = parse(request.body)
    val query = (json \ "query").extract[String]
    val operation = (json \ "operationName").extractOpt[String]
    val variables = (json \ "variables").toOption.flatMap {
      case JString(vars) ⇒ Some(parseVariables(vars))
      case obj: JObject ⇒ Some(obj)
      case _ ⇒ None
    }
    val f = QueryParser.parse(query) match {
      case Success(q) => executeQuery(q, variables, operation)
      case Failure(error: SyntaxError) => Future.successful("error" -> error.getMessage)
      case Failure(error: Throwable) => Future.successful("error" -> error.getMessage)
    }
    Await.result(f, Duration(30, SECONDS))
  }

  private def executeQuery(queryAst: Document, vars: Option[JObject], operation: Option[String])= {
    val schema: Schema[Data, Unit] = JsonPlaceHolderSchema.theSchema
    Executor.execute(schema, queryAst,
      userContext = new Data,
      operationName = operation,
      variables=vars.getOrElse(JObject()))
      .map(d => pretty(render(d)) )
      .recover {
        case error: QueryAnalysisError ⇒ BadRequest(error.resolveError)
        case error: ErrorWithResolver ⇒ InternalServerError(error.resolveError)
      }
  }

  private def parseVariables(variables: String) =
    if (variables.trim == "" || variables.trim == "null") JObject() else parse(variables).extract[JObject]



}
