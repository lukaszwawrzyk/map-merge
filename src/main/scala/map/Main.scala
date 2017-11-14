package map

object Main extends App {
  ConfigReader.read(args) foreach MapDownloader.download
}




