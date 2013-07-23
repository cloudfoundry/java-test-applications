/*
 * Cloud Foundry Java Test Applications
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

package grails.application

import java.lang.management.ManagementFactory

class ApplicationController {

    static {
        if (System.getenv()['FAIL_INIT'] != null) {
            throw new RuntimeException('$FAIL_INIT caused initialisation to fail')
        }
    }

    def index() {
        if (System.getenv()['FAIL_OOM'] != null) {
            println "Provoking OOM..."
            byte[] _ = new byte[Integer.MAX_VALUE]
        }

        def runtimeMxBean = ManagementFactory.getRuntimeMXBean()
        def data = new TreeMap()
        data["Class Path"] = runtimeMxBean.classPath.split(':')
        data["Environment Variables"] = System.getenv()
        data["Input Arguments"] = runtimeMxBean.inputArguments
        data["Request Headers"] = headers
        data["System Properties"] = new TreeMap(System.properties)

        return [data: data]
    }

    def getHeaders() {
        def data = new TreeMap()

        for(headerName in request.headerNames) {
            data[headerName] = request.getHeader(headerName)
        }

        data
    }
}
