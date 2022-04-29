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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.management.RuntimeMXBean;
import java.security.Provider;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public final class RuntimeUtilsTest {

    private final Map<String, String> environment = Collections.singletonMap("test-key-1", "test-value-1");

    private final Provider provider = mock(Provider.class);

    private final RuntimeMXBean runtimeMXBean = mock(RuntimeMXBean.class);

    private final Map<Object, Object> systemProperties = Collections.singletonMap("test-key-2", "test-value-2");

    private Provider[] securityProviders = new Provider[]{this.provider};

    private final RuntimeUtils runtimeUtils = new RuntimeUtils(this.environment, this.runtimeMXBean,
        this.securityProviders, this.systemProperties);

    @Test
    public void classPath() {
        when(this.runtimeMXBean.getClassPath()).thenReturn("alpha:bravo");
        assertThat(this.runtimeUtils.classPath()).containsExactly("alpha", "bravo");
    }

    @Test
    public void environmentVariables() {
        assertThat(this.runtimeUtils.environmentVariables()).isEqualTo(this.environment);
    }

    @Test
    public void inputArguments() {
        when(this.runtimeMXBean.getInputArguments()).thenReturn(Arrays.asList("alpha", "bravo"));
        assertThat(this.runtimeUtils.inputArguments()).containsExactly("alpha", "bravo");
    }

    @Test
    public void requestHeaders() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("test-key", "test-value-1");
        request.addHeader("test-key", "test-value-2");

        assertThat(this.runtimeUtils.requestHeaders(request)).containsExactly(entry("test-key", Arrays.asList("test-value-1", "test-value-2")));
    }

    @Test
    public void securityProviders() {
        when(this.provider.toString()).thenReturn("test-provider");
        assertThat(this.runtimeUtils.securityProviders()).containsExactly("test-provider");
    }

    @Test
    public void systemProperties() {
        assertThat(this.runtimeUtils.systemProperties()).isEqualTo(this.systemProperties);
    }

}
