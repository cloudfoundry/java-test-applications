# Java Test Applications

A collection of applications used for testing the Java buildpack.

## Building

This project is built with Gradle. To build the artifacts:

	./gradlew

This will produce a compressed artifacts in `<project>/build/libs` and exploded artifacts in `<project>/build/exploded`.

Note that the Play application uses the sbt build system and must be built separately as described below.

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

## `play-application`

This is a standard sample application which was created by issuing:

    play new play-application

To build and deploy this application, download and install the Play framework from [http://www.playframework.org](http://www.playframework.org),
then change directory to `play-application` and start the Play console:

    play

Compile the application and create a standalone distribution:

    [play-application] $ compile
    ...
    [play-application] $ dist

The Play console may then be exited using `Ctrl-D`.

The packaged application is placed in `play-application/dist/play-application-1.0-SNAPSHOT.zip` but needs to be unzipped before being
deployed to Cloud Foundry.
