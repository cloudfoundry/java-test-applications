name := "play-application"

version := "1.0.0.BUILD-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.gopivotal.cloudfoundry.test" % "core" % "1.0.0.BUILD-SNAPSHOT",
  "com.h2database" % "h2" % "1.4.187",
  javaCore,
  javaJdbc,
  "org.springframework" % "spring-jdbc" % "4.2.0.RELEASE",
  "org.springframework.data" % "spring-data-commons" % "1.10.2.RELEASE",
  "org.springframework.data" % "spring-data-redis" % "1.5.2.RELEASE",
  "org.springframework.data" % "spring-data-mongodb" % "1.7.2.RELEASE",
  "org.springframework.amqp" % "spring-amqp" % "1.4.5.RELEASE",
  "org.springframework.amqp" % "spring-rabbit" % "1.4.5.RELEASE"

)

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
