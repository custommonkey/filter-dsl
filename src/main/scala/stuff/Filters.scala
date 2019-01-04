package stuff

import java.awt.Color
import java.awt.image.BufferedImage

import com.jhlabs.image._
import com.jhlabs.math.Function2D
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric.Interval.Closed
import stuff.Filter._

object Filters extends WithGradient {

  val channelMix: Filter = gilter(new ChannelMixFilter)

  def colourhalftone(
      dotRadius: Float = 2,
      cyanScreenAngle: Float = Math.toRadians(108).toFloat,
      magentaScreenAngle: Float = Math.toRadians(162).toFloat,
      yellowScreenAngle: Float = Math.toRadians(90).toFloat
  ): Filter = filter(new ColorHalftoneFilter) { f ⇒
    f.setdotRadius(dotRadius)
    f.setCyanScreenAngle(cyanScreenAngle)
    f.setMagentaScreenAngle(magentaScreenAngle)
    f.setYellowScreenAngle(yellowScreenAngle)
  }

  object Distribution extends Enumeration {
    type Distribution = Value
    val Gaussian, Uniform = Value
  }

  import Distribution._

  def noise(
      amount: Int = 25,
      distribution: Distribution = Uniform,
      monochrome: Boolean = false,
      density: Float = 1
  ): Filter = filter(new NoiseFilter) { f ⇒
    f.setAmount(amount)
    f.setDistribution(distribution.id)
    f.setDensity(density)
    f.setMonochrome(monochrome)
  }

  def contrast(brightness: Float = 1, contrast: Float = 1): Filter = filter(new ContrastFilter) {
    f ⇒
      f.setBrightness(brightness)
      f.setContrast(contrast)
  }

  def scale(width: Int = 100, height: Int = 100) =
    new ScaleFilter(width, height)

  val invert: Filter = gilter(new InvertFilter)

  object WaveType extends Enumeration {
    type WaveType = Value

    val Sine, Sawtooth, Triangle, Noise = Value

  }

  import WaveType._

  def ripple(xAmp: Float = 5,
             yAmp: Float = 0,
             xWaveLength: Float = 16.0f,
             yWaveLength: Float = 16.0f,
             waveType: WaveType = Sine): Filter = filter(new RippleFilter) { f ⇒
    f.setXAmplitude(xAmp)
    f.setYAmplitude(yAmp)
    f.setXWavelength(xWaveLength)
    f.setYWavelength(yWaveLength)
    f.setWaveType(waveType.id)
  }

  def halftone(mask: BufferedImage,
               softness: Thing,
               invert: Boolean = false,
               monochrome: Boolean = false): Filter =
    filter(new HalftoneFilter) { f ⇒
      f.setSoftness(softness)
      f.setMask(mask)
      f.setInvert(invert)
      f.setMonochrome(monochrome)
    }

  type Thing = Float Refined Closed[W.`0f`.T, W.`1f`.T]
  object Caustic {
    type Scale = Float Refined Closed[W.`1f`.T, W.`300f`.T]
  }

  def caustics(scale: Float Refined Closed[W.`1f`.T, W.`300f`.T],
               brightness: Int Refined Closed[W.`0`.T, W.`1`.T],
               amount: Thing,
               turbulence: Float = 1.0f,
               dispersion: Float = 0.0f,
               time: Float = 0.0f,
               samples: Int = 2,
               bgColor: Color = new Color(0xff799fff)): Filter =
    filter(new CausticsFilter) { f ⇒
      f.setScale(scale)
      f.setBrightness(brightness)
      f.setAmount(amount)
      f.setTurbulence(turbulence)
      f.setDispersion(dispersion)
      f.setTime(time)
      f.setSamples(samples)
      f.setBgColor(bgColor.getRGB)
    }

  def boxblur(
      hRadius: Float = 0.0f,
      vRadius: Float = 0.0f,
      iterations: Int = 1,
      premultiplyAlpha: Boolean = true
  ): Filter = filter(new BoxBlurFilter()) { f ⇒
    f.setHRadius(hRadius)
    f.setVRadius(vRadius)
    f.setIterations(iterations)
    f.setPremultiplyAlpha(premultiplyAlpha)
  }

  val glitch: Filter       = gilter(new GlitchFilter)
  val applyMask: Filter    = gilter(new ApplyMaskFilter)
  val average: Filter      = gilter(new AverageFilter)
  val bicubicScale: Filter = gilter(new BicubicScaleFilter)
  val block: Filter        = gilter(new BlockFilter)
  val blur: Filter         = gilter(new BlurFilter)
  val border: Filter       = gilter(new BorderFilter)
  val boxBlur: Filter      = gilter(new BoxBlurFilter)
  val brushedMetal: Filter = gilter(new BrushedMetalFilter)
  val bump: Filter         = gilter(new BumpFilter)
  val cellular: Filter     = gilter(new CellularFilter)
  val check: Filter        = gilter(new CheckFilter)
  val chromaKey: Filter    = gilter(new ChromaKeyFilter)
  val chrome: Filter       = gilter(new ChromeFilter)
  val circle: Filter       = gilter(new CircleFilter)
//  def composite(a: BufferedImage, b: BufferedImage): Filter =
//    gilter(new CompositeFilter(AlphaComposite.Src.derive(0.5f)))
  val contour: Filter      = gilter(new ContourFilter)
  val contrast: Filter     = gilter(new ContrastFilter)
  val convolve: Filter     = gilter(new ConvolveFilter)
  val crop: Filter         = gilter(new CropFilter)
  val crystallize: Filter  = gilter(new CrystallizeFilter)
  val curl: Filter         = gilter(new CurlFilter)
  val curves: Filter       = gilter(new CurvesFilter)
  val deinterlace: Filter  = gilter(new DeinterlaceFilter)
  val despeckle: Filter    = gilter(new DespeckleFilter)
  val diffuse: Filter      = gilter(new DiffuseFilter)
  val diffusion: Filter    = gilter(new DiffusionFilter)
  val dilate: Filter       = gilter(new DilateFilter)
  val displace: Filter     = gilter(new DisplaceFilter)
  val dissolve: Filter     = gilter(new DissolveFilter)
  val dither: Filter       = gilter(new DitherFilter)
  val doG: Filter          = gilter(new DoGFilter)
  val edge: Filter         = gilter(new EdgeFilter)
  val emboss: Filter       = gilter(new EmbossFilter)
  val equalize: Filter     = gilter(new EqualizeFilter)
  val erodeAlpha: Filter   = gilter(new ErodeAlphaFilter)
  val erode: Filter        = gilter(new ErodeFilter)
  val exposure: Filter     = gilter(new ExposureFilter)
  val fBM: Filter          = gilter(new FBMFilter)
  val fade: Filter         = gilter(new FadeFilter)
  val feedback: Filter     = gilter(new FeedbackFilter)
  val fieldWarp: Filter    = gilter(new FieldWarpFilter)
  val fill: Filter         = gilter(new FillFilter)
  val flare: Filter        = gilter(new FlareFilter)
  val flip: Filter         = gilter(new FlipFilter)
  val flush3D: Filter      = gilter(new Flush3DFilter)
  val fourColor: Filter    = gilter(new FourColorFilter)
  val gain: Filter         = gilter(new GainFilter)
  val gamma: Filter        = gilter(new GammaFilter)
  val gaussian: Filter     = gilter(new GaussianFilter)
  val glint: Filter        = gilter(new GlintFilter)
  val glow: Filter         = gilter(new GlowFilter)
  val gradientWipe: Filter = gilter(new GradientWipeFilter)
  val gray: Filter         = gilter(new GrayFilter)
  val grayscale: Filter    = gilter(new GrayscaleFilter)
  val hSBAdjust: Filter    = gilter(new HSBAdjustFilter)
  val halftone: Filter     = gilter(new HalftoneFilter)
  val highPass: Filter     = gilter(new HighPassFilter)
  val interpolate: Filter  = gilter(new InterpolateFilter)
  val invertAlpha: Filter  = gilter(new InvertAlphaFilter)
  val javaLnF: Filter      = gilter(new JavaLnFFilter)
  val kaleidoscope: Filter = gilter(new KaleidoscopeFilter)
  val key: Filter          = gilter(new KeyFilter)
  val laplace: Filter      = gilter(new LaplaceFilter)
  val lensBlur: Filter     = gilter(new LensBlurFilter)
  val levels: Filter       = gilter(new LevelsFilter)
  val life: Filter         = gilter(new LifeFilter)
  val light: Filter        = gilter(new LightFilter)
  val lookup: Filter       = gilter(new LookupFilter)
  val mapColors: Filter    = gilter(new MapColorsFilter)

  def map(xf: (Float, Float) => Float, yf: (Float, Float) => Float): Filter =
    filter(new MapFilter) { f ⇒
      class F(f: (Float, Float) => Float) extends Function2D {
        override def evaluate(x: Float, y: Float): Float = f(x, y)
      }
      f.setXMapFunction(new F(xf))
      f.setYMapFunction(new F(yf))
    }

  val marble: Filter       = gilter(new MarbleFilter)
  val marbleTex: Filter    = gilter(new MarbleTexFilter)
  val mask: Filter         = gilter(new MaskFilter)
  val maximum: Filter      = gilter(new MaximumFilter)
  val median: Filter       = gilter(new MedianFilter)
  val minimum: Filter      = gilter(new MinimumFilter)
  val mirror: Filter       = gilter(new MirrorFilter)
  val motionBlur: Filter   = gilter(new MotionBlurFilter)
  val motionBlurOp: Filter = gilter(new MotionBlurOp)
  val noise: Filter        = gilter(new NoiseFilter)
  val offset: Filter       = gilter(new OffsetFilter)
  val oil: Filter          = gilter(new OilFilter)
  val opacity: Filter      = gilter(new OpacityFilter)
  val outline: Filter      = gilter(new OutlineFilter)
  val perspective: Filter  = gilter(new PerspectiveFilter)
  val pinch: Filter        = gilter(new PinchFilter)
  val plasma: Filter       = gilter(new PlasmaFilter)
  val pointillize: Filter  = gilter(new PointillizeFilter)
  val polar: Filter        = gilter(new PolarFilter)
  val posterize: Filter    = gilter(new PosterizeFilter)
  val premultiply: Filter  = gilter(new PremultiplyFilter)
  val quantize: Filter     = gilter(new QuantizeFilter)
  val quilt: Filter        = gilter(new QuiltFilter)
  val rGBAdjust: Filter    = gilter(new RGBAdjustFilter)

  //TODO def rays = new RaysFilter

  val reduceNoise: Filter   = gilter(new ReduceNoiseFilter)
  val renderText: Filter    = gilter(new RenderTextFilter)
  val rescale: Filter       = gilter(new RescaleFilter)
  val ripple: Filter        = gilter(new RippleFilter)
  val rotate: Filter        = gilter(new RotateFilter)
  val saturation: Filter    = gilter(new SaturationFilter)
  val scale: Filter         = gilter(new ScaleFilter)
  val scratch: Filter       = gilter(new ScratchFilter)
  val shade: Filter         = gilter(new ShadeFilter)
  val shadow: Filter        = gilter(new ShadowFilter)
  val shape: Filter         = gilter(new ShapeFilter)
  val sharpen: Filter       = gilter(new SharpenFilter)
  val shatter: Filter       = gilter(new ShatterFilter)
  val shear: Filter         = gilter(new ShearFilter)
  val shine: Filter         = gilter(new ShineFilter)
  val skeleton: Filter      = gilter(new SkeletonFilter)
  val sky: Filter           = gilter(new SkyFilter)
  val smartBlur: Filter     = gilter(new SmartBlurFilter)
  val smear: Filter         = gilter(new SmearFilter)
  val solarize: Filter      = gilter(new SolarizeFilter)
  val sparkle: Filter       = gilter(new SparkleFilter)
  val sphere: Filter        = gilter(new SphereFilter)
  val stamp: Filter         = gilter(new StampFilter)
  val swim: Filter          = gilter(new SwimFilter)
  val swizzle: Filter       = gilter(new SwizzleFilter)
  val texture: Filter       = gilter(new TextureFilter)
  val threshold: Filter     = gilter(new ThresholdFilter)
  val tileImage: Filter     = gilter(new TileImageFilter)
  val tritone: Filter       = gilter(new TritoneFilter)
  val twirl: Filter         = gilter(new TwirlFilter)
  val unpremultiply: Filter = gilter(new UnpremultiplyFilter)
  val unsharp: Filter       = gilter(new UnsharpFilter)
  val variableBlur: Filter  = gilter(new VariableBlurFilter)
  val warp: Filter          = gilter(new WarpFilter)
  val water: Filter         = gilter(new WaterFilter)
  val weave: Filter         = gilter(new WeaveFilter)
  val wood: Filter          = gilter(new WoodFilter)

}
