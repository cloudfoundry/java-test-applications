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

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Smoke test: builds the boot jar and starts it with web-application-type=none.
 * Spring Boot loads the full application context; "Started" in output means success.
 * Process is destroyed after startup (it may keep running if keep-alive is enabled).
 *
 * Run: ./gradlew test (bootJar is built first via task dependency)
 */
class SmokeTest {

    @Test
    void jarStartsWithoutErrors() throws Exception {
        File libsDir = new File("build/libs");
        File[] jars = libsDir.listFiles(f ->
                f.getName().endsWith(".jar") && !f.getName().contains("-plain"));
        assertThat(jars).as("No boot jar found in build/libs — run ./gradlew bootJar first").isNotEmpty();

        File jar = jars[0];
        String javaExec = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        Process process = new ProcessBuilder(
                javaExec, "-jar", jar.getAbsolutePath(),
                "--spring.main.web-application-type=none",
                "--spring.main.banner-mode=off"
        )
                .redirectErrorStream(true)
                .start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            long deadline = System.currentTimeMillis() + 30_000;
            while ((line = reader.readLine()) != null) {
                output.append(line).append('\n');
                if (line.contains("Started ")) {
                    return; // success
                }
                if (System.currentTimeMillis() > deadline) {
                    fail("App did not start within 30s:\n%s", output);
                }
            }
        } finally {
            process.destroyForcibly().waitFor(5, TimeUnit.SECONDS);
        }

        int exitCode = process.exitValue();
        if (exitCode != 0) {
            fail("App startup failed with exit code %d:\n%s", exitCode, output);
        }
    }
}
