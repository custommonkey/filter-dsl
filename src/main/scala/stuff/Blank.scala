package stuff

import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB

import cats.effect.IO
import stuff.Api._

case class Blank(width: Int, height: Int, colour: Colour) extends Source {

  override def apply(v1: Unit): IO[BufferedImage] =
    IO {
      val i = new BufferedImage(width, height, TYPE_INT_ARGB)
      val g = i.createGraphics()
      g.setColor(colour)
      g.fillRect(0, 0, width, height)
      i
    }

}
