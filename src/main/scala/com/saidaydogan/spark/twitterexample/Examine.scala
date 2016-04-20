package com.saidaydogan.spark.twitterexample

import com.google.gson.{GsonBuilder, JsonParser}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by said.aydogan on 20.4.2016.
  */
object Examine {

  val jsonParser = new JsonParser()
  val gson = new GsonBuilder().setPrettyPrinting().create()

  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setAppName("TwitterEXAMPLE - Examine").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    val tweetDir = "C:\\TwitterExample"


    val tweets = sc.textFile("C:\\TwitterExample\\tweets_1461133450000\\part-00000")
    println("------------Sample JSON Tweets-------")
    for (tweet <- tweets.take(5)) {
      println(gson.toJson(jsonParser.parse(tweet)))
    }


  }
}
