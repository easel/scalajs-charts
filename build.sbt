
lazy val root = project.in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
	  libraryDependencies ++= Dependencies.ScalaJsDom.value ++ Dependencies.ScalaJsReact.value,
    scalaVersion := "2.11.12",
    crossScalaVersions := Seq("2.11.12", "2.12.6"),
    organization := "com.github.easel",
    name := "scalajs-charts",
    version := "0.6-SNAPSHOT",
    isSnapshot := version.value.contains("SNAPSHOT"),
    publishMavenStyle := true,
    licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)
