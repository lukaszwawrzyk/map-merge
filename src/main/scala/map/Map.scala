package map

import com.sksamuel.scrimage.Image

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.Scalaz._

class Map(val mapView: MapView) {
  private def moveRight(scale: Int) = Location.longitude =>= (_ + (mapView.lngOffset * scale))
  private def moveDown (scale: Int) = Location.latitude  =>= (_ - (mapView.latOffset * scale))

  def createImage(location: Location, radius: Int)(implicit ec: ExecutionContext): Future[Image] = {
    require(radius >= 1)

    def getImageRec(location: Location, scale: Int, step: Int): Future[Image] = {
      println(s"Requested $location, scale=$scale, step=$step")

      if (step == 1) {
        mapView.download(location)
      } else {
        val topLeft = location
        val topRight = moveRight(scale)(topLeft)
        val bottomLeft = moveDown(scale)(topLeft)
        val bottomRight = moveRight(scale)(bottomLeft)
        val get = (loc: Location) => getImageRec(loc, scale / 2, step - 1)

        (get(topLeft) |@| get(topRight) |@| get(bottomLeft) |@| get(bottomRight)) (Merger.merge4)
      }
    }

    getImageRec(location, math.pow(2, radius - 2).toInt, radius)
  }
}
