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

package com.gopivotal.cloudfoundry.test
import grails.converters.JSON

class ApplicationController {

    def healthUtils

    def memoryUtils

    def runtimeUtils

    def health() {
        render this.healthUtils.health()
    }

    def classPath() {
        render this.runtimeUtils.classPath() as JSON
    }

    def environmentVariables() {
        render this.runtimeUtils.environmentVariables() as JSON
    }

    def outOfMemory() {
        render this.memoryUtils.outOfMemory()
    }

    def requestHeaders() {
        render this.runtimeUtils.requestHeaders(request) as JSON
    }

    def inputArguments() {
        render this.runtimeUtils.inputArguments() as JSON
    }

    def securityProviders() {
        render this.runtimeUtils.securityProviders() as JSON
    }

    def systemProperties() {
        render this.runtimeUtils.systemProperties() as JSON
    }
}
