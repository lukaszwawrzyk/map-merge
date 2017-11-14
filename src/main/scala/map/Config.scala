package map

case class Config(
  zoom: Int,
  location: Location,
  radius: Int,
  mapType: String,
  outputPath: String,
  apiKey: String
)

object Config {
  val default = Config(
    zoom = 18,
    location = Location(50.065352, 19.909054),
    radius = 3,
    mapType = "satellite",
    outputPath = "out.jpg",
    apiKey = "changeme"
  )
}

object ConfigReader {

  def read(args: Array[String]): Option[Config] = {
    parser.parse(args, Config.default)
  }

  private val parser = new scopt.OptionParser[Config]("map-merge") {
    (opt[Double]('y', "lat")
      required()
      valueName "<latitude>"
      action { (lat, conf) => conf.copy(location = conf.location.copy(latitude = lat))})

    (opt[Double]('x', "lng")
      required()
      valueName "<longitude>"
      action { (lng, conf) => conf.copy(location = conf.location.copy(longitude = lng))})

    (opt[Int]('z', "zoom")
      action { (zoom, conf) => conf.copy(zoom = zoom) }
      validate { (zoom) => if (Set(16, 18, 20) contains zoom) success else failure("Supported <zoom> values are 16, 18 and 20") }
      text "Zoom value: 20 for closer view or 18 for more general, 16 for far")

    val validMapTypes = Set("roadmap", "satellite", "terrain", "hybrid")

    (opt[String]('t', "type")
      action { (t, conf) => conf.copy(mapType = t) }
      validate { (t) => if (validMapTypes contains t) success else failure(s"Supported <maptype> values are ${validMapTypes mkString ", "}") }
      text s"Type of map, supported values are ${validMapTypes mkString ", "}")

    (opt[Int]('r', "radius")
      action { (r, conf) => conf.copy(radius = r) }
      validate { (rad) => if (rad >= 1) success else failure("Radius has to be greater than 0") }
      text "Size of an image: 1 -> 1200px, 2 -> 2400px, 3 -> 4800px, 4 -> 9600px")

    (opt[String]('o', "out")
      action { (path, conf) => conf.copy(outputPath = path) }
      text "Output file name")

    (opt[String]('k', "api-key")
      action { (key, conf) => conf.copy(apiKey = key) }
      text "Key for google map api")

    help("help") text "prints this usage text"
  }

}