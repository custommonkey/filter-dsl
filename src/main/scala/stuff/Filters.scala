package stuff

import java.awt.Color
import java.awt.image.{BufferedImage, BufferedImageOp}

import cats.effect.IO
import com.jhlabs.image._
import com.jhlabs.math.Function2D
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric.Interval.Closed
import eu.timepit.refined.numeric.Positive
import stuff.Api.Filename
import stuff.Filters.Caustic.{Brightness, Samples}

object Filters extends WithGradient {

  case object ChannelMix extends AbstractFilter(new ChannelMixFilter)

  case class ColourHalftone(dotRadius: Float Refined Positive,
                            cyanScreenAngle: Float,
                            magentaScreenAngle: Float,
                            yellowScreenAngle: Float)
      extends AbstractFilter(new ColorHalftoneFilter) {
    filter.setdotRadius(dotRadius)
    filter.setCyanScreenAngle(cyanScreenAngle)
    filter.setMagentaScreenAngle(magentaScreenAngle)
    filter.setYellowScreenAngle(yellowScreenAngle)
  }

  object Distribution extends Enumeration {
    type Distribution = Value
    val Gaussian, Uniform = Value
  }

  import Distribution._

  case class Noise(amount: Int, distribution: Distribution, monochrome: Boolean, density: Float)
      extends AbstractFilter(new NoiseFilter) {
    filter.setAmount(amount)
    filter.setDistribution(distribution.id)
    filter.setDensity(density)
    filter.setMonochrome(monochrome)
  }

  case class Contrast(brightness: Float = 1, contrast: Float = 1)
      extends AbstractFilter(new ContrastFilter) {
    filter.setBrightness(brightness)
    filter.setContrast(contrast)
  }

  case class Scale(width: Int, height: Int) extends AbstractFilter(new ScaleFilter(width, height))

  case object Invert extends AbstractFilter(new InvertFilter)

  object WaveType extends Enumeration {
    type WaveType = Value

    val Sine, Sawtooth, Triangle, Noise = Value

  }

  import WaveType._

  case class Ripple(xAmp: Float = 5,
                    yAmp: Float = 0,
                    xWaveLength: Float = 16.0f,
                    yWaveLength: Float = 16.0f,
                    waveType: WaveType = Sine)
      extends AbstractFilter(new RippleFilter) {
    filter.setXAmplitude(xAmp)
    filter.setYAmplitude(yAmp)
    filter.setXWavelength(xWaveLength)
    filter.setYWavelength(yWaveLength)
    filter.setWaveType(waveType.id)
  }

  case class Halftone(mask: BufferedImage,
                      softness: Thing,
                      invert: Boolean = false,
                      monochrome: Boolean = false)
      extends AbstractFilter(new HalftoneFilter) {
    filter.setSoftness(softness)
    filter.setMask(mask)
    filter.setInvert(invert)
    filter.setMonochrome(monochrome)
  }

  type Thing = Float Refined Closed[W.`0f`.T, W.`1f`.T]

  implicit val filename: Filename[Filter] = _.toString()

  object Caustic {
    type Scale      = Float Refined Closed[W.`1f`.T, W.`300f`.T]
    type Brightness = Int Refined Closed[W.`0`.T, W.`1`.T]
    type Samples    = Int Refined Closed[W.`0`.T, W.`10`.T]

    implicit val filename: Filename[Caustic] = { c =>
      import c._

//      val xx = LabelledGeneric[Caustic].to(c)

//      xx.l

      s"$productPrefix-scala-${c.scale}-brightness-$brightness-amount-$amount-dispersion-$dispersion-time-$time-samples-$samples-color-$bgColor"
    }
  }

  sealed trait Filter extends (BufferedImage => IO[BufferedImage])

  abstract class AbstractFilter[T <: BufferedImageOp](val filter: T) extends Filter {
    override def apply(i: BufferedImage): IO[BufferedImage] =
      IO(filter.filter(i, new BufferedImage(i.getWidth, i.getHeight(), i.getType)))
  }

  final case class Caustic(scale: Caustic.Scale,
                           brightness: Brightness,
                           amount: Thing,
                           turbulence: Thing,
                           dispersion: Thing,
                           time: Float,
                           samples: Samples,
                           bgColor: Color)
      extends AbstractFilter(new CausticsFilter) {
    filter.setScale(scale)
    filter.setBrightness(brightness)
    filter.setAmount(amount)
    filter.setTurbulence(turbulence)
    filter.setDispersion(dispersion)
    filter.setTime(time)
    filter.setSamples(samples)
    filter.setBgColor(bgColor.getRGB)
  }

  case class BoxBlur(hRadius: Float, vRadius: Float, iterations: Int, premultiplyAlpha: Boolean)
      extends AbstractFilter(new BoxBlurFilter()) {
    filter.setHRadius(hRadius)
    filter.setVRadius(vRadius)
    filter.setIterations(iterations)
    filter.setPremultiplyAlpha(premultiplyAlpha)
  }

  case object Glitch       extends AbstractFilter(new GlitchFilter)
  case object ApplyMask    extends AbstractFilter(new ApplyMaskFilter)
  case object Average      extends AbstractFilter(new AverageFilter)
  case object BicubicScale extends AbstractFilter(new BicubicScaleFilter)
  case class Block(blockSize: Int Refined Positive) extends AbstractFilter(new BlockFilter) {
    filter.setBlockSize(blockSize)
  }
  case object Blur         extends AbstractFilter(new BlurFilter)
  case object Border       extends AbstractFilter(new BorderFilter)
  case object BoxBlur      extends AbstractFilter(new BoxBlurFilter)
  case object BrushedMetal extends AbstractFilter(new BrushedMetalFilter)
  case object Bump         extends AbstractFilter(new BumpFilter)
  case object Cellular     extends AbstractFilter(new CellularFilter)
  case object Check        extends AbstractFilter(new CheckFilter)
  case object ChromaKey    extends AbstractFilter(new ChromaKeyFilter)
  case object Chrome       extends AbstractFilter(new ChromeFilter)
  case object Circle       extends AbstractFilter(new CircleFilter)
//  def composite(a: BufferedImage, b: BufferedImage): Filter =
//    gilter(new CompositeFilter(AlphaComposite.Src.derive(0.5f)))
  case object Contour      extends AbstractFilter(new ContourFilter)
  case object Contrast     extends AbstractFilter(new ContrastFilter)
  case object Convolve     extends AbstractFilter(new ConvolveFilter)
  case object Crop         extends AbstractFilter(new CropFilter)
  case object Crystallize  extends AbstractFilter(new CrystallizeFilter)
  case object Curl         extends AbstractFilter(new CurlFilter)
  case object Curves       extends AbstractFilter(new CurvesFilter)
  case object Deinterlace  extends AbstractFilter(new DeinterlaceFilter)
  case object Despeckle    extends AbstractFilter(new DespeckleFilter)
  case object Diffuse      extends AbstractFilter(new DiffuseFilter)
  case object Diffusion    extends AbstractFilter(new DiffusionFilter)
  case object Dilate       extends AbstractFilter(new DilateFilter)
  case object Displace     extends AbstractFilter(new DisplaceFilter)
  case object Dissolve     extends AbstractFilter(new DissolveFilter)
  case object Dither       extends AbstractFilter(new DitherFilter)
  case object DoG          extends AbstractFilter(new DoGFilter)
  case object Edge         extends AbstractFilter(new EdgeFilter)
  case object Emboss       extends AbstractFilter(new EmbossFilter)
  case object Equalize     extends AbstractFilter(new EqualizeFilter)
  case object ErodeAlpha   extends AbstractFilter(new ErodeAlphaFilter)
  case object Erode        extends AbstractFilter(new ErodeFilter)
  case object Exposure     extends AbstractFilter(new ExposureFilter)
  case object FBM          extends AbstractFilter(new FBMFilter)
  case object Fade         extends AbstractFilter(new FadeFilter)
  case object Feedback     extends AbstractFilter(new FeedbackFilter)
  case object FieldWarp    extends AbstractFilter(new FieldWarpFilter)
  case object Fill         extends AbstractFilter(new FillFilter)
  case object Flare        extends AbstractFilter(new FlareFilter)
  case object Flip         extends AbstractFilter(new FlipFilter)
  case object Flush3D      extends AbstractFilter(new Flush3DFilter)
  case object FourColor    extends AbstractFilter(new FourColorFilter)
  case object Gain         extends AbstractFilter(new GainFilter)
  case object Gamma        extends AbstractFilter(new GammaFilter)
  case object Gaussian     extends AbstractFilter(new GaussianFilter)
  case object Glint        extends AbstractFilter(new GlintFilter)
  case object Glow         extends AbstractFilter(new GlowFilter)
  case object GradientWipe extends AbstractFilter(new GradientWipeFilter)
  case object Gray         extends AbstractFilter(new GrayFilter)
  case object Grayscale    extends AbstractFilter(new GrayscaleFilter)
  case object HSBAdjust    extends AbstractFilter(new HSBAdjustFilter)
  case object Halftone     extends AbstractFilter(new HalftoneFilter)
  case object HighPass     extends AbstractFilter(new HighPassFilter)
  case object Interpolate  extends AbstractFilter(new InterpolateFilter)
  case object InvertAlpha  extends AbstractFilter(new InvertAlphaFilter)
  case object JavaLnF      extends AbstractFilter(new JavaLnFFilter)
  case object Kaleidoscope extends AbstractFilter(new KaleidoscopeFilter)
  case object Key          extends AbstractFilter(new KeyFilter)
  case object Laplace      extends AbstractFilter(new LaplaceFilter)
  case object LensBlur     extends AbstractFilter(new LensBlurFilter)
  case object Levels       extends AbstractFilter(new LevelsFilter)
  case object Life         extends AbstractFilter(new LifeFilter)
  case object Light        extends AbstractFilter(new LightFilter)
  case object Lookup       extends AbstractFilter(new LookupFilter)
  case object MapColors    extends AbstractFilter(new MapColorsFilter)

  case class map(xf: (Float, Float) => Float, yf: (Float, Float) => Float)
      extends AbstractFilter(new MapFilter) {
    class F(f: (Float, Float) => Float) extends Function2D {
      override def evaluate(x: Float, y: Float): Float = f(x, y)
    }
    filter.setXMapFunction(new F(xf))
    filter.setYMapFunction(new F(yf))
  }

  case object Marble       extends AbstractFilter(new MarbleFilter)
  case object MarbleTex    extends AbstractFilter(new MarbleTexFilter)
  case object Mask         extends AbstractFilter(new MaskFilter)
  case object Maximum      extends AbstractFilter(new MaximumFilter)
  case object Median       extends AbstractFilter(new MedianFilter)
  case object Minimum      extends AbstractFilter(new MinimumFilter)
  case object Mirror       extends AbstractFilter(new MirrorFilter)
  case object MotionBlur   extends AbstractFilter(new MotionBlurFilter)
  case object MotionBlurOp extends AbstractFilter(new MotionBlurOp)
  case object Noise        extends AbstractFilter(new NoiseFilter)
  case object Offset       extends AbstractFilter(new OffsetFilter)
  case object Oil          extends AbstractFilter(new OilFilter)
  case object Opacity      extends AbstractFilter(new OpacityFilter)
  case object Outline      extends AbstractFilter(new OutlineFilter)
  case object Perspective  extends AbstractFilter(new PerspectiveFilter)
  case object Pinch        extends AbstractFilter(new PinchFilter)
  case object Plasma       extends AbstractFilter(new PlasmaFilter)
  case object Pointillize  extends AbstractFilter(new PointillizeFilter)
  case object Polar        extends AbstractFilter(new PolarFilter)
  case object Posterize    extends AbstractFilter(new PosterizeFilter)
  case object Premultiply  extends AbstractFilter(new PremultiplyFilter)
  case object Quantize     extends AbstractFilter(new QuantizeFilter)
  case object Quilt        extends AbstractFilter(new QuiltFilter)
  case object RGBAdjust    extends AbstractFilter(new RGBAdjustFilter)

  //TODO def rays = new RaysFilter

  case object ReduceNoise   extends AbstractFilter(new ReduceNoiseFilter)
  case object RenderText    extends AbstractFilter(new RenderTextFilter)
  case object Rescale       extends AbstractFilter(new RescaleFilter)
  case object Ripple        extends AbstractFilter(new RippleFilter)
  case object Rotate        extends AbstractFilter(new RotateFilter)
  case object Saturation    extends AbstractFilter(new SaturationFilter)
  case object Scale         extends AbstractFilter(new ScaleFilter)
  case object Scratch       extends AbstractFilter(new ScratchFilter)
  case object Shade         extends AbstractFilter(new ShadeFilter)
  case object Shadow        extends AbstractFilter(new ShadowFilter)
  case object Shape         extends AbstractFilter(new ShapeFilter)
  case object Sharpen       extends AbstractFilter(new SharpenFilter)
  case object Shatter       extends AbstractFilter(new ShatterFilter)
  case object Shear         extends AbstractFilter(new ShearFilter)
  case object Shine         extends AbstractFilter(new ShineFilter)
  case object Skeleton      extends AbstractFilter(new SkeletonFilter)
  case object Sky           extends AbstractFilter(new SkyFilter)
  case object SmartBlur     extends AbstractFilter(new SmartBlurFilter)
  case object Smear         extends AbstractFilter(new SmearFilter)
  case object Solarize      extends AbstractFilter(new SolarizeFilter)
  case object Sparkle       extends AbstractFilter(new SparkleFilter)
  case object Sphere        extends AbstractFilter(new SphereFilter)
  case object Stamp         extends AbstractFilter(new StampFilter)
  case object Swim          extends AbstractFilter(new SwimFilter)
  case object Swizzle       extends AbstractFilter(new SwizzleFilter)
  case object Texture       extends AbstractFilter(new TextureFilter)
  case object Threshold     extends AbstractFilter(new ThresholdFilter)
  case object TileImage     extends AbstractFilter(new TileImageFilter)
  case object Tritone       extends AbstractFilter(new TritoneFilter)
  case object Twirl         extends AbstractFilter(new TwirlFilter)
  case object Unpremultiply extends AbstractFilter(new UnpremultiplyFilter)
  case object Unsharp       extends AbstractFilter(new UnsharpFilter)
  case object VariableBlur  extends AbstractFilter(new VariableBlurFilter)
  case object Warp          extends AbstractFilter(new WarpFilter)
  case object Water         extends AbstractFilter(new WaterFilter)
  case object Weave         extends AbstractFilter(new WeaveFilter)
  case object Wood          extends AbstractFilter(new WoodFilter)

}
