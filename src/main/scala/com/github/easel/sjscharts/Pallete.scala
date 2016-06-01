package com.github.easel.sjscharts

/**
  * Created by erik on 3/17/16.
  */
case class Color(rgb: String) extends AnyVal

abstract class Pallete {
  val primary: Seq[Color]
  val secondary: Seq[Color]
  val tertiary: Seq[Color]
  val complement: Seq[Color]

  private lazy val all = Seq(primary, secondary, tertiary, complement)

  lazy val mixedScheme: Seq[Color] = {
    var inputs = all
    var output = Seq.empty[Color]
    while (inputs.nonEmpty) {
      inputs.headOption match {
        case None ⇒
          "pass"
        case Some(topList) ⇒
          topList.headOption match {
            case None ⇒
              inputs = inputs.tail
            case Some(topItem) ⇒
              inputs = inputs.tail :+ topList.tail
              output :+= topItem
          }
      }
    }
    output
  }

  lazy val first: Seq[Color] = all.flatMap(_.headOption)

  lazy val middle: Seq[Color] = all.map { x ⇒ x(x.length / 2) }

  lazy val last: Seq[Color] = all.flatMap(_.reverse.headOption)

}

object Pallete {

  implicit class RichColorString(s: String) {
    def toScheme: Seq[Color] = s.lines.map(_.trim).filter(!_.isEmpty).map(Color).toSeq
  }

  object SeventhSense extends Pallete {
    override val primary =
      """
        | #F68C20
        | #FFBB75
        | #FFA64C
        | #CB6A06
        | #A15100
      """.stripMargin.toScheme

    override val secondary =
      """
        | #F6B620
        | #FFD675
        | #FFC94C
        | #CB9006
        | #A17000
      """.stripMargin.toScheme

    override val tertiary =
      """
        | #2740A7
        | #687AC6
        | #4459B1
        | #142C8A
        | #0C1F6D
      """.stripMargin.toScheme

    override val complement =
      """
        | #187D99
        | #59A6BC
        | #348AA3
        | #08647E
        | #034F64
      """.stripMargin.toScheme
  }

  object RGB extends Pallete {
    override val primary =
      """
        | #ff0000
        | #00ff00
        | #0000ff
      """.stripMargin.toScheme

    override val secondary =
      """
        | #ff0000
        | #00ff00
        | #0000ff
      """.stripMargin.toScheme

    override val tertiary =
      """
        | #ff0000
        | #00ff00
        | #0000ff
      """.stripMargin.toScheme

    override val complement =
      """
        | #ff0000
        | #00ff00
        | #0000ff
      """.stripMargin.toScheme
  }

}
