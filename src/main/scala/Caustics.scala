import better.files._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import eu.timepit.refined.api.RefType
import eu.timepit.refined.auto._
import stuff.Api._
import stuff.Filters.Caustic.Scale
import stuff.Filters._

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Caustics extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    val in = read(File("/Users/jmartin/Downloads/screen-shot-2015-06-24-at-10-11-24-pm.png"))

    {
      for {
        scale  ← 1 to 300 by 20
        amount ← 0 to 10
      } yield {
        val a = RefType.applyRef[Scale].unsafeFrom(scale.toFloat)
        val b = RefType.applyRef[Thing].unsafeFrom(amount.toFloat / 10)

        in >>= caustics(a, 1, b) >>= write(File(s"caustics-$scale-$amount.png"))
      }
    }.toList.sequence

  }.as(ExitCode.Success)

}
