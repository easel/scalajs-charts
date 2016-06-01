import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

object Dependencies {
  object Versions {
    val MorrisJs = "0.5.1"
    val ScalaJsDom = "0.8.2"
    val ScalaJsReact = "0.10.5-SNAPSHOT"
  }

  val MorrisJs = Def.setting(Seq(
    "org.webjars" % "morrisjs" % Versions.MorrisJs
  ))

  val ScalaJsDom = Def.setting(Seq(
    "org.scala-js" %%% "scalajs-dom" % Versions.ScalaJsDom
  ))

  val ScalaJsReact = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % Versions.ScalaJsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % Versions.ScalaJsReact,
    "com.github.japgolly.scalajs-react" %%% "test" % Versions.ScalaJsReact
  ))
}
