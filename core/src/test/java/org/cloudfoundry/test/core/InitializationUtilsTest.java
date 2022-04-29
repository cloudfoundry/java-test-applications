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

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class InitializationUtilsTest {

    @Test
    public void failFalse() {
        Map<String, String> environment = Collections.singletonMap("FAIL_INIT", "false");
        new InitializationUtils(environment).fail();
    }

    @Test
    public void failNull() {
        Map<String, String> environment = Collections.emptyMap();
        new InitializationUtils(environment).fail();
    }

    @Test
    public void failTrue() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Map<String, String> environment = Collections.singletonMap("FAIL_INIT", "true");
            new InitializationUtils(environment).fail();
        });
    }

}
