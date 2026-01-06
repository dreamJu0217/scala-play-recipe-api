name := """recipe-play-api-test"""
organization := "recipe.text"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.18"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.33"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.4.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.4.0"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "recipe.text.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "recipe.text.binders._"
