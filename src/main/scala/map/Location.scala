package map

import java.nio.DoubleBuffer

import scalaz.Lens

case class Location(latitude: Double, longitude: Double)

object Location {
  val fun: (Location, Double) => Location = (x, y) => x.copy(latitude = y)
  val fu2: (Location, Double) => Location = (x, y) => x.copy(longitude = y)

  val latitude = Lens.lensu[Location, Double](fun, _.latitude)
  val longitude = Lens.lensu[Location, Double](fu2, _.longitude
  )
}
