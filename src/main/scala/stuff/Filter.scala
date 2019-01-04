/**
  * Copyright Homeaway, Inc 2016-Present. All Rights Reserved.
  * No unauthorized use of this software.
  */
package stuff

import java.awt.image.{BufferedImage, BufferedImageOp}

import cats.effect.IO
import cats.implicits._

import scala.Console.{GREEN, RESET}

object Filter {

  private def applyFilter(image: BufferedImage, filter: BufferedImageOp): IO[BufferedImage] =
    IO(println(s"${GREEN}Applying filter $filter$RESET")) *>
      IO(filter.filter(image, new BufferedImage(image.getWidth, image.getHeight(), image.getType)))

  def filter[T <: BufferedImageOp](op: T)(filter: T ⇒ Unit)(i: BufferedImage): IO[BufferedImage] = {
    filter(op)
    applyFilter(i, op)
  }

  def gilter[T <: BufferedImageOp](op: T)(i: BufferedImage): IO[BufferedImage] = applyFilter(i, op)

  type Filter = BufferedImage ⇒ IO[BufferedImage]

}
