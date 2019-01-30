/*
 * Copyright 2014-2019 the original author or authors.
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

import java.util.concurrent.Callable;

import static org.cloudfoundry.test.core.HumanReadableMemoryFormatter.toIbi;

final class MemoryExhauster implements Callable<byte[][]> {

    private final MemorySizeLogger memorySizeLogger;

    private final long outOfMemorySize;

    MemoryExhauster(MemorySizeLogger memorySizeLogger, long outOfMemorySize) {
        this.memorySizeLogger = memorySizeLogger;
        this.outOfMemorySize = outOfMemorySize;
    }

    @Override
    public byte[][] call() throws Exception {
        System.out.printf("Exhausting %s of memory\n", toIbi(this.outOfMemorySize));
        this.memorySizeLogger.enable();

        int size = (int) Math.sqrt(this.outOfMemorySize);
        byte[][] bytes = new byte[size][];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = new byte[size];
        }

        return bytes;
    }

}
