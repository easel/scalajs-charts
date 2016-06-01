
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
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
)
