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

package com.gopivotal.test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
final class TestController {

    @SuppressWarnings("unused")
    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ResponseBody
    String root(HttpEntity<String> requestEntity) {
        if (System.getenv().get("FAIL_OOM") != null) {
            System.err.println("Provoking OOM...");
            byte[] _ = new byte[Integer.MAX_VALUE];
        }

        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        Map<String, Object> data = new TreeMap<String, Object>();
        data.put("Class Path", runtimeMxBean.getClassPath().split(":"));
        data.put("Environment Variables", System.getenv());
        data.put("Input Arguments", runtimeMxBean.getInputArguments());
        data.put("Request Headers", requestEntity.getHeaders());
        data.put("System Properties", System.getProperties());

        return map(data);
    }

    private static String list(List<String> data) {
        String content = "<ul>";

        for (String value : data) {
            content += "<li>" + value + "</li>";
        }

        content += "</ul>";
        return content;
    }

    @SuppressWarnings("unchecked")
    private static String map(Map<String, Object> data) {
        String content = "<table>";

        for (String key : data.keySet()) {
            content += "<tr><td>" + key + "</td><td>";

            Object value = data.get(key);
            if (value instanceof List) {
                content += list((List<String>) value);
            } else if (value.getClass().isArray()) {
                content += list(Arrays.asList((String[]) value));
            } else if (value instanceof Map) {
                content += map((Map<String, Object>) value);
            } else if (value instanceof String) {
                content += value;
            } else {
                content += "Unknown value type '" + value.getClass().getSimpleName() + "'";
            }

            content += "</td></tr>";
        }

        content += "</table>";
        return content;
    }
}
