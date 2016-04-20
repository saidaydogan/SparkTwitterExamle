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

//    val filter = new FilterQuery()
//    filter.language("tr")

    val tweetStream = TwitterUtils.createStream(ssc, Utils.getAuth).filter(_.getLang == "tr")
      .map(gson.toJson(_))

    tweetStream.foreachRDD((rdd, time) => {
      val count = rdd.count()
      if (count > 0) {
        val outputRDD = rdd.repartition(1)
        outputRDD.saveAsTextFile(outputDir + "/tweets_" + time.milliseconds.toString)
        numTweetsCollected += count
        if (numTweetsCollected > 25) {
          println(numTweetsCollected + " adet tweet oldu!!")
        }
      }
    })

    ssc.start()
    ssc.awaitTermination()

  }
}