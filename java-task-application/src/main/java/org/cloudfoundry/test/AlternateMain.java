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

/**
 * Alternate plain-Java entry point — used to test the java-buildpack JavaMain framework
 * and the JBP_CONFIG_JAVA_MAIN class override mechanism.
 *
 * No Spring context. Runs as a bare JVM process.
 *
 * To run via buildpack main-class override, set in manifest.yml or cf push env:
 *   JBP_CONFIG_JAVA_MAIN: '{class: "org.cloudfoundry.test.AlternateMain"}'
 *
 * Then run as a one-off task (buildpack uses the overridden main class):
 *   cf run-task java-task-application --name alt-task
 */
public class AlternateMain {

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        System.out.println("=== AlternateMain task started (plain Java, no Spring) ===");
        System.out.printf("Java version:    %s%n", System.getProperty("java.version"));
        System.out.printf("Heap total (MB): %d%n", rt.totalMemory() / 1024 / 1024);
        System.out.printf("Heap free  (MB): %d%n", rt.freeMemory()  / 1024 / 1024);
        System.out.printf("Heap max   (MB): %d%n", rt.maxMemory()   / 1024 / 1024);
        System.out.printf("VCAP_APPLICATION: %s%n", System.getenv("VCAP_APPLICATION"));
        if (args.length > 0) {
            System.out.printf("Arguments: %s%n", String.join(", ", args));
        }
        System.out.println("=== AlternateMain task completed successfully ===");
    }

}
