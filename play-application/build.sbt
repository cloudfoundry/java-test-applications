name := "play-application"

version := "1.0.0.BUILD-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.gopivotal.cloudfoundry.test" % "core" % "1.0.0.BUILD-SNAPSHOT",
  "com.h2database" % "h2" % "1.4.187",
  javaCore,
  javaJdbc,
  "org.springframework" % "spring-context" % "4.1.6.RELEASE",
  "org.springframework" % "spring-expression" % "4.1.6.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.1.6.RELEASE",
  "org.springframework" % "spring-context" % "4.1.6.RELEASE",
  "org.springframework" % "spring-expression" % "4.1.6.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.1.6.RELEASE",
  "org.springframework" % "spring-web" % "4.1.6.RELEASE"
)

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
