import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play-application"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    javaJpa,
    "org.springframework"    %    "spring-context"    %    "3.2.3.RELEASE",
    "org.springframework"    %    "spring-core"       %    "3.2.3.RELEASE",
    "org.springframework"    %    "spring-beans"      %    "3.2.3.RELEASE"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )

}
