/*
 * Copyright 2016 the original author or authors.
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

package com.gopivotal.cloudfoundry.test.common;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.gopivotal.cloudfoundry.test.core.HealthUtils;
import com.gopivotal.cloudfoundry.test.core.MemoryUtils;
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationController {

    @Inject
    private HealthUtils healthUtils;

    @Inject
    private RuntimeUtils runtimeUtils;

    @Inject
    private MemoryUtils memoryUtils;

    @Path("/")
    @GET
    public String health() {
        return healthUtils.health();
    }

    @Path("/class-path")
    @GET
    public List<String> classPath() {
        return runtimeUtils.classPath();
    }

    @Path("/environment-variables")
    @GET
    public Map<String, String> environmentVariables() {
        return runtimeUtils.environmentVariables();
    }

    @Path("/out-of-memory")
    @POST
    public void outOfMemory() {
        memoryUtils.outOfMemory();
    }

    @Path("/request-headers")
    @GET
    public Map<String, List<String>> requestHeaders(@Context HttpServletRequest request) {
        return runtimeUtils.requestHeaders(request);
    }

    @Path("/input-arguments")
    @GET
    public List<String> inputArguments() {
        return runtimeUtils.inputArguments();
    }

    @Path("/system-properties")
    @GET
    public Map<Object, Object> systemProperties() {
        return runtimeUtils.systemProperties();
    }

    @Path("/security-providers")
    @GET
    public List<String> securityProviders() {
        return runtimeUtils.securityProviders();
    }
}
