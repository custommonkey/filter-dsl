import better.files.File.home
import cats.effect.ExitCode.Success
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import stuff.Api._
import stuff.Filters._
import stuff.Values

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Caustics extends IOApp with Values {

  override def run(args: List[String]): IO[ExitCode] = {

    val in = read(home / "Downloads/screen-shot-2015-06-24-at-10-11-24-pm.png")

    (0 to 10).toList.traverse { _ =>
      wild[Caustic] >>= { caustic =>
        print(caustic) >>
          in >>=
          caustic >>=
          write(caustic)
      }
    }

  }.as(Success)

}
