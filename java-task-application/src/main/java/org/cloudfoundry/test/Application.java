/*
 * Copyright 2014-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Default entry point — detected by the java-buildpack Spring Boot framework via
 * the Start-Class MANIFEST.MF attribute set by the Spring Boot Gradle plugin.
 *
 * Deploy:  cf push java-task-application
 * Run:     cf run-task java-task-application --command "java -jar *.jar" --name task
 */
@SpringBootApplication
public class Application implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        Runtime rt = Runtime.getRuntime();
        logger.info("=== Spring Boot task started ===");
        logger.info("Java version:    {}", System.getProperty("java.version"));
        logger.info("Heap total (MB): {}", rt.totalMemory() / 1024 / 1024);
        logger.info("Heap free  (MB): {}", rt.freeMemory()  / 1024 / 1024);
        logger.info("Heap max   (MB): {}", rt.maxMemory()   / 1024 / 1024);
        logger.info("VCAP_APPLICATION: {}", System.getenv("VCAP_APPLICATION"));
        logger.info("Arguments: {}", args.getNonOptionArgs());
        logger.info("=== Task completed successfully ===");
    }

}
