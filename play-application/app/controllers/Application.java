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

package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
            System.err.println("Provoking OOM...");
            byte[] _ = new byte[Integer.MAX_VALUE];
        }

        Probe probe = Spring.getBeanOfType(Probe.class);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("Class Path", probe.getClassPath());
        data.put("Environment Variables", System.getenv());
        data.put("Input Arguments", probe.getInputArguments());
        data.put("Request Headers", request().headers());
        data.put("System Properties", new TreeMap<Object, Object>(System.getProperties()));

        return ok(index.render(data));
    }

}
