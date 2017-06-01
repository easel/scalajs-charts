package com.github.easel.sjscharts.morrisjs

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react._
import org.scalajs.dom

/**
  * Created by erik on 6/1/16.
  */
object ReactMorris {
  val Spinner = ScalaComponent.builder[Unit]("ReactMorris.Spinner")
    .render { $ ⇒
      <.div(
        ^.cls := "chart-loading-container text-center",
        <.i(^.cls := "fa fa-5x fa-spinner faa-spin animated")
      )
    }
    .build

  val Empty = ScalaComponent.builder[Unit]("ReactMorris.Empty")
    .render { $ ⇒
      <.div("No Data")
    }
    .build

  case class State(
    element:  Option[dom.Element]   = None,
    instance: Option[ChartInstance] = None
  )

  val Component = ScalaComponent.builder[Morris.ChartDefinition[_]]("ReactMorris")
    .initialState(State())
    .render { $ ⇒
      <.div()
    }
    .componentDidMount(ctx ⇒ {
      ctx.modState(_.copy(
        element  = Some(ctx.getDOMNode),
        instance = Some(ctx.props.withElement(element = ctx.getDOMNode).render())
      ))
    })
    .componentDidUpdate($ ⇒ (for {
      instance ← $.currentState.instance
      element ← $.currentState.element
    } yield Callback {
      // TODO: detect data-only changes and call instance.setData instead of completely blowing away the chart
      element.innerHTML = ""
      $.currentProps.withElement(element).render()
    }).getOrElse(Callback.log("Unable to find chart state")))
    .build
}
