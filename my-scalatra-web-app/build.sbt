val ScalatraVersion = "2.6.3"

organization := "com.example"

name := "My Scalatra Web App"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.6"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.9.v20180320" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.sangria-graphql" %% "sangria" % "1.4.2",
  "org.sangria-graphql" %% "sangria-json4s-native" % "1.0.0",
  "org.json4s" %% "json4s-native" % "3.5.4",
  "joda-time" % "joda-time" % "2.10",
  "org.scalaj" %% "scalaj-http" % "2.4.1",
  "org.json4s" %% "json4s-jackson" % "3.5.4"
)

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
