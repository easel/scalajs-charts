scalaVersion := "2.11.8"

lazy val root = project.in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(libraryDependencies ++= Dependencies.ScalaJsDom.value ++ Dependencies.ScalaJsReact.value)
