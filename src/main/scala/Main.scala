import better.files._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import stuff.Api._
import stuff.Filters._
import eu.timepit.refined.auto._

import scala.util.Random.nextFloat

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Main extends IOApp {

  //val one = caustics()
  //halftone(softness = 1) >>=
  //caustics >>=
  //scale() >>=

  //val in = File("/Users/jmartin/Desktop/flow/flow1.png")
  private val in = read(File("/Users/jmartin/Downloads/screen-shot-2015-06-24-at-10-11-24-pm.png"))
  //val bohr = File("/Users/jeffmartin/Downloads/Niels_Bohr.jpg")
  //val in = File("/Users/jeffmartin/Downloads/6060918265_1ee61fbd5a_m.jpg")

  override def run(args: List[String]): IO[ExitCode] = {
    in >>=
      colourhalftone() >>=
      noise() >>=
      contrast(brightness = nextFloat(), contrast = nextFloat()) >>=
      ripple(xAmp = nextFloat() * 20, xWaveLength = nextFloat() * 200) >>=
      //invert >>=
      boxblur(hRadius = nextFloat() * 10) >>=
      glitch >>=
      write(File("out.png"))

//  in >>= (a ⇒ composite(a, a)(a)) >>= write(File("composite.png"))
    //TODO in >>= flip >>= write(File("flip.png"))
    //TODO in >>= rays >>= write(File("rays.png"))
    //TODO in >>= shear >>= write(File("shear.png"))
    //TODO in >>= shine >>= write(File("shine.png"))
    //TODO in >>= warp >>= write(File("warp.png"))
    val filters = List(
      applyMask,
      average,
      bicubicScale,
      block,
      blur,
      border,
      boxBlur,
      brushedMetal,
      bump,
      caustics(23f, 1, .5f),
      cellular,
      check,
      chromaKey,
      chrome,
      circle,
      colourhalftone(),
      contour,
      contrast,
      convolve,
      crop,
      crystallize,
      curl,
      curves,
      deinterlace,
      despeckle,
      diffuse,
      diffusion,
      dilate,
      displace,
      dissolve,
      dither,
//      doG,
      edge,
      emboss,
      equalize,
      erode,
      erodeAlpha,
      exposure,
      fBM,
      fade,
      feedback,
      fieldWarp,
      fill,
      flare,
      flush3D,
      fourColor,
      gain,
      gamma,
      gaussian,
      glint,
      glow,
      gradient(),
      gradientWipe,
      gray,
      grayscale,
      hSBAdjust,
      halftone,
      highPass,
      interpolate,
      invertAlpha,
      javaLnF,
      kaleidoscope,
      key,
      laplace,
      lensBlur,
      levels,
      life,
      light,
      lookup,
//      map((x, y) => x * y, (x, y) => x * y),
      mapColors,
      marble,
      marbleTex,
      mask,
      maximum,
      median,
      minimum,
      mirror,
      motionBlur,
      motionBlurOp,
      noise,
      offset,
      oil,
      opacity,
      outline,
      perspective,
      pinch,
      plasma,
      pointillize,
      polar,
      posterize,
      premultiply,
      quantize,
      quilt,
      rGBAdjust,
      reduceNoise,
      renderText,
      rescale,
      ripple,
      rotate,
      saturation,
      scale,
      scratch,
      shade,
      shadow,
      shape,
      sharpen,
      shatter,
      skeleton,
      sky,
      smartBlur,
      smear,
      solarize,
      sparkle,
      sphere,
      stamp,
      swim,
      swizzle,
      texture,
      threshold,
      tileImage,
      tritone,
      twirl,
      unpremultiply,
      unsharp,
      variableBlur,
      water,
      weave,
      wood
    ).zipWithIndex

    (filters, filters).traverseN {
      case ((af, ai), (bf, bi)) ⇒
        in >>= af >>= bf >>= write(File(s"out-$ai-$bi.png"))
    }

  }.as(ExitCode.Success)

}
