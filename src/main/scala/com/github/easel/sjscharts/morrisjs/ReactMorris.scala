package com.github.easel.sjscharts.morrisjs

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB, ReactDOM}
import org.scalajs.dom

/**
  * Created by erik on 6/1/16.
  */
object ReactMorris {
  val Spinner = ReactComponentB[Unit]("ReactMorris.Spinner")
    .render { $ ⇒
      <.div(
        ^.cls := "chart-loading-container text-center",
        <.i(^.cls := "fa fa-5x fa-spinner faa-spin animated")
      )
    }
    .buildU

  val Empty = ReactComponentB[Unit]("ReactMorris.Empty")
    .render { $ ⇒
      <.div("No Data")
    }
    .buildU

  case class State(
    element:  Option[dom.Element]   = None,
    instance: Option[ChartInstance] = None
  )

  val Component = ReactComponentB[Morris.ChartDefinition[_]]("ReactMorris")
    .initialState(State())
    .render { $ ⇒
      <.div()
    }
    .componentDidMount($ ⇒ {
      val element = ReactDOM.findDOMNode($)
      $.modState(_.copy(
        element  = Some(element),
        instance = Some($.props.withElement(element = element).render())
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
