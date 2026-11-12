/*
 * Copyright 2014-2026 the original author or authors.
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

package org.cloudfoundry.test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

/**
 * A dependency-free application that starts in well under a second and reports what the JVM received:
 * <ul>
 * <li>{@code GET /} returns {@code ok}</li>
 * <li>{@code GET /input-arguments} returns the JVM input arguments, one per line</li>
 * <li>{@code GET /system-properties} returns the system properties as {@code key=value} lines</li>
 * <li>{@code GET /environment-variables} returns the environment as {@code key=value} lines</li>
 * <li>{@code GET /class-path} returns the class path</li>
 * </ul>
 */
public final class Main {

    public static void main(String[] args) throws IOException {
        String port = System.getenv("PORT");
        HttpServer server = HttpServer.create(new InetSocketAddress(port != null ? Integer.parseInt(port) : 8080), 0);

        server.createContext("/", exchange -> {
            if ("/".equals(exchange.getRequestURI().getPath())) {
                respond(exchange, "ok");
            } else {
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
            }
        });
        server.createContext("/input-arguments", exchange ->
                respond(exchange, String.join("\n", ManagementFactory.getRuntimeMXBean().getInputArguments())));
        server.createContext("/system-properties", exchange ->
                respond(exchange, lines(new TreeMap<Object, Object>(System.getProperties()))));
        server.createContext("/environment-variables", exchange ->
                respond(exchange, lines(new TreeMap<String, String>(System.getenv()))));
        server.createContext("/class-path", exchange ->
                respond(exchange, System.getProperty("java.class.path")));

        server.start();
        System.out.println("Started on port " + server.getAddress().getPort());
    }

    private static String lines(Map<?, ?> map) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            result.append(entry.getKey()).append('=').append(entry.getValue()).append('\n');
        }
        return result.toString();
    }

    private static void respond(HttpExchange exchange, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }
}
