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

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public final class MemoryUtilsTest {

    @Test
    public void outOfMemoryNull() {
        Map<String, String> environment = new HashMap<>();

        assertNull(new MemoryUtils(environment, 0).outOfMemory());
    }

    @Test
    public void outOfMemoryFalse() {
        Map<String, String> environment = new HashMap<>();
        environment.put("FAIL_OOM", "false");

        assertNull(new MemoryUtils(environment, 0).outOfMemory());
    }

    @Test
    public void outOfMemoryTrue() {
        Map<String, String> environment = new HashMap<>();
        environment.put("FAIL_OOM", "true");

        assertNotNull(new MemoryUtils(environment, 0).outOfMemory());
    }
}
