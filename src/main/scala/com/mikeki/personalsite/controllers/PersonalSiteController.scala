package com.mikeki.personalsite.controllers

import com.danielasfregola.twitter4s.TwitterClient
import com.danielasfregola.twitter4s.entities.User
import com.google.inject.{Inject, Singleton}
import com.twitter.bijection.Conversion._
import com.twitter.bijection.twitter_util.UtilBijections.twitter2ScalaFuture
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.response.Mustache
import com.twitter.util.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class PersonalSiteController @Inject()(
  twitterClient: TwitterClient
) extends Controller {

  val TwitterUser = "MiguelCervera"

  get("/") { request: Request =>
    twitterClient.getUser(TwitterUser).as[Future[User]].map { user =>
      HomeView(
        user.name,
        user.id,
        user.profile_image_url_https.replace("normal", "400x400"),
        user.profile_banner_url.getOrElse("")
      )
    }
  }

  get("/:*") { request: Request =>
    response.ok.file(request.params("*"))
  }
}

@Mustache("home")
case class HomeView(
  name: String,
  twitter_id: Long,
  avatar_url: String,
  banner_url: String
)
