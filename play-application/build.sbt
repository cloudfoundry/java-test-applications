name := "play-application"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaCore,
  "org.springframework" % "spring-context" % "4.0.0.RC1",
  "com.gopivotal.cloudfoundry.test" % "core" % "1.0.0.BUILD-SNAPSHOT"
)

play.Project.playJavaSettings
