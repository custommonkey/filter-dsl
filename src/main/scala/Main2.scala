import better.files._
import stuff.Filters._
import stuff.Implicits.{FileOps, ImageOps, fileToBufferedImage}

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Main2 /*extends App*/ {

  val bohr = File("/Users/jeffmartin/Downloads/Niels_Bohr.jpg")
  val in = File("/Users/jeffmartin/Downloads/6060918265_1ee61fbd5a_m.jpg")

  in ~> composite(in, bohr) ~> "composite.png"

}
