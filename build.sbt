name := "map-merge"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
  Resolver.sonatypeRepo("public")
)


libraryDependencies ++= Seq(
  "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.0",
  "com.sksamuel.scrimage" %% "scrimage-io-extra" % "2.1.0",
  "com.sksamuel.scrimage" %% "scrimage-filters" % "2.1.0",
  "com.github.pathikrit" %% "better-files" % "2.16.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4-SNAPSHOT",
  "io.spray" %% "spray-can" % "1.3.3",
  "org.scalaz" %% "scalaz-core" % "7.2.2",
  "org.scalaz" %% "scalaz-effect" % "7.2.2",
  "org.scalaz" %% "scalaz-concurrent" % "7.2.2",
  "org.scalaz" %% "scalaz-typelevel" % "7.1.8",
  "com.github.scopt" %% "scopt" % "3.4.0"
)

assemblyJarName in assembly := "map-merge.jar"