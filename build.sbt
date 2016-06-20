import com.typesafe.sbt.SbtNativePackager._

packageArchetype.java_application
name := "personalsite"
organization := "com.mikeki"
version := "0.0.1"
scalaVersion := "2.11.8"
fork in run := true
parallelExecution in ThisBuild := false

lazy val versions = new {
  val finatra = "2.1.6"
  val guice = "4.0"
  val logback = "1.1.7"
  val finagleMetrics = "0.0.2"
  val bijectionUtil = "0.9.2"
  val twitter4s = "0.1"
}

resolvers ++= Seq(
  "Twitter" at "https://maven.twttr.com"
)

assemblyMergeStrategy in assembly := {
  case "BUILD" => MergeStrategy.discard
  case other => MergeStrategy.defaultMergeStrategy(other)
}

libraryDependencies ++= Seq(
  "com.twitter.finatra" %% "finatra-http" % versions.finatra,
  "com.twitter.finatra" %% "finatra-httpclient" % versions.finatra,
  "ch.qos.logback" % "logback-classic" % versions.logback,
  "com.github.rlazoti" %% "finagle-metrics" % versions.finagleMetrics,
  "com.twitter" %% "bijection-util" % versions.bijectionUtil,
  "com.danielasfregola" %% "twitter4s" % versions.twitter4s
)
