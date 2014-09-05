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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Utility methods used during memory tests
 */
@Component
public final class MemoryUtils {

    private static final double BYTE = 1;

    private static final double KIBI = 1024 * BYTE;

    private static final double MIBI = 1024 * KIBI;

    private static final double GIBI = 1048 * MIBI;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private final Map<String, String> environment;

    private final long outOfMemorySize;

    /**
     * Creates an instance of the utility
     */
    public MemoryUtils() {
        this(System.getenv(), ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax());
    }

    MemoryUtils(Map<String, String> environment, long outOfMemorySize) {
        this.environment = environment;
        this.outOfMemorySize = outOfMemorySize;
    }

    /**
     * Generates an {@link OutOfMemoryError} if the {@code FAIL_OOM} environment variable is {@code true}. Otherwise
     * does nothing.
     *
     * @return
     */
    @PostConstruct
    public byte[][] outOfMemoryOnStart() {
        String value = this.environment.get("FAIL_OOM");

        if ((value != null) && Boolean.parseBoolean(value)) {
            return outOfMemory();
        }

        return null;
    }

    public byte[][] outOfMemory() {
        try {
            AtomicBoolean flag = new AtomicBoolean();
            this.executor.submit(new MemorySizeLogger(flag));
            return this.executor.submit(new MemoryExhauster(flag, this.outOfMemorySize)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private String ibi(long size) {
        if (size > GIBI) {
            return String.format("%.1f GiB", (size / GIBI));
        } else if (size > MIBI) {
            return String.format("%.1f MiB", (size / MIBI));
        } else if (size > KIBI) {
            return String.format("%.1f KiB", (size / KIBI));
        } else {
            return String.format("%d B", size);
        }
    }

    private final class MemorySizeLogger implements Runnable {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        private final AtomicBoolean flag;

        public MemorySizeLogger(AtomicBoolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                while (!this.flag.get()) {
                    this.logger.info(message());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }

        private String message() {
            MemoryUsage heapMemoryUsage = this.memoryMXBean.getHeapMemoryUsage();
            long used = heapMemoryUsage.getUsed();
            long max = heapMemoryUsage.getMax();

            return String.format("%s of %s used", ibi(used), ibi(max));
        }
    }

    private class MemoryExhauster implements Callable<byte[][]> {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        private final AtomicBoolean flag;

        private final long outOfMemorySize;

        public MemoryExhauster(AtomicBoolean flag, long outOfMemorySize) {
            this.flag = flag;
            this.outOfMemorySize = outOfMemorySize;
        }

        @Override
        public byte[][] call() throws Exception {
            this.logger.info("Exhausting {} of memory", ibi(this.outOfMemorySize));

            int size = (int) Math.sqrt(this.outOfMemorySize);
            byte[][] bytes = new byte[size][];

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = new byte[size];
            }

            this.flag.set(true);
            return bytes;
        }
    }
}
