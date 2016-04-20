package com.saidaydogan.spark.twitterexample

import java.io.File

import com.google.gson.Gson
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import twitter4j.FilterQuery

/**
  * Created by said.aydogan on 19.4.2016.
  */

object Tutorial {
  private var numTweetsCollected = 0L
  private val gson = new Gson()

  def main(args: Array[String]) {

    val sparkHome = System.getenv("SPARK_HOME");
    val outputDir = new File("C:\\TwitterExample")
    outputDir.mkdirs()

    println("Initializing Streaming Spark Context...")
    val conf = new SparkConf().setSparkHome(sparkHome)
      .setAppName("TwitterEXAMPLE").setMaster("local[4]")
    val sc = new SparkContext(conf)

    val ssc = new StreamingContext(sc, Seconds(10))


    val tweetStream = TwitterUtils.createStream(ssc, Utils.getAuth).filter(_.getLang == "tr")
    val users = tweetStream.map(_.getUser)
    val recentUsers = users.map(f=> (f.getName, f.getScreenName)).reduceByKeyAndWindow(_ + _, Seconds(60))

    recentUsers.foreachRDD(rdd => {
      println("\n Son 1 dakika içerisinde yazan kişiler (%s kişi):".format(rdd.count()))
      rdd.foreach{
        case (user, tag) => println("%s <-> %s".format(user,tag))

      }
    })


//    val tweets = tweetStream.map(gson.toJson(_))
//
//    tweets.foreachRDD((rdd, time) => {
//      val count = rdd.count()
//      if (count > 0) {
//        val outputRDD = rdd.repartition(1)
//        outputRDD.saveAsTextFile(outputDir + "/tweets_" + time.milliseconds.toString)
//        numTweetsCollected += count
//        if (numTweetsCollected > 25) {
//          println(numTweetsCollected + " adet tweet oldu!!")
//        }
//      }
//    })

    ssc.start()
    ssc.awaitTermination()

  }
}