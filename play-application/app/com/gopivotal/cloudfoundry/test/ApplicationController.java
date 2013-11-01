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

import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

@org.springframework.stereotype.Controller
public final class ApplicationController {

    private final RuntimeUtils runtimeUtils;

    @Autowired
    ApplicationController(RuntimeUtils runtimeUtils) {
        this.runtimeUtils = runtimeUtils;
    }

    public Result classPath() {
        return Controller.ok(Json.toJson(this.runtimeUtils.classPath()));
    }

    public Result environmentVariables() {
        return Controller.ok(Json.toJson(this.runtimeUtils.environmentVariables()));
    }

    public Result inputArguments() {
        return Controller.ok(Json.toJson(this.runtimeUtils.inputArguments()));
    }

    public Result systemProperties() {
        return Controller.ok(Json.toJson(this.runtimeUtils.systemProperties()));
    }

}
