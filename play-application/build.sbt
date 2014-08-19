name := "play-application"

version := "1.0.0.BUILD-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.gopivotal.cloudfoundry.test" % "core" % "1.0.0.BUILD-SNAPSHOT",
  "com.h2database" % "h2" % "1.3.175",
  javaCore,
  javaJdbc,
  "org.springframework" % "spring-context" % "4.0.5.RELEASE",
  "org.springframework" % "spring-expression" % "4.0.5.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.0.5.RELEASE",
  "org.springframework" % "spring-context" % "4.0.5.RELEASE",
  "org.springframework" % "spring-expression" % "4.0.5.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.0.5.RELEASE",
  "org.springframework" % "spring-web" % "4.0.5.RELEASE"
)

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

play.Project.playJavaSettings
