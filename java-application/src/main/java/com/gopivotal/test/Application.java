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

import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Application {

    static {
        if (System.getenv().get("FAIL_INIT") != null) {
            throw new RuntimeException("$FAIL_INIT caused initialisation to fail");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (System.getenv().get("FAIL_OOM") != null) {
            new Thread(new Runnable() {


                @SuppressWarnings("unused")
                @Override
                public void run() {
                    System.err.println("Provoking OOM...");
                    byte[] _ = new byte[Integer.MAX_VALUE];
                }
            }).start();
        }

        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        Map<String, Object> data = new TreeMap<String, Object>();
        data.put("Class Path", runtimeMxBean.getClassPath().split(":"));
        data.put("Environment Variables", System.getenv());
        data.put("Input Arguments", runtimeMxBean.getInputArguments());
        data.put("System Properties", System.getProperties());

        map(data, new IndentingPrintStream(System.out));

        System.out.println();
        System.out.println("Sleeping for 1 minute...");
        Thread.sleep(60 * 1000);
    }

    private static void list(List<String> data, IndentingPrintStream out) {
        for (String value : data) {
            out.println(value);
        }
    }

    @SuppressWarnings("unchecked")
    private static void map(Map<String, Object> data, IndentingPrintStream out) {
        for (String key : data.keySet()) {
            out.println(key + ":");

            Object value = data.get(key);
            IndentingPrintStream indented = out.indent();

            if (value instanceof List) {
                list((List<String>) value, indented);
            } else if (value.getClass().isArray()) {
                list(Arrays.<String> asList((String[]) value), indented);
            } else if (value instanceof Map) {
                map((Map<String, Object>) value, indented);
            } else if (value instanceof String) {
                indented.println((String) value);
            } else {
                indented.println("Unkown value type '" + value.getClass().getSimpleName() + "'");
            }
        }
    }

    private static final class IndentingPrintStream {

        private final int indent;

        private final PrintStream out;

        private IndentingPrintStream(PrintStream out) {
            this(0, out);
        }

        private IndentingPrintStream(int indent, PrintStream out) {
            this.indent = indent;
            this.out = out;
        }

        private void println(String s) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < indent; i++) {
                sb.append('\t');
            }

            sb.append(s);

            out.println(sb.toString());
        }

        private IndentingPrintStream indent() {
            return new IndentingPrintStream(indent + 1, out);
        }

    }

}
