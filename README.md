# Java Test Applications

A collection of applications used for testing the Java buildpack.

## Applications

### Spring Boot 4.1 (Java 21) — multi-module build

| Name | Description
| ---- | -----------
| `dist-zip-application` | A Spring Boot 4.1 application, deployed as a `distZip`
| `ejb-application` | A Jakarta EE 10 EJB application
| `groovy-application` | A Spring Boot 4.1 application started with `groovy`
| `java-main-application` | A Spring Boot 4.1 application started with `java -jar`
| `java-task-application` | A Spring Boot 4.1 CF task application. Tests Spring Boot `Start-Class` detection and `JBP_CONFIG_JAVA_MAIN` class override. Push with `instances: 0`, run via `cf run-task`
| `web-application` | A Spring MVC 4.1 application (Servlet 6 / WAR)

### Spring Boot 3.5 (Java 17) — standalone projects

| Name | Description
| ---- | -----------
| `java-main-application-boot3` | A Spring Boot 3.5 application started with `java -jar`

> Standalone projects have their own `build.gradle` and Gradle wrapper and are not
> included in the root multi-module build. Build them from their own directory.

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

> `java-main-application-boot3` exposes only `GET /` (health).

## Building

### Prerequisites

* JDK 21 for the multi-module build (Spring Boot 4.1 modules)
* JDK 17 for standalone Spring Boot 3.5 projects

Both JDKs can be managed with [sdkman](https://sdkman.io):

```bash
sdk install java 21.0.10-librca
sdk install java 17.0.19-tem
```

> **Note:** The Gradle daemon must run on Java 21. Gradle 8.x uses ASM 9.x which does
> not support Java 22+ class files. Set `JAVA_HOME` to your Java 21 installation before
> running `./gradlew`, or add `org.gradle.java.home=/path/to/java21` in a local
> (uncommitted) `gradle.properties`.

### Multi-module build (Spring Boot 4.1)

```plain
./gradlew
```

### Standalone projects (Spring Boot 3.5)

```plain
cd java-main-application-boot3
./gradlew bootJar
```

### Building Behind a Proxy
Since this project downloads its dependencies from the internet, building behind a proxy
requires some extra effort. Configure Gradle with the following system properties
(more information [here][]):

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
The project is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
[here]: http://stackoverflow.com/questions/5991194/gradle-proxy-configuration
