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

package controllers;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

import play.api.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import util.Probe;
import views.html.index;

public class Application extends Controller {

    static {
        if (System.getenv().get("FAIL_INIT") != null) {
            throw new RuntimeException("$FAIL_INIT caused initialisation to fail");
        }
    }

    @SuppressWarnings("unused")
    public static Result index() {
        if (System.getenv().get("FAIL_OOM") != null) {
            System.err.println("Exhausting heap...");
            byte[] _ = new byte[Integer.MAX_VALUE];
        }

        Spring.getBeanOfType(Probe.class);

        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("Class Path", runtimeMxBean.getClassPath().split(":"));
        data.put("Environment Variables", System.getenv());
        data.put("Input Arguments", runtimeMxBean.getInputArguments());
        data.put("Request Headers", request().headers());

        return ok(index.render(data));
    }

}
