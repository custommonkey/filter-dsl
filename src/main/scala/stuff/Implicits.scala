package stuff

import java.awt.image.{BufferedImage, BufferedImageOp}
import javax.imageio.ImageIO

import better.files._

object Implicits {

  implicit def fileToBufferedImage(file: File): BufferedImage =
    ImageIO.read(file.toJava)

  trait FilterOps {
    def applyFilter(image: BufferedImage, filter: BufferedImageOp): BufferedImage = {
      println(s"Applying filter $filter")
      filter.filter(image, new BufferedImage(image.getWidth, image.getHeight(), image.getType))
    }
  }

  trait Arrows {

    def ~>(filter: BufferedImageOp): BufferedImage

  }

  implicit class ImageOps(image: BufferedImage) extends FilterOps with Arrows {
    def ~>(file: String): Unit = ~>(File(file))

    def ~>(file: File): Unit = {
      println(s"Writing file $file")
      ImageIO.write(image, "png", file.toJava)
    }

    def ~>(filter: BufferedImageOp): BufferedImage = applyFilter(image, filter)
  }

  implicit class FileOps(file: File) extends FilterOps with Arrows {
    def ~>(filter: BufferedImageOp): BufferedImage = {
      val image = ImageIO.read(file.toJava)
      applyFilter(image, filter)
    }

  }

}
