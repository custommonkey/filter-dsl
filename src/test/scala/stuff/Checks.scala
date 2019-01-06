package stuff

import cats.effect.IO
import org.scalacheck.Arbitrary
import org.scalatest.prop.PropertyChecks

trait Checks extends PropertyChecks {

  def check[T, A, B, C](tests: (A, B, C) => IO[T])(implicit arbA: Arbitrary[A],
                                                   arbB: Arbitrary[B],
                                                   arbC: Arbitrary[C]): Unit = forAll {
    (a: A, b: B, c: C) =>
      tests(a, b, c).unsafeRunSync()
  }

  def check[T, A](tests: A => IO[T])(implicit arbA: Arbitrary[A]): Unit = forAll { a: A =>
    tests(a).unsafeRunSync()
  }

}
