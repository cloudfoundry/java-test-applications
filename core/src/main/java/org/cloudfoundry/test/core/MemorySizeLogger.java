/*
 * Copyright 2014-2018 the original author or authors.
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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.concurrent.CountDownLatch;

import static org.cloudfoundry.test.core.HumanReadableMemoryFormatter.toIbi;

final class MemorySizeLogger implements Runnable {

    private final CountDownLatch latch = new CountDownLatch(1);

    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    @Override
    public void run() {
        try {
            this.latch.await();

            for (; ; ) {
                System.out.println(message());
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    void enable() {
        this.latch.countDown();
    }

    private String message() {
        MemoryUsage heapMemoryUsage = this.memoryMXBean.getHeapMemoryUsage();
        long used = heapMemoryUsage.getUsed();
        long max = heapMemoryUsage.getMax();

        return String.format("%s of %s used", toIbi(used), toIbi(max));
    }

}
