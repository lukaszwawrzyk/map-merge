package map

import akka.actor.{ ActorSystem, Terminated }
import com.sksamuel.scrimage.Image
import com.sksamuel.scrimage.nio.JpegWriter

import scalaz.Scalaz._
import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }

object MapDownloader {
  def download(config: Config): Future[Terminated] = {

    def save(image: Image) = {
      println("saving")
      image.output(config.outputPath)(JpegWriter().withCompression(90))
      println("saved")
    }

    import scala.concurrent.ExecutionContext.Implicits.global
    implicit val system = ActorSystem()

    val mapView = new MapView(system, config.zoom, config.mapType)
    val map = new Map(mapView)

    val saved = map.createImage(config.location, config.radius) map save

    Await.ready(saved >> system.terminate(), 1.day)
  }
}
