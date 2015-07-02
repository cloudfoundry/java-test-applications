# Java Test Applications
[![Build Status](https://travis-ci.org/cloudfoundry/java-test-applications.svg?branch=master)](https://travis-ci.org/cloudfoundry/java-test-applications)

A collection of applications used for testing the Java buildpack.

## Applications
| Name | Description
| ---- | -----------
| `dist-zip-application` | A Spring Boot application, deployed as a `distZip`
| `grails-application` | A Grails application, deployed as a WAR
| `groovy-application` | An application started with `groovy`
| `java-main-application` | A Spring Boot application started with `java -jar`
| `play-application` | A Play Framework application, deployed as a `dist`
| `ratpack-application` | A Ratpack application, deployed as a `distZip`
| `spring-boot-cli-application` | A Spring Boot CLI application, deployed with `spring grab`
| `spring-boot-cli-jar-application` | A Spring Boot CLI application, deployed with `spring jar`
| `web-application` | A Spring MVC application using Servlet 3
| `web-servlet-2-application` | A Spring MVC application using Servlet 2

### Output Content
All applications support the following REST operations:

| URI | Description
| --- | -----------
| `GET  /` | The health of the application
| `GET  /class-path` | The classpath of the application
| `GET  /datasource/check-access` | The ability of the application to access a RDBMS
| `GET  /datasource/url` | The URL of the application's `DataSource`
| `GET  /environment-variables` | The environment variables available to the application
| `GET  /input-arguments` | The list of JVM input arguments for the application
| `GET  /mongodb/check-access` | The ability of the application to access MongoDB
| `GET  /mongodb/url` | The URL of the application's MongoDB
| `POST /out-of-memory` | The URL to trigger an out of memory error
| `GET  /rabbit/check-access` | The ability of the application to access RabbitMQ
| `GET  /rabbit/url` | The URL of the application's RabbitMQ
| `GET  /redis/check-access` | The ability of the application to access Redis
| `GET  /redis/url` | The URL of the application's Redis
| `GET  /request-headers` | The http request headers of the current request
| `GET  /security-providers` | The system security providers available to the application
| `GET  /system-properties` | The system properties available to the application

## Building

Before building the project, the following tools must be installed: 
* [JDK 7](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Spring Boot CLI](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-installing-the-cli)
* [Grails](https://grails.org/download)
* [Typesafe Activator](https://typesafe.com/platform/getstarted)

This project is built with Gradle. After installing the pre-requisites, run:

```plain
./gradlew
```

### Building Behind a Proxy
Since this project downloads its dependencies from the internet, building behing a proxy requires some extra effort.  In order configure gradle properly, use the following system properties.  More information can be found [here][].

```plain
./gradlew -Dhttp.proxyHost=<HOST> -Dhttp.proxyPort=<PORT>
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

## License
The Tomcat Builder is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
[contributor guidelines]: CONTRIBUTING.md
[here]: http://stackoverflow.com/questions/5991194/gradle-proxy-configuration
[Pull requests]: http://help.github.com/send-pull-requests
