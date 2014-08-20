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

package com.gopivotal.cloudfoundry.test;

import com.gopivotal.cloudfoundry.test.core.HealthUtils;
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

@org.springframework.stereotype.Controller
public final class ApplicationController {

    private final HealthUtils healthUtils;

    private final RuntimeUtils runtimeUtils;

    @Autowired
    ApplicationController(HealthUtils healthUtils, RuntimeUtils runtimeUtils) {
        this.healthUtils = healthUtils;
        this.runtimeUtils = runtimeUtils;
    }

    public Result health() {
        return toResult(this.healthUtils.health());
    }

    public Result classPath() {
        return toResult(this.runtimeUtils.classPath());
    }

    public Result environmentVariables() {
        return toResult(this.runtimeUtils.environmentVariables());
    }

    public Result requestHeaders() {
        return toResult(Controller.request().headers());
    }

    public Result inputArguments() {
        return toResult(this.runtimeUtils.inputArguments());
    }

    public Result systemProperties() {
        return toResult(this.runtimeUtils.systemProperties());
    }

    private static <V> Result toResult(V value) {
        return Controller.ok(Json.toJson(value));
    }

    private static Result toResult(String value) {
        return Controller.ok(value);
    }

}
