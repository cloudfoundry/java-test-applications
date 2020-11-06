# Java Test Applications

A collection of applications used for testing the Java buildpack.

## Applications
| Name | Description
| ---- | -----------
| `dist-zip-application` | A Spring Boot application, deployed as a `distZip`
| `ejb-application` | A JEE EJB application using Servlet 3
| `groovy-application` | An application started with `groovy`
| `java-main-application` | A Spring Boot application started with `java -jar`
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
| `GET  /environment-variables` | The environment variables available to the application
| `GET  /input-arguments` | The list of JVM input arguments for the application
| `POST /out-of-memory` | The URL to trigger an out of memory error
| `GET  /request-headers` | The http request headers of the current request
| `GET  /security-providers` | The system security providers available to the application
| `GET  /system-properties` | The system properties available to the application

## Building

Before building the project, the following tools must be installed:
* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Spring Boot 2 CLI](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-installing-the-cli)

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

## License
The Tomcat Builder is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
[here]: http://stackoverflow.com/questions/5991194/gradle-proxy-configuration
