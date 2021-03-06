package stuff

import java.awt.Font.{BOLD, ITALIC, PLAIN}
import java.awt.{Color, Font, GraphicsEnvironment}

import cats.effect.IO
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.scalacheck.arbitraryRefType
import org.scalacheck.Gen.Choose
import org.scalacheck.{Arbitrary, Gen, ScalacheckShapeless}
import stuff.Filters.Caustic.{Brightness, Samples, Scale}
import stuff.Filters.Thing

trait Values extends ScalacheckShapeless {

  def wild[T](implicit arbitrary: Arbitrary[T]): IO[T] = IO(arbitrary.arbitrary.sample.get)

  implicit def arbPositive[T: Numeric](implicit c: Choose[T]): Arbitrary[T Refined Positive] =
    arbitraryRefType(Gen.posNum[T])
  implicit val arbBrightness: Arbitrary[Brightness] = arbitraryRefType(Gen.oneOf(0, 1))
  implicit val arbScala: Arbitrary[Scale]           = arbitraryRefType(Gen.choose(1f, 300f))
  implicit val arbThing: Arbitrary[Thing]           = arbitraryRefType(Gen.choose(0f, 1f))
  implicit val arbSamples: Arbitrary[Samples]       = arbitraryRefType(Gen.choose(1, 10))
  implicit val arbColor: Arbitrary[Color] = Arbitrary {
    for {
      r <- Gen.choose(0, 255)
      g <- Gen.choose(0, 255)
      b <- Gen.choose(0, 255)
      a <- Gen.choose(0, 255)
    } yield new Color(r, g, b, a)
  }
  implicit val arbFont: Arbitrary[Font] = Arbitrary {
    for {
      name  <- Gen.oneOf(GraphicsEnvironment.getLocalGraphicsEnvironment.getAvailableFontFamilyNames)
      style <- Gen.oneOf(PLAIN, BOLD, ITALIC)
      size  <- Gen.choose(15, 100)
    } yield new Font(name, style, size)
  }

}

object Values extends Values
