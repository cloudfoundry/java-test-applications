# Java Test Applications

A collection of applications used for testing the Java buildpack.

## Applications

| Name | Output Destination | Description
| ---- | ------ | -----------
`grails-application` | HTTP Response | A Grails application created by issuing `grails create-app`
`java-main` | `System.out` | An application started with `java -jar`. _Since this application does not service web requests, you can select `none` for its subdomain and domain when you deploy it to Cloud Foundry._
`play-application` | HTTP Response | A Play application created by issuing `play new play-application`
`web-application` | HTTP Response | A web application that uses Spring MVC and Servlet 3

### Output Content
All applications output the following content:

* `java.lang:type=Runtime/InputArguments`
* `java.lang:type=Runtime/ClassPath`

## Building

This project is built with Gradle. To build the artifacts, run:

```plain
./gradlew
```

## Deploying to Cloud Foundry

Each test application contains a `manifest.yml` file which allows the built application to be deployed to Cloud Foundry by simply issuing:

```plain
cf push
```

To avoid clashing with the URLs of other applications, you should specify your own subdomain for the application (unless the test application
does not need a subdomain).

## Failure Testing

Failure testing is supported for each of the above applications by setting a suitable environment variable.

If the environment variable FAIL_INIT is set, the application will fail to initialize:

```plain
cf set-env <application name> FAIL_INIT true
```

If the environment variable FAIL_OOM is set, the application will repeatedly exhaust the heap until the JVM is killed:

```plain
cf set-env <application name> FAIL_OOM true
```
