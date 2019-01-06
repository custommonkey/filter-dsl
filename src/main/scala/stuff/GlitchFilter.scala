package stuff

import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import javax.imageio.ImageIO

import com.jhlabs.image.AbstractBufferedImageOp

import scala.util.Random

class GlitchFilter extends AbstractBufferedImageOp {

  override def filter(src: BufferedImage, dest: BufferedImage): BufferedImage = retry(10) {
    val buf = new ByteArrayOutputStream()

    ImageIO.write(src, "jpeg", buf)

    val bytes = buf.toByteArray

    val header = bytes.sliding(2).indexWhere(b => b.head == 255 && b.last == 218)

    0 to 5 foreach { _ =>
      bytes(Random.nextInt(bytes.length - header)) = Random.nextInt().toByte
    }

    ImageIO.read(new ByteArrayInputStream(bytes))
  }

  private def retry[T](n: Int)(fn: => T): T = {
    try {
      fn
    } catch {
      case e: Throwable =>
        println(corrupt(toString + " failed " + e.getMessage + " retrying"))
        if (n > 1) retry(n - 1)(fn)
        else throw e
    }
  }

  private def corrupt(s: String) = s.map { c =>
    if (Random.nextInt(10) == 0) {
      Random.nextPrintableChar()
    } else c
  }

  override def toString: String = corrupt(super.toString)

}
