/**
  * Copyright Homeaway, Inc 2016-Present. All Rights Reserved.
  * No unauthorized use of this software.
  */
package stuff

import java.awt.image.{BufferedImage, BufferedImageOp}
import java.awt.{AlphaComposite, Color}

import com.jhlabs.image._
import com.jhlabs.math.Function2D

object Filters extends WithGradient {

  def channelMix = new ChannelMixFilter

  def colourhalftone(
      dotRadius: Float = 2,
      cyanScreenAngle: Float = Math.toRadians(108).toFloat,
      magentaScreenAngle: Float = Math.toRadians(162).toFloat,
      yellowScreenAngle: Float = Math.toRadians(90).toFloat
  ): ColorHalftoneFilter = {
    val f = new ColorHalftoneFilter
    f.setdotRadius(dotRadius)
    f.setCyanScreenAngle(cyanScreenAngle)
    f.setMagentaScreenAngle(magentaScreenAngle)
    f.setYellowScreenAngle(yellowScreenAngle)
    f
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
  ): NoiseFilter = {
    val f = new NoiseFilter
    f.setAmount(amount)
    f.setDistribution(distribution.id)
    f.setDensity(density)
    f.setMonochrome(monochrome)
    f
  }

  def contrast(brightness: Float = 1, contrast: Float = 1): ContrastFilter = {
    val f = new ContrastFilter
    f.setBrightness(brightness)
    f.setContrast(contrast)
    f
  }

  def scale(width: Int = 100, height: Int = 100) =
    new ScaleFilter(width, height)

  val invert = new InvertFilter

  object WaveType extends Enumeration {
    type WaveType = Value

    val Sine, Sawtooth, Triangle, Noise = Value

  }

  import WaveType._

  def ripple(xAmp: Float = 5,
             yAmp: Float = 0,
             xWaveLength: Float = 16.0f,
             yWaveLength: Float = 16.0f,
             waveType: WaveType = Sine): RippleFilter = {
    val f = new RippleFilter
    f.setXAmplitude(xAmp)
    f.setYAmplitude(yAmp)
    f.setXWavelength(xWaveLength)
    f.setYWavelength(yWaveLength)
    f.setWaveType(waveType.id)
    f
  }

  def halftone(mask: BufferedImage,
               softness: Float = 0.1f,
               invert: Boolean = false,
               monochrome: Boolean = false): HalftoneFilter = {
    val f = new HalftoneFilter
    f.setSoftness(softness)
    f.setMask(mask)
    f.setInvert(invert)
    f.setMonochrome(monochrome)
    f
  }

  def caustics(scale: Float = 32,
               brightness: Int = 10,
               amount: Float = 1.0f,
               turbulence: Float = 1.0f,
               dispersion: Float = 0.0f,
               time: Float = 0.0f,
               samples: Int = 2,
               bgColor: Color = new Color(0xff799fff)): CausticsFilter = {
    val f = new CausticsFilter
    f.setScale(scale)
    f.setBrightness(brightness)
    f.setAmount(amount)
    f.setTurbulence(turbulence)
    f.setDispersion(dispersion)
    f.setTime(time)
    f.setSamples(samples)
    f.setBgColor(bgColor.getRGB)
    f
  }

  def boxblur(
      hRadius: Float = 0.0f,
      vRadius: Float = 0.0f,
      iterations: Int = 1,
      premultiplyAlpha: Boolean = true
  ): BoxBlurFilter = {
    val f = new BoxBlurFilter()
    f.setHRadius(hRadius)
    f.setVRadius(vRadius)
    f.setIterations(iterations)
    f.setPremultiplyAlpha(premultiplyAlpha)
    f
  }

  def glitch: BufferedImageOp = new GlitchFilter

  def applyMask = new ApplyMaskFilter

  def average = new AverageFilter

  def bicubicScale = new BicubicScaleFilter

  def block = new BlockFilter

  def blur = new BlurFilter

  def border = new BorderFilter

  def boxBlur = new BoxBlurFilter

  def brushedMetal = new BrushedMetalFilter

  def bump = new BumpFilter

  def caustics = new CausticsFilter

  def cellular = new CellularFilter

  def check = new CheckFilter

  def chromaKey = new ChromaKeyFilter

  def chrome = new ChromeFilter

  def circle = new CircleFilter

  def composite(a: BufferedImage, b: BufferedImage) =
    new CompositeFilter(AlphaComposite.Src.derive(0.5f))

  def contour = new ContourFilter

  def contrast = new ContrastFilter

  def convolve = new ConvolveFilter

  def crop = new CropFilter

  def crystallize = new CrystallizeFilter

  def curl = new CurlFilter

  def curves = new CurvesFilter

  def deinterlace = new DeinterlaceFilter

  def despeckle = new DespeckleFilter

  def diffuse = new DiffuseFilter

  def diffusion = new DiffusionFilter

  def dilate = new DilateFilter

  def displace = new DisplaceFilter

  def dissolve = new DissolveFilter

  def dither = new DitherFilter

  def doG = new DoGFilter

  def edge = new EdgeFilter

  def emboss = new EmbossFilter

  def equalize = new EqualizeFilter

  def erodeAlpha = new ErodeAlphaFilter

  def erode = new ErodeFilter

  def exposure = new ExposureFilter

  def fBM = new FBMFilter

  def fade = new FadeFilter

  def feedback = new FeedbackFilter

  def fieldWarp = new FieldWarpFilter

  def fill = new FillFilter

  def flare = new FlareFilter

  def flip = new FlipFilter

  def flush3D = new Flush3DFilter

  def fourColor = new FourColorFilter

  def gain = new GainFilter

  def gamma = new GammaFilter

  def gaussian = new GaussianFilter

  def glint = new GlintFilter

  def glow = new GlowFilter

  def gradientWipe = new GradientWipeFilter

  def gray = new GrayFilter

  def grayscale = new GrayscaleFilter

  def hSBAdjust = new HSBAdjustFilter

  def halftone = new HalftoneFilter

  def highPass = new HighPassFilter

  def interpolate = new InterpolateFilter

  def invertAlpha = new InvertAlphaFilter

  def javaLnF = new JavaLnFFilter

  def kaleidoscope = new KaleidoscopeFilter

  def key = new KeyFilter

  def laplace = new LaplaceFilter

  def lensBlur = new LensBlurFilter

  def levels = new LevelsFilter

  def life = new LifeFilter

  def light = new LightFilter

  def lookup = new LookupFilter

  def mapColors = new MapColorsFilter

  def map(xf: (Float, Float) => Float,
          yf: (Float, Float) => Float): MapFilter = {
    val f = new MapFilter
    class F(f: (Float, Float) => Float) extends Function2D {
      override def evaluate(x: Float, y: Float): Float = f(x, y)
    }
    f.setXMapFunction(new F(xf))
    f.setYMapFunction(new F(yf))
    f
  }

  def marble = new MarbleFilter

  def marbleTex = new MarbleTexFilter

  def mask = new MaskFilter

  def maximum = new MaximumFilter

  def median = new MedianFilter

  def minimum = new MinimumFilter

  def mirror = new MirrorFilter

  def motionBlur = new MotionBlurFilter

  def motionBlurOp = new MotionBlurOp

  def noise = new NoiseFilter

  def offset = new OffsetFilter

  def oil = new OilFilter

  def opacity = new OpacityFilter

  def outline = new OutlineFilter

  def perspective = new PerspectiveFilter

  def pinch = new PinchFilter

  def plasma = new PlasmaFilter

  def pointillize = new PointillizeFilter

  def polar = new PolarFilter

  def posterize = new PosterizeFilter

  def premultiply = new PremultiplyFilter

  def quantize = new QuantizeFilter

  def quilt = new QuiltFilter

  def rGBAdjust = new RGBAdjustFilter

  //TODO def rays = new RaysFilter

  def reduceNoise = new ReduceNoiseFilter

  def renderText = new RenderTextFilter

  def rescale = new RescaleFilter

  def ripple = new RippleFilter

  def rotate = new RotateFilter

  def saturation = new SaturationFilter

  def scale = new ScaleFilter

  def scratch = new ScratchFilter

  def shade = new ShadeFilter

  def shadow = new ShadowFilter

  def shape = new ShapeFilter

  def sharpen = new SharpenFilter

  def shatter = new ShatterFilter

  def shear = new ShearFilter

  def shine = new ShineFilter

  def skeleton = new SkeletonFilter

  def sky = new SkyFilter

  def smartBlur = new SmartBlurFilter

  def smear = new SmearFilter

  def solarize = new SolarizeFilter

  def sparkle = new SparkleFilter

  def sphere = new SphereFilter

  def stamp = new StampFilter

  def swim = new SwimFilter

  def swizzle = new SwizzleFilter

  def texture = new TextureFilter

  def threshold = new ThresholdFilter

  def tileImage = new TileImageFilter

  def tritone = new TritoneFilter

  def twirl = new TwirlFilter

  def unpremultiply = new UnpremultiplyFilter

  def unsharp = new UnsharpFilter

  def variableBlur = new VariableBlurFilter

  def warp = new WarpFilter

  def water = new WaterFilter

  def weave = new WeaveFilter

  def wood = new WoodFilter

}
