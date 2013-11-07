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

import java.util.List;
import java.util.Map;

import com.gopivotal.cloudfoundry.test.core.DataSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;

import javax.sql.DataSource;

@RestController
final class ApplicationController {

    private final RuntimeUtils runtimeUtils;

    private final DataSourceUtils DataSourceUtils;

    private final DataSource datasource;

    @Autowired
    ApplicationController(RuntimeUtils runtimeUtils, DataSourceUtils DataSourceUtils, DataSource datasource) {
        this.runtimeUtils = runtimeUtils;
        this.DataSourceUtils = DataSourceUtils;
        this.datasource = datasource;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/class-path")
    List<String> classPath() {
        return this.runtimeUtils.classPath();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/environment-variables")
    Map<String, String> environmentVariables() {
        return this.runtimeUtils.environmentVariables();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/input-arguments")
    List<String> inputArguments() {
        return this.runtimeUtils.inputArguments();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/system-properties")
    Map<Object, Object> systemProperties() {
        return this.runtimeUtils.systemProperties();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/datasource-classname")
    String datasourceClassName() {
        return this.DataSourceUtils.getClassName(this.datasource);
    }

}
