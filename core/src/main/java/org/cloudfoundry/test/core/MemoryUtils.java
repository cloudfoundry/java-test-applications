/*
 * Copyright 2014-2017 the original author or authors.
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

package org.cloudfoundry.test.core;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utility methods for mutating the memory of an application
 */
@RestController
public class MemoryUtils {

    private final Map<String, String> environment;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private final long outOfMemorySize;

    /**
     * Create an instance of the utility
     */
    public MemoryUtils() {
        this(System.getenv(), ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax());
    }

    MemoryUtils(Map<String, String> environment, long outOfMemorySize) {
        this.environment = environment;
        this.outOfMemorySize = outOfMemorySize;
    }

    /**
     * Generates an {@link OutOfMemoryError} if the {@code FAIL_OOM} environment variable is {@code true}. Otherwise does nothing.
     *
     * @return Never returns as it will generate an {@link OutOfMemoryError}
     */
    @RequestMapping(method = RequestMethod.POST, value = "/out-of-memory")
    public byte[][] outOfMemory() {
        try {
            MemorySizeLogger memorySizeLogger = new MemorySizeLogger();

            this.executor.submit(memorySizeLogger);
            return this.executor.submit(new MemoryExhauster(memorySizeLogger, this.outOfMemorySize)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    void outOfMemoryOnStart() {
        Optional.ofNullable(this.environment.get("FAIL_OOM"))
            .map(Boolean::parseBoolean)
            .filter(b -> b)
            .ifPresent(f -> {
                System.err.println("$FAIL_OOM caused memory exhaustion");
                outOfMemory();
            });
    }

}
