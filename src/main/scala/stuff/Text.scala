package stuff

import java.awt.RenderingHints.{KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON}
import java.awt.geom.{Point2D, Rectangle2D}
import java.awt.image.{BufferedImage, BufferedImageOp, ColorModel}
import java.awt.{Font, RenderingHints}

import stuff.Api.Colour
import stuff.Filters.AbstractFilter

case class Text(words: String, x: Int, y: Int, colour: Colour, font: Font)
    extends AbstractFilter(new BufferedImageOp {

      override def filter(src: BufferedImage, dest: BufferedImage): BufferedImage = {
        val g = src.createGraphics()
        g.setColor(colour)
        g.setFont(font)
        g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON)
        if (x > src.getWidth) throw new IllegalArgumentException() //TODO: Should be dependent type
        if (y > src.getHeight) throw new IllegalArgumentException()
        g.drawString(words, x, y)
        src
      }
      override def getBounds2D(src: BufferedImage): Rectangle2D = ???
      override def createCompatibleDestImage(src: BufferedImage,
                                             destCM: ColorModel): BufferedImage = ???
      override def getPoint2D(srcPt: Point2D, dstPt: Point2D): Point2D          = ???
      override def getRenderingHints: RenderingHints                            = ???
    })
