package map

object Main extends App {
  ConfigReader.read(args) map MapDownloader.download
}




