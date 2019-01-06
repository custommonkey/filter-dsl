package stuff

import better.files.File
import cats.implicits._
import org.scalacheck.Arbitrary
import org.scalatest.{Matchers, WordSpec}
import stuff.Api._

class TextSpec extends WordSpec with Matchers with Checks with Values {

  implicit val arbColour: Arbitrary[Colour] = Arbitrary {
    Values.arbColor.arbitrary.map { c =>
      new Colour(c.getRed, c.getGreen, c.getBlue)
    }
  }

  "Text filter" should {
    "draw text" in {
      check { (text: Text, colour: Colour, i: Int) =>
        val height = 400
        print(text) *>
          Blank(height.goldenWidth, height, colour)(()) >>=
          text.copy(x = 20, y = 200) >>=
          write(File(s"test-$i"))
      }
    }
  }

}
