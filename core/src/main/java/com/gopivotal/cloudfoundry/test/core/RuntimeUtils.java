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

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Utility methods used to get information about the current runtime
 */
@Component
public final class RuntimeUtils {

    private final Map<String, String> environment;

    private final RuntimeMXBean runtimeMXBean;

    private final Map<Object, Object> systemProperties;

    private final Provider[] securityProviders;

    public RuntimeUtils() {
        this(System.getenv(), ManagementFactory.getRuntimeMXBean(), Security.getProviders(), 
                System.getProperties());
    }

    RuntimeUtils(Map<String, String> environment, RuntimeMXBean runtimeMXBean, Provider[] securityProviders, 
                 Map<Object, Object> 
            systemProperties) {
        this.environment = environment;
        this.runtimeMXBean = runtimeMXBean;
        this.securityProviders = securityProviders;
        this.systemProperties = systemProperties;
    }

    public List<String> classPath() {
        return Arrays.asList(this.runtimeMXBean.getClassPath().split(":"));
    }

    public Map<String, String> environmentVariables() {
        return new TreeMap<>(this.environment);
    }

    public Map<String, List<String>> requestHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = new TreeMap<>();

        for (Enumeration<String> names = request.getHeaderNames(); names.hasMoreElements(); ) {
            String name = names.nextElement();
            headers.put(name, getValuesAsList(request.getHeaders(name)));
        }

        return headers;
    }

    public List<String> inputArguments() {
        return this.runtimeMXBean.getInputArguments();
    }

    public Map<Object, Object> systemProperties() {
        return new TreeMap<>(this.systemProperties);
    }

    public List<String> securityProviders() {
        List<String> providerNames= new ArrayList<>();
        for (Provider p : this.securityProviders) {
            providerNames.add(p.toString());
        }
        return providerNames;
    }

    private List<String> getValuesAsList(Enumeration<String> raw) {
        List<String> values = new ArrayList<>();

        for (; raw.hasMoreElements(); ) {
            values.add(raw.nextElement());
        }

        Collections.sort(values);
        return values;
    }

}
