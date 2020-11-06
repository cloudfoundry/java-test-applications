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


import org.cloudfoundry.test.core.HealthUtils
import org.cloudfoundry.test.core.InitializationUtils
import org.cloudfoundry.test.core.MemoryUtils
import org.cloudfoundry.test.core.RuntimeUtils

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
    new InitializationUtils().fail()

    def memoryUtils = new MemoryUtils()
    memoryUtils.outOfMemoryOnStart()

    def healthUtils = new HealthUtils()
    def runtimeUtils = new RuntimeUtils()

    handlers {

        get('') {
            response.send healthUtils.health()
        }

        get('class-path') {
            render json(runtimeUtils.classPath())
        }

        get('environment-variables') {
            render json(runtimeUtils.environmentVariables())
        }

        get('request-headers') {
            TreeMap<String, List<String>> headers = request.getHeaders().getNames().collectEntries { name ->
                [(name): request.getHeaders().getAll(name)]
            }
            render json(headers)
        }

        get('input-arguments') {
            render json(runtimeUtils.inputArguments())
        }

        post('out-of-memory') {
            response.send memoryUtils.outOfMemory()
        }

        get('security-providers') {
            render json(runtimeUtils.securityProviders())
        }

        get('system-properties') {
            render json(runtimeUtils.systemProperties())
        }

    }
}
