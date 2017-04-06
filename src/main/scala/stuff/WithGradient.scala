/**
  * Copyright Homeaway, Inc 2016-Present. All Rights Reserved.
  * No unauthorized use of this software.
  */
package stuff

import java.awt.Point

import com.jhlabs.image._
import enumeratum.values.{IntEnum, IntEnumEntry}

import scala.collection.immutable.IndexedSeq

trait WithGradient {

  sealed abstract class GradientType(val value: Int) extends IntEnumEntry

  object GradientType extends IntEnum[GradientType] {

    val values: IndexedSeq[GradientType] = findValues

    case object Linear extends GradientType(0)

    case object Bilinear extends GradientType(1)

    case object Radial extends GradientType(2)

    case object Conical extends GradientType(3)

    case object Biconical extends GradientType(4)

    case object Square extends GradientType(5)

  }

  sealed abstract class GradientInterpolation(val value: Int)
      extends IntEnumEntry

  object GradientInterpolation extends IntEnum[GradientInterpolation] {
    val values: IndexedSeq[GradientInterpolation] = findValues

    case object Linear extends GradientInterpolation(0)

    case object CircleUp extends GradientInterpolation(1)

    case object CircleDown extends GradientInterpolation(2)

    case object Smooth extends GradientInterpolation(3)

  }

  def gradient(p1: Point = new Point(0, 0),
               p2: Point = new Point(64, 64),
               color1: Int = 0xff000000,
               color2: Int = 0xffffffff,
               repeat: Boolean = false,
               `type`: GradientType = GradientType.Linear,
               interpolation: GradientInterpolation =
                 GradientInterpolation.Linear) =
    new GradientFilter(p1,
                       p2,
                       color1,
                       color2,
                       repeat,
                       `type`.value,
                       interpolation.value)
}
