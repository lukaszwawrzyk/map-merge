package map

import java.util.Locale

import akka.actor.ActorSystem
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.sksamuel.scrimage.Image
import spray.can.Http
import spray.http.HttpMethods.GET
import spray.http.{ HttpRequest, HttpResponse, Uri }

import scala.concurrent.Future
import scala.concurrent.duration._

object MapView {
  private val WatermarkSize = 44
  private implicit val DownloadTimeout: Timeout = Timeout(30.minutes)
}

class MapView(system: ActorSystem, zoom: Int, mapType: String, apiKey: String) {

  import system.dispatcher

  val lngOffset: Double = zoom match {
    case 20 => 0.0008
    case 18 => 0.00322
    case 16 => 0.01285
    case _  => 0.0
  }
  val latOffset: Double = zoom match {
    case 20 => 0.000518
    case 18 => 0.002065
    case 16 => 0.008250
    case _  => 0.0
  }

  def download(location: Location): Future[Image] = {
    val url = {
      ("https://maps.googleapis.com/maps/api/staticmap" +
        "?center=%1.8f,%1.8f" +
        "&zoom=%d" +
        "&size=600x622" +
        "&scale=2" +
        "&maptype=%s" +
        "&format=png" +
        "&key=%s")
        .formatLocal(
          Locale.US,
          location.latitude, location.longitude, zoom, mapType, apiKey
        )
    }
    import MapView.DownloadTimeout
    (IO(Http)(system) ? HttpRequest(GET, Uri(url))).mapTo[HttpResponse]
      .map(_.entity.data.toByteArray)
      .map(Image.apply)
      .map(trim)
  }

  private def trim(image: Image): Image = {
    println("trimming")
    image.trim(0, 0, 0, MapView.WatermarkSize)
  }

}
