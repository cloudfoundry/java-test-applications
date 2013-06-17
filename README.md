# Java Test Applications

A collection of applications used for testing the Java buildpack.

## Building

This project is built with Gradle. To build the artifacts:

	./gradlew

This will produce a compressed artifacts in `<project>/build/libs` and exploded artifacts in `<project>/build/exploded`.

## `java-main`
The `java-main` application is typical of applications that would be started with `java -jar`.  It has the following characteristics:

* Specifies a `java.main.class` system property
* Specifies a `java.opts` system property
* Outputs `java.lang:type=Runtime/InputArguments` to `System.out`


## `web-application`
The `web-application` application is typical of applications that use Spring MVC and Servlet 3.  It has the following characteristics:

* Outputs `java.lang:type=Runtime/InputArguments` to the HTTP response
