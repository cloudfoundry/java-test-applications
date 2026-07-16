/*
 * Copyright 2014-2026 the original author or authors.
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

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility methods used to get information about the current runtime
 */
@RestController
public final class RuntimeUtils {

    private final Map<String, String> environment;

    private final Environment springEnvironment;

    private final RuntimeMXBean runtimeMXBean;

    private final Provider[] securityProviders;

    private final Map<Object, Object> systemProperties;

    public RuntimeUtils(Environment springEnvironment) {
        this(System.getenv(), springEnvironment, ManagementFactory.getRuntimeMXBean(), Security.getProviders(), System.getProperties());
    }

    RuntimeUtils(Map<String, String> environment, Environment springEnvironment, RuntimeMXBean runtimeMXBean, Provider[] securityProviders, Map<Object, Object> systemProperties) {
        this.environment = environment;
        this.springEnvironment = springEnvironment;
        this.runtimeMXBean = runtimeMXBean;
        this.securityProviders = securityProviders;
        this.systemProperties = systemProperties;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/active-profiles")
    public List<String> activeProfiles() {
        return Arrays.asList(this.springEnvironment.getActiveProfiles());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/class-path")
    public List<String> classPath() {
        return Arrays.asList(this.runtimeMXBean.getClassPath().split(":"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/environment-variables")
    public Map<String, String> environmentVariables() {
        return new TreeMap<>(this.environment);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/input-arguments")
    public List<String> inputArguments() {
        return this.runtimeMXBean.getInputArguments();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/loaded-jars")
    public List<String> loadedJars() {
        List<String> urls = new ArrayList<>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        while (cl != null) {
            if (cl instanceof URLClassLoader ucl) {
                Arrays.stream(ucl.getURLs()).map(URL::toString).forEach(urls::add);
            }
            cl = cl.getParent();
        }
        return urls;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/request-headers")
    public Map<String, List<String>> requestHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames()).stream()
            .collect(Collectors.toMap(Function.identity(), name -> Collections.list(request.getHeaders(name)), this::throwingMerge, TreeMap::new));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/security-providers")
    public List<String> securityProviders() {
        return Arrays.stream(this.securityProviders)
            .map(Provider::toString)
            .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/spring-env")
    public ResponseEntity<String> springEnv(@RequestParam("key") String key) {
        String value = this.springEnvironment.getProperty(key);
        return value != null ? ResponseEntity.ok(value) : ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/system-properties")
    public Map<Object, Object> systemProperties() {
        return new TreeMap<>(this.systemProperties);
    }

    private <T> T throwingMerge(T u, T v) {
        throw new IllegalStateException(String.format("Duplicate key %s", u));
    }

}
