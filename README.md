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

## `web-application`
The `web-application` application is typical of applications that use Spring MVC and Servlet 3.  It has the following characteristics:

* Outputs `java.lang:type=Runtime/InputArguments` to the HTTP response

## `play-application`

This is a based on a sample Play Java application created by issuing:

    play new play-application

It has the following characteristics:

* Outputs `java.lang:type=Runtime/InputArguments` to the HTTP response
	
To build and deploy this application, download and install the Play framework from [http://www.playframework.org](http://www.playframework.org),
then change directory to `play-application` and issue:

    play dist

The packaged application is placed in `play-application/dist/play-application-1.0-SNAPSHOT.zip`.

## Deploying to Cloud Foundry

Each test application contains a `manifest.yml` file which allows the built application to be deployed to Cloud Foundry by simply issuing:

    cf push

To avoid clashing with the URLs of other applications, you should specify your own subdomain for the application.

## Failure Testing

Failure testing is supported for each of the above applications by setting a suitable environment variable.

If the environment variable FAIL_INIT is set, the application will fail to initialise:

    cf set-env <application name> FAIL_INIT true

If the environment variable FAIL_OOM is set, the application will repeatedly exhaust the heap until the JVM is killed:

    cf set-env <application name> FAIL_OOM true