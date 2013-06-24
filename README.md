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

If the environment variable FAIL_OOM is set, the application will repeatedly exhaust the heap until the JVM is killed:

    cf set-env <application name> FAIL_OOM true

## `web-application`
The `web-application` application is typical of applications that use Spring MVC and Servlet 3.  It has the following characteristics:

* Outputs `java.lang:type=Runtime/InputArguments` to the HTTP response

If the environment variable FAIL_INIT is set, the application will fail to initialise:

    cf set-env <application name> FAIL_INIT true
