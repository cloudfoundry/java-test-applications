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
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

public final class RuntimeUtilsTest {

    private final Map<String, String> environment = new HashMap<>();

    private final RuntimeMXBean runtimeMXBean = mock(RuntimeMXBean.class);

    private final Map<Object, Object> systemProperties = new HashMap<>();

    private final RuntimeUtils runtimeUtils = new RuntimeUtils(this.environment, this.runtimeMXBean, this.systemProperties);

    @Test
    public void classPath() {
        when(this.runtimeMXBean.getClassPath()).thenReturn("alpha:bravo");
        assertEquals(Arrays.asList("alpha", "bravo"), this.runtimeUtils.classPath());
    }

    @Test
    public void environmentVariables() {
        assertSame(this.environment, this.runtimeUtils.environmentVariables());
    }

    @Test
    public void requestHeaders() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("one", "two");
        Map<String, String> parsedHeaders = this.runtimeUtils.requestHeaders(request);

        Map<String, String> expectedResults = new HashMap<>();
        expectedResults.put("one", "two");

        assertEquals(expectedResults, parsedHeaders);
    }

    @Test
    public void inputArguments() {
        List<String> inputArguments = new ArrayList<>();
        when(this.runtimeMXBean.getInputArguments()).thenReturn(inputArguments);
        assertSame(inputArguments, this.runtimeUtils.inputArguments());
    }

    @Test
    public void systemProperties() {
        assertSame(this.systemProperties, this.runtimeUtils.systemProperties());
    }
}
