import better.files._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import stuff.Api._
import stuff.Filters._
import stuff.Values
import File.home

import scala.util.Random.nextFloat

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Flow extends IOApp with Values {

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      halftone <- wild[ColourHalftone]
      blur     <- wild[BoxBlur]
    } yield
      read(File(home / "Desktop/flow/flow1.png")) >>=
        halftone >>=
        Noise >>=
        Contrast(brightness = nextFloat(), contrast = nextFloat()) >>=
        Ripple(xAmp = nextFloat() * 20, xWaveLength = nextFloat() * 200) >>=
        Invert >>=
        blur >>=
        Glitch >>=
        write(File("out.png"))
  }.flatten

}
