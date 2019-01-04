import java.awt.image.BufferedImage

import better.files._
import cats.effect.IO
import stuff.Api._

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Main2 /*extends App*/ {

  val bohr: IO[BufferedImage] = read(File("/Users/jeffmartin/Downloads/Niels_Bohr.jpg"))
  val in: IO[BufferedImage]   = read(File("/Users/jeffmartin/Downloads/6060918265_1ee61fbd5a_m.jpg"))
//  val c: IO[Filter]           = (in, bohr).mapN(composite)
//
//  c >>= write(File("composite.png"))

}
