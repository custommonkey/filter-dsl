import better.files._
import cats.effect.ExitCode.Success
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import stuff.Api._
import stuff.Filters._
import eu.timepit.refined.auto._
import stuff.Values

import scala.util.Random.nextFloat

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Main extends IOApp with Values {

  //val one = caustics()
  //halftone(softness = 1) >>=
  //caustics >>=
  //scale() >>=

  //val in = File("/Users/jmartin/Desktop/flow/flow1.png")
  private val in = read(File.home / "Downloads/screen-shot-2015-06-24-at-10-11-24-pm.png")
  //val bohr = File("/Users/jeffmartin/Downloads/Niels_Bohr.jpg")
  //val in = File("/Users/jeffmartin/Downloads/6060918265_1ee61fbd5a_m.jpg")

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      halftone <- wild[ColourHalftone]
      blur     <- wild[BoxBlur]
    } yield {
      in >>=
        halftone >>=
        Noise >>=
        Contrast(brightness = nextFloat(), contrast = nextFloat()) >>=
        Ripple(xAmp = nextFloat() * 20, xWaveLength = nextFloat() * 200) >>=
        //invert >>=
        blur >>=
        Glitch >>=
        write(File("out.png"))
    }

//  in >>= (a ⇒ composite(a, a)(a)) >>= write(File("composite.png"))
    //TODO in >>= flip >>= write(File("flip.png"))
    //TODO in >>= rays >>= write(File("rays.png"))
    //TODO in >>= shear >>= write(File("shear.png"))
    //TODO in >>= shine >>= write(File("shine.png"))
    //TODO in >>= warp >>= write(File("warp.png"))
    val filters = List[Filter](
      ApplyMask,
      Average,
      BicubicScale,
      wild[Block].unsafeRunSync(),
      Blur,
      Border,
      BoxBlur,
      BrushedMetal,
      Bump,
      wild[Caustic].unsafeRunSync(),
      Cellular,
      Check,
      ChromaKey,
      Chrome,
      Circle,
      Halftone,
      Contour,
      Contrast,
      Convolve,
      Crop,
      Crystallize,
      Curl,
      Curves,
      Deinterlace,
      Despeckle,
      Diffuse,
      Diffusion,
      Dilate,
      Displace,
      Dissolve,
      Dither,
//      DoG,
//        Edge,
//        Emboss,
//        Equalize,
//        Erode,
//        ErodeAlpha,
//        Exposure,
//        FBM,
//        Fade,
//        Feedback,
//        FieldWarp,
//        Fill,
//        Flare,
//        Flush3D,
//        FourColor,
//        Gain,
//        Gamma,
//        Gaussian,
//        Glint,
//        Glow,
//        gradient(),
//        GradientWipe,
//        Gray,
//        Grayscale,
//        HSBAdjust,
//        Halftone,
//        HighPass,
//        Interpolate,
//        InvertAlpha,
//        JavaLnF,
//        Kaleidoscope,
//        Key,
//        Laplace,
//        LensBlur,
//        Levels,
//        Life,
//        Light,
//        Lookup,
//      Map((x, y) => x * y, (x, y) => x * y),
//        MapColors,
//        Marble,
//        MarbleTex,
//        Mask,
//        Maximum,
//        Median,
//        Minimum,
//        Mirror,
//        MotionBlur,
//        MotionBlurOp,
//        Noise,
//        Offset,
//        Oil,
//        Opacity,
//        Outline,
//        Perspective,
//        Pinch,
//        Plasma,
//        Pointillize,
//        Polar,
//        Posterize,
//        Premultiply,
//        Quantize,
//        Quilt,
//        RGBAdjust,
//        ReduceNoise,
//        RenderText,
//        Rescale,
//        Ripple,
//        Rotate,
//        Saturation,
//        Scale,
//        Scratch,
//        Shade,
//        Shadow,
//        Shape,
//        Sharpen,
//        Shatter,
//        Skeleton,
//        Sky,
//        SmartBlur,
//        Smear,
//        Solarize,
//        Sparkle,
//        Sphere,
//        Stamp,
//        Swim,
//        Swizzle,
//        Texture,
//        Threshold,
//        TileImage,
//        Tritone,
//        Twirl,
//        Unpremultiply,
//        Unsharp,
//        VariableBlur,
//        Water,
//        Weave,
//        Wood
    )

    implicitly[Filename[Filter]]
    tupleFilename[Filter, Filter]
    implicitly[Filename[(Filter, Filter)]]

    (filters, filters).traverseN {
      case (af, bf) =>
        in >>= af >>= bf >>= write(af -> bf)
    }
  }.as(Success)

}
