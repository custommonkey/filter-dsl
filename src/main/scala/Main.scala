import better.files._
import stuff.Filters._
import stuff.Implicits.FileOps
import stuff.Implicits.ImageOps
import stuff.Implicits.fileToBufferedImage

import scala.util.Random.{nextFloat, nextInt}

/**
  * http://www.jhlabs.com/ip/filters/
  */
object Main /*extends App*/ {

  //val one = caustics()
  //halftone(softness = 1) ~>
  //caustics ~>
  //scale() ~>

  //val in = File("/Users/jmartin/Desktop/flow/flow1.png")
  val in = File("/Users/jmartin/Downloads/5983207856_f663495dd9_m.jpg")
  //val bohr = File("/Users/jeffmartin/Downloads/Niels_Bohr.jpg")
  //val in = File("/Users/jeffmartin/Downloads/6060918265_1ee61fbd5a_m.jpg")

  in ~>
    colourhalftone() ~>
    noise() ~>
    contrast(brightness = nextFloat(), contrast = nextFloat()) ~>
    ripple(xAmp = nextInt(20), xWaveLength = nextInt(200)) ~>
    //invert ~>
    boxblur(hRadius = nextInt(10)) ~>
    glitch ~>
    File("out.png")

  in ~> applyMask ~> "applyMask.png"
  in ~> average ~> "average.png"
  in ~> bicubicScale ~> "bicubicScale.png"
  in ~> block ~> "block.png"
  in ~> blur ~> "blur.png"
  in ~> border ~> "border.png"
  in ~> boxBlur ~> "boxBlur.png"
  in ~> brushedMetal ~> "brushedMetal.png"
  in ~> bump ~> "bump.png"
  in ~> caustics ~> "caustics.png"
  in ~> cellular ~> "cellular.png"
  in ~> check ~> "check.png"
  in ~> chromaKey ~> "chromaKey.png"
  in ~> chrome ~> "chrome.png"
  in ~> circle ~> "circle.png"
  in ~> colourhalftone() ~> "colorHalftone.png"
  in ~> composite(in, in) ~> "composite.png"
  in ~> contour ~> "contour.png"
  in ~> contrast ~> "contrast.png"
  in ~> convolve ~> "convolve.png"
  in ~> crop ~> "crop.png"
  in ~> crystallize ~> "crystallize.png"
  in ~> curl ~> "curl.png"
  in ~> curves ~> "curves.png"
  in ~> deinterlace ~> "deinterlace.png"
  in ~> despeckle ~> "despeckle.png"
  in ~> diffuse ~> "diffuse.png"
  in ~> diffusion ~> "diffusion.png"
  in ~> dilate ~> "dilate.png"
  in ~> displace ~> "displace.png"
  in ~> dissolve ~> "dissolve.png"
  in ~> dither ~> "dither.png"
  in ~> doG ~> "doG.png"
  in ~> edge ~> "edge.png"
  in ~> emboss ~> "emboss.png"
  in ~> equalize ~> "equalize.png"
  in ~> erode ~> "erode.png"
  in ~> erodeAlpha ~> "erodeAlpha.png"
  in ~> exposure ~> "exposure.png"
  in ~> fBM ~> "fBM.png"
  in ~> fade ~> "fade.png"
  in ~> feedback ~> "feedback.png"
  in ~> fieldWarp ~> "fieldWarp.png"
  in ~> fill ~> "fill.png"
  in ~> flare ~> "flare.png"
  //TODO in ~> flip ~> "flip.png"
  in ~> flush3D ~> "flush3D.png"
  in ~> fourColor ~> "fourColor.png"
  in ~> gain ~> "gain.png"
  in ~> gamma ~> "gamma.png"
  in ~> gaussian ~> "gaussian.png"
  in ~> glint ~> "glint.png"
  in ~> glow ~> "glow.png"
  in ~> gradient() ~> "gradient.png"
  in ~> gradientWipe ~> "gradientWipe.png"
  in ~> gray ~> "gray.png"
  in ~> grayscale ~> "grayscale.png"
  in ~> hSBAdjust ~> "hSBAdjust.png"
  in ~> halftone ~> "halftone.png"
  in ~> highPass ~> "highPass.png"
  in ~> interpolate ~> "interpolate.png"
  in ~> invertAlpha ~> "invertAlpha.png"
  in ~> javaLnF ~> "javaLnF.png"
  in ~> kaleidoscope ~> "kaleidoscope.png"
  in ~> key ~> "key.png"
  in ~> laplace ~> "laplace.png"
  in ~> lensBlur ~> "lensBlur.png"
  in ~> levels ~> "levels.png"
  in ~> life ~> "life.png"
  in ~> light ~> "light.png"
  in ~> lookup ~> "lookup.png"
  in ~> map((x, y) => x * y, (x, y) => x * y) ~> "map.png"
  in ~> mapColors ~> "mapColors.png"
  in ~> marble ~> "marble.png"
  in ~> marbleTex ~> "marbleTex.png"
  in ~> mask ~> "mask.png"
  in ~> maximum ~> "maximum.png"
  in ~> median ~> "median.png"
  in ~> minimum ~> "minimum.png"
  in ~> mirror ~> "mirror.png"
  in ~> motionBlur ~> "motionBlur.png"
  in ~> motionBlurOp ~> "motionBlurOp.png"
  in ~> noise ~> "noise.png"
  in ~> offset ~> "offset.png"
  in ~> oil ~> "oil.png"
  in ~> opacity ~> "opacity.png"
  in ~> outline ~> "outline.png"
  in ~> perspective ~> "perspective.png"
  in ~> pinch ~> "pinch.png"
  in ~> plasma ~> "plasma.png"
  in ~> pointillize ~> "pointillize.png"
  in ~> polar ~> "polar.png"
  in ~> posterize ~> "posterize.png"
  in ~> premultiply ~> "premultiply.png"
  in ~> quantize ~> "quantize.png"
  in ~> quilt ~> "quilt.png"
  in ~> rGBAdjust ~> "rGBAdjust.png"
  //TODO in ~> rays ~> "rays.png"
  in ~> reduceNoise ~> "reduceNoise.png"
  in ~> renderText ~> "renderText.png"
  in ~> rescale ~> "rescale.png"
  in ~> ripple ~> "ripple.png"
  in ~> rotate ~> "rotate.png"
  in ~> saturation ~> "saturation.png"
  in ~> scale ~> "scale.png"
  in ~> scratch ~> "scratch.png"
  in ~> shade ~> "shade.png"
  in ~> shadow ~> "shadow.png"
  in ~> shape ~> "shape.png"
  in ~> sharpen ~> "sharpen.png"
  in ~> shatter ~> "shatter.png"
  //TODO in ~> shear ~> "shear.png"
  //TODO in ~> shine ~> "shine.png"
  in ~> skeleton ~> "skeleton.png"
  in ~> sky ~> "sky.png"
  in ~> smartBlur ~> "smartBlur.png"
  in ~> smear ~> "smear.png"
  in ~> solarize ~> "solarize.png"
  in ~> sparkle ~> "sparkle.png"
  in ~> sphere ~> "sphere.png"
  in ~> stamp ~> "stamp.png"
  in ~> swim ~> "swim.png"
  in ~> swizzle ~> "swizzle.png"
  in ~> texture ~> "texture.png"
  in ~> threshold ~> "threshold.png"
  in ~> tileImage ~> "tileImage.png"
  in ~> tritone ~> "tritone.png"
  in ~> twirl ~> "twirl.png"
  in ~> unpremultiply ~> "unpremultiply.png"
  in ~> unsharp ~> "unsharp.png"
  in ~> variableBlur ~> "variableBlur.png"
  //TODO in ~> warp ~> "warp.png"
  in ~> water ~> "water.png"
  in ~> weave ~> "weave.png"
  in ~> wood ~> "wood.png"
}
