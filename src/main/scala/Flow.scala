import better.files._
import stuff.Filters._
import stuff.Implicits.FileOps
import stuff.Implicits.ImageOps
import stuff.Implicits.fileToBufferedImage

import scala.util.Random.{nextFloat, nextInt}

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Flow extends App {

  val in = File("/Users/jmartin/Desktop/flow/flow1.png")

  in ~>
    colourhalftone() ~>
    noise() ~>
    contrast(brightness = nextFloat(), contrast = nextFloat()) ~>
    ripple(xAmp = nextInt(20), xWaveLength = nextInt(200)) ~>
    invert ~>
    boxblur(hRadius = nextInt(10)) ~>
    glitch ~>
    File("out.png")

}
