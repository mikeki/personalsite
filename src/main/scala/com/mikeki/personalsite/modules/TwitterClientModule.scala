package com.mikeki.personalsite.modules

import com.danielasfregola.twitter4s.TwitterClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}
import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule

object MissingTwitterConfigException extends UnsupportedOperationException

object TwitterClientModule extends TwitterModule {

  @Singleton
  @Provides
  def providesTwitterClient: TwitterClient = {
    val consumerToken = ConsumerToken(
      key = getFromEnv("TWITTER_CONSUMER_KEY"),
      secret = getFromEnv("TWITTER_CONSUMER_SECRET")
    )
    val accessToken = AccessToken(
      key = getFromEnv("TWITTER_ACCESS_KEY"),
      secret = getFromEnv("TWITTER_ACCESS_SECRET")
    )
    new TwitterClient(consumerToken, accessToken)
  }

  private def getFromEnv(key: String): String = {
    scala.util.Properties.envOrNone(key).getOrElse {
      throw MissingTwitterConfigException
    }
  }
}
