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

  get("/") { request: Request =>
    val twitterHandle = scala.util.Properties.envOrNone("TWITTER_HANDLE").getOrElse {
      throw MissingTwitterHandle
    }
    val description = scala.util.Properties.envOrNone("DESCRIPTION")
    val location = scala.util.Properties.envOrNone("LOCATION")

    twitterClient.getUser(twitterHandle).as[Future[User]].map { user =>
      HomeView(
        user.name,
        user.id,
        user.profile_image_url_https.replace("normal", "400x400"),
        user.profile_image_url_https.replace("normal", "mini"),
        user.profile_banner_url.map(_ + "/1500x500"),
        description.orElse(user.description),
        location.orElse(user.location),
        twitterHandle,
        scala.util.Properties.envOrNone("EMAIL"),
        scala.util.Properties.envOrNone("FACEBOOK_HANDLE"),
        scala.util.Properties.envOrNone("GITHUB_HANDLE"),
        scala.util.Properties.envOrNone("LINKEDIN_HANDLE")
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
  favicon_url: String,
  banner_url: Option[String],
  description: Option[String],
  location: Option[String],
  twitter: String,
  email: Option[String],
  facebook: Option[String],
  github: Option[String],
  linked_in: Option[String]
)

object MissingTwitterHandle extends UnsupportedOperationException
