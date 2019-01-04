import better.files._
import cats.effect.ExitCode.Success
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import stuff.Api._
import stuff.Filters._

import scala.util.Random.nextFloat

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Flow extends IOApp {

  val in = File("/Users/jmartin/Desktop/flow/flow1.png")

  override def run(args: List[String]): IO[ExitCode] = {

    read(in) >>=
      colourhalftone() >>=
      noise() >>=
      contrast(brightness = nextFloat(), contrast = nextFloat()) >>=
      ripple(xAmp = nextFloat() * 20, xWaveLength = nextFloat() * 200) >>=
      invert >>=
      boxblur(hRadius = nextFloat() * 10) >>=
      glitch >>=
      write(File("out.png"))

  }.as(Success)

}
