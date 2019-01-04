package stuff

import java.awt.image.BufferedImage
import java.io.IOException

import better.files.File
import cats.Eval
import cats.effect.IO
import cats.implicits._
import javax.imageio.ImageIO

object Api {

  case class ReadError(file: File, e: IOException)
      extends Throwable(s"Error loading $file, ${e.getMessage}")

  def read(file: File): IO[BufferedImage] =
    IO.eval(Eval.later(ImageIO.read(file.toJava))).recoverWith {
      case e: IOException ⇒ IO.raiseError(ReadError(file, e))
    }

  def write(file: File)(i: BufferedImage): IO[Unit] =
    IO(println(s"Writing $file")) *>
      IO(ImageIO.write(i, "png", file.toJava)).void

}
