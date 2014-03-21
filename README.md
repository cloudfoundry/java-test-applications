# Java Test Applications
[![Build Status](https://travis-ci.org/cloudfoundry/java-test-applications.svg?branch=master)](https://travis-ci.org/cloudfoundry/java-test-applications)

A collection of applications used for testing the Java buildpack.

## Applications

| Name | Description
| ---- | -----------
`grails-application` | A Grails application, deployed as a WAR
`groovy-application` | An application started with `groovy`
`java-main-application` | A Spring Boot application started with `java -jar`
`play-application` | A Play Framework application, deployed as a `dist`
`ratpack-application` | A Ratpack application, deployed as a `distZip`
`spring-boot-cli-application` | A Spring Boot CLI application, deployed with `spring grab`
`spring-boot-cli-jar-application` | A Spring Boot CLI application, deployed with `spring jar`
`web-application` | A Spring MVC application using Servlet 3
`web-servlet-2-application` | A Spring MVC application using Servlet 2

### Output Content
All applications support the following REST operations:

| URI | Description
| --- | -----------
`/` | The health of the application
`/class-path` | The classpath of the application
`/environment-variables` | The environment variables available to the application
`/input-arguments` | The list of JVM input arguments for the application
`/system-properties` | The system properties available to the application
`/datasource/check-access` | The ability of the application to access a RDBMS
`/datasource-url` | The URL of the application's `DataSource`
`/redis/check-access` | The ability of the application to access Redis
`/redis/url` | The URL of the application's Redis
`/mongodb/check-access` | The ability of the application to access MongoDB
`/mongodb/url` | The URL of the application's MongoDB
`/rabbit/check-access` | The ability of the application to access RabbitMQ
`/rabbit/url` | The URL of the application's RabbitMQ

## Building

This project is built with JDK 7 and Gradle. To build the artifacts, install JDK 7, Play, and Grails, install Spring Boot by following [these instructions](https://github.com/spring-projects/spring-boot), and then run:

```plain
./gradlew
```

## Deploying to Cloud Foundry

Each test application contains a `manifest.yml` file which allows the built application to be deployed to Cloud Foundry by simply issuing:

```plain
cf push
```

To avoid clashing with the URLs of other applications, you should specify your own subdomain for the application (unless the test application does not need a subdomain).

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

## Running Tests
To run the tests, do the following:

```bash
./gradlew
```

## Contributing
[Pull requests][] are welcome; see the [contributor guidelines][] for details.

[Pull requests]: http://help.github.com/send-pull-requests
[contributor guidelines]: CONTRIBUTING.md

## License
The Tomcat Builder is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0

