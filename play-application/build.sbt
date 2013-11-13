name := "play-application"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.gopivotal.cloudfoundry.test" % "core" % "1.0.0.BUILD-SNAPSHOT",
  "com.h2database" % "h2" % "1.3.174",
  javaCore,
  javaJdbc,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "org.springframework" % "spring-context" % "4.0.0.RC1",
  "org.springframework" % "spring-expression" % "4.0.0.RC1",
  "org.springframework" % "spring-jdbc" % "4.0.0.RC1"
)

play.Project.playJavaSettings

resolvers += "Spring Milestone" at "http://repo.spring.io/milestone"

resolvers += "Spring Snapshot" at "http://repo.spring.io/snapshot"
