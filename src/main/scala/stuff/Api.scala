package stuff

import java.awt.image.BufferedImage
import java.io.IOException

import better.files.File
import cats.Eval
import cats.effect.ExitCode.Success
import cats.effect.{ExitCode, IO}
import cats.implicits._
import javax.imageio.ImageIO

object Api {

  case class ReadError(file: File, e: IOException)
      extends Throwable(s"Error loading $file, ${e.getMessage}")

  def read(file: File): IO[BufferedImage] =
    IO.eval(Eval.later(ImageIO.read(file.toJava))).recoverWith {
      case e: IOException => IO.raiseError(ReadError(file, e))
    }

  trait Filename[T] extends (T => String)

  implicit def tupleFilename[A, B](implicit a: Filename[A], b: Filename[B]): Filename[(A, B)] = {
    case (aa, bb) =>
      a(aa) + "-" + b(bb)
  }

  implicit val fileFilename: Filename[File] = _.toString

  def write[T](t: T)(i: BufferedImage)(implicit toFile: Filename[T]): IO[ExitCode] = {
    val file = toFile(t)
    print(s"Writing $file") *>
      IO(ImageIO.write(i, "png", new java.io.File(file + ".png"))).void
  }.as(Success)

  def print(a: Any): IO[Unit] = IO(pprint.pprintln(a))

}
