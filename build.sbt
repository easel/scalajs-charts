
lazy val root = project.in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
	  libraryDependencies ++= Dependencies.ScalaJsDom.value ++ Dependencies.ScalaJsReact.value,
    scalaVersion := "2.11.8",
    organization := "com.github.easel",
    name := "scalajs-charts",
    version := "0.1",
    //version := "0.1-SNAPSHOT",
    //isSnapshot := true,
    publishMavenStyle := true,
    licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)
