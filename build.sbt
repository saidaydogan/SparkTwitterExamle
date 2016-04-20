name := "TwitterExample"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.1"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.10" % "1.6.1"

libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.1"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.1"

libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.6.1"

libraryDependencies += "com.google.code.gson" % "gson" % "2.6.2"

libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.4"

libraryDependencies += "commons-cli" % "commons-cli" % "1.3.1"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
