/*
 * Copyright 2013 the original author or authors.
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

package com.gopivotal.cloudfoundry.test.core;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utility methods used during memory tests
 */
public final class MemoryUtils {

    private final ExecutorService executor = Executors.newFixedThreadPool(1);

    private final Map<String, String> environment;

    private final int outOfMemorySize;

    /**
     * Creates an instance of the utility
     */
    public MemoryUtils() {
        this(System.getenv(), Integer.MAX_VALUE);
    }

    MemoryUtils(Map<String, String> environment, int outOfMemorySize) {
        this.environment = environment;
        this.outOfMemorySize = outOfMemorySize;
    }

    /**
     * Generates an {@link OutOfMemoryError} if the {@code FAIL_OOM} environment variable is {@code true}. Otherwise
     * does nothing.
     *
     * @return
     */
    public byte[] outOfMemory() {
        String value = this.environment.get("FAIL_OOM");

        if ((value != null) && Boolean.parseBoolean(value)) {
            try {
                return this.executor.submit(new Callable<byte[]>() {

                    @Override
                    public byte[] call() throws Exception {
                        return new byte[MemoryUtils.this.outOfMemorySize];
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
