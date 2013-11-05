name := "play-application"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaCore,
  "org.springframework" % "spring-context" % "3.2.4.RELEASE",
  "com.gopivotal.cloudfoundry.test" % "core" % "1.0.0.BUILD-SNAPSHOT"
)

play.Project.playJavaSettings
