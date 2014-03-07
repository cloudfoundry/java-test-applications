name := "play-application"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.gopivotal.cloudfoundry.test" % "core" % "1.0.0.BUILD-SNAPSHOT",
  "com.h2database" % "h2" % "1.3.174",
  javaCore,
  javaJdbc,
  "org.springframework" % "spring-context" % "4.0.1.RELEASE",
  "org.springframework" % "spring-expression" % "4.0.1.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.0.1.RELEASE",
  "org.springframework" % "spring-context" % "4.0.1.RELEASE",
  "org.springframework" % "spring-expression" % "4.0.1.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.0.1.RELEASE"
)

play.Project.playJavaSettings

resolvers += "Spring Milestone" at "http://repo.spring.io/milestone"

resolvers += "Spring Snapshot" at "http://repo.spring.io/snapshot"
