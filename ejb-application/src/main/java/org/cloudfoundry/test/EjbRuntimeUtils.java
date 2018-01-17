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

package org.cloudfoundry.test;

import org.cloudfoundry.test.core.RuntimeUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class EjbRuntimeUtils {

    @Inject
    private RuntimeUtils runtimeUtils;

    @GET
    @Path("/class-path")
    public List<String> classPath() {
        return this.runtimeUtils.classPath();
    }

    @GET
    @Path("/environment-variables")
    public Map<String, String> environmentVariables() {
        return this.runtimeUtils.environmentVariables();
    }

    @GET
    @Path("/input-arguments")
    public List<String> inputArguments() {
        return this.runtimeUtils.inputArguments();
    }

    @GET
    @Path("/request-headers")
    public Map<String, List<String>> requestHeaders(@Context HttpServletRequest request) {
        return this.runtimeUtils.requestHeaders(request);
    }

    @GET
    @Path("/security-providers")
    public List<String> securityProviders() {
        return this.runtimeUtils.securityProviders();
    }

    @GET
    @Path("/system-properties")
    public Map<Object, Object> systemProperties() {
        return this.runtimeUtils.systemProperties();
    }

}
