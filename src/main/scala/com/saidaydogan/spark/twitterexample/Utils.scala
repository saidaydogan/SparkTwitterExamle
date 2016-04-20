package com.saidaydogan.spark.twitterexample
import org.apache.commons.cli.{Options, ParseException, PosixParser}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.feature.HashingTF
import twitter4j.auth.OAuthAuthorization
import twitter4j.conf.ConfigurationBuilder

/**
  * Created by said.aydogan on 19.4.2016.
  */


object Utils {

  val numFeatures = 1000
  val tf = new HashingTF(numFeatures)


  def getAuth = {
    val tconf = new ConfigurationBuilder().setOAuthConsumerKey("setOAuthConsumerKey")
      .setOAuthConsumerSecret("setOAuthConsumerSecret")
      .setOAuthAccessToken("setOAuthAccessToken")
      .setOAuthAccessTokenSecret("setOAuthAccessTokenSecret")
      .build()

    Some(new OAuthAuthorization(tconf))


//    Some(new OAuthAuthorization(new ConfigurationBuilder().build()))
  }

  /**
    * Create feature vectors by turning each tweet into bigrams of characters (an n-gram model)
    * and then hashing those to a length-1000 feature vector that we can pass to MLlib.
    * This is a common way to decrease the number of features in a model while still
    * getting excellent accuracy (otherwise every pair of Unicode characters would
    * potentially be a feature).
    */
  def featurize(s: String): Vector = {
    tf.transform(s.sliding(2).toSeq)
  }

  object IntParam {
    def unapply(str: String): Option[Int] = {
      try {
        Some(str.toInt)
      } catch {
        case e: NumberFormatException => None
      }
    }
  }
}