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

package org.cloudfoundry.java.test.core;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Utility methods used during application initialization
 */
@Component
public final class InitializationUtils {

    private final Map<String, String> environment;

    /**
     * Create a new instance of the utility
     */
    public InitializationUtils() {
        this(System.getenv());
    }

    InitializationUtils(Map<String, String> environment) {
        this.environment = environment;
    }

    /**
     * Generates a {@link RuntimeException} if the {@code FAIL_INIT} environment variable is {@code true}. Otherwise
     * does nothing.
     */
    public void fail() {
        String value = this.environment.get("FAIL_INIT");

        if ((value != null) && Boolean.parseBoolean(value)) {
            System.err.println("$FAIL_INIT caused initialization to fail");
            throw new RuntimeException("$FAIL_INIT caused initialization to fail");
        }
    }
}
