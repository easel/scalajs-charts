package com.github.easel.sjscharts.morrisjs

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.{JSON, UndefOr, |}

/**
  * Created by erik on 1/7/16.
  */
//scalastyle:off
@js.native
@JSName("Morris")
object MorrisStatic extends js.Object {
  def Donut(params: DonutParams): ChartInstance = js.native

  def Bar(params: BarParams): ChartInstance = js.native
}

@js.native
trait ChartInstance extends js.Object {
  val raphael: js.Dynamic = js.native

  def redraw(): Unit = js.native

  def setData(data: js.Dynamic, redraw: UndefOr[Boolean] = js.undefined): Unit = js.native
}

@js.native
trait LabelValue extends js.Object {
  val label: String
  val value: Double
}

@js.native
trait Params extends js.Object {
  val element: dom.Element | String = js.native
  val resize: Boolean = js.native

}

@js.native
trait DonutParams extends Params {
  val data: Array[LabelValue] = js.native
  val colors: js.UndefOr[Array[String]] = js.native
  val backgroundColor: js.UndefOr[String] = js.native
  val labelColor: js.UndefOr[String] = js.native
}

@js.native
trait BarParams extends Params {
  val data: Array[js.Object] = js.native
  val xkey: String = js.native
  val ykeys: Array[String] = js.native
  val labels: Array[String] = js.native
  val barColors: js.UndefOr[Array[String]] = js.native
  val stacked: Boolean = js.native
  val hideHover: String | Boolean = js.native

  def hoverCallback(index: Int, options: js.Dynamic, original: String, row: js.Dynamic): String = js.native

  val axes: Boolean = js.native
  val grid: Boolean = js.native
  val gridTextColor: String = js.native
  val gridTextSize: String = js.native
  val gridTextFamily: String = js.native
  val gridTextWeight: String = js.native
}

object Morris {

  trait ChartDefinition[T <: Params] {
    def build: T

    def element: dom.Element

    def render(): ChartInstance

    def withElement(element: dom.Element): ChartDefinition[T]

    def chartData: js.Dynamic = js.Dynamic.literal()
  }

  case class Donut(
      data:      Map[String, Double],
      hideHover: Boolean             = false,
      colors:    Seq[String]         = Seq.empty,
      resize:    Boolean             = false,
      element:   dom.Element         = dom.document.body
  ) extends ChartDefinition[DonutParams] {
    def withElement(element: dom.Element) = this.copy(element = element)

    override def chartData: js.Dynamic = data.map {
      case (k, v) ⇒
        js.Dynamic.literal(label = k, value = v)
    }.toJSArray.asInstanceOf[js.Dynamic]

    def build: DonutParams = {
      val chartParams = js.Dynamic.literal(
        element = element,
        data    = chartData,
        resize  = resize,
        colors  = if (colors.isEmpty) () else UndefOr.any2undefOrA(colors.toJSArray)
      ).asInstanceOf[DonutParams]
      chartParams
    }

    def render() = MorrisStatic.Donut(build)
  }

  case class Bar(
      data:          Seq[(String, Seq[Option[Double]])],
      labels:        Seq[String],
      stacked:       Boolean                                                = false,
      hideHover:     Boolean                                                = false,
      barColors:     Seq[String]                                            = Seq.empty,
      resize:        Boolean                                                = false,
      element:       dom.Element                                            = dom.document.body,
      hoverCallback: Option[(Int, js.Dynamic, String, js.Dynamic) ⇒ String] = None

  ) extends ChartDefinition[BarParams] {
    def withElement(element: dom.Element) = this.copy(element = element)

    def build: BarParams = {
      val jsData = data.map {
        case (x, y) ⇒
          val datum = js.Dynamic.literal("x" → x)
          y.indices.foreach { i ⇒
            val yAttr = s"y$i"
            datum.updateDynamic(yAttr)(y(i).orUndefined)
            yAttr
          }
          datum
      }.toJSArray
      val yKeys = data.headOption.map(_._2.indices.map(i ⇒ s"y$i")).getOrElse(Seq.empty).toJSArray
      val params = js.Dynamic.literal(
        element       = element,
        data          = jsData,
        labels        = labels.toJSArray,
        xkey          = "x",
        ykeys         = yKeys,
        stacked       = stacked,
        hideHover     = hideHover,
        resize        = resize,
        barColors     = if (barColors.isEmpty) () else UndefOr.any2undefOrA(barColors.toJSArray),
        hoverCallback = hoverCallback.orUndefined
      ).asInstanceOf[BarParams]
      params
    }

    def render() = MorrisStatic.Bar(build)
  }

}
