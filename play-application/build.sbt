name := "play-application"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.gopivotal.cloudfoundry.test" % "core" % "1.0.0.BUILD-SNAPSHOT",
  "com.h2database" % "h2" % "1.3.174",
  javaCore,
  "mysql" % "mysql-connector-java" % "5.0.5",
  "org.springframework" % "spring-context" % "3.2.4.RELEASE",
  "org.springframework" % "spring-jdbc" % "3.2.4.RELEASE"
)

play.Project.playJavaSettings
