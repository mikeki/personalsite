package com.mikeki.personalsite

import com.mikeki.personalsite.controllers.PersonalSiteController
import com.mikeki.personalsite.modules.TwitterClientModule
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.modules.MustacheModule
import com.twitter.finatra.http.routing.HttpRouter

object PersonalSiteServerMain extends PersonalSiteServer

class PersonalSiteServer extends HttpServer {

  /*
   * Since Heroku only supports a single port per service,
   * we disable the Admin HTTP Server
   */
  override val disableAdminHttpServer = true

  override val modules = Seq(
    TwitterClientModule,
    MustacheModule
  )

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PersonalSiteController]
  }
}
