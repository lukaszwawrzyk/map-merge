package map

import scalaz.Lens

case class Location(latitude: Double, longitude: Double)

object Location {
  val latitude: Lens[Location, Double] = Lens.lensu[Location, Double](
    (x, y) => x.copy(latitude = y),
    _.latitude
  )
  val longitude: Lens[Location, Double] = Lens.lensu[Location, Double](
    (x, y) => x.copy(longitude = y),
    _.longitude
  )
}
