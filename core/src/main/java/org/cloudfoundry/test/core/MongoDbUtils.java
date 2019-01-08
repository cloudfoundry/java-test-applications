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

package org.cloudfoundry.test.core;

import com.mongodb.MongoClient;
import com.mongodb.connection.Cluster;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * Utility class for analysing a {@link MongoDbFactory}.
 */
@RestController
@RequestMapping("/mongodb")
public final class MongoDbUtils extends AbstractServiceUtils<MongoDbFactory> {

    public MongoDbUtils(MongoDbFactory serviceConnector) {
        super(serviceConnector);
    }

    public String checkAccess(MongoDbFactory mongoDbFactory) {
        try {
            mongoDbFactory.getDb();
            return "ok";
        } catch (Exception e) {
            return "failed with " + e.getMessage();
        }
    }

    public String getUrl(MongoDbFactory mongoDbFactory) {
        if (isClass(mongoDbFactory, "org.cloudfoundry.test.fake.FakeMongoDbFactory")) {
            return "mongodb://fake";
        } else if (isClass(mongoDbFactory, "org.springframework.data.mongodb.core.SimpleMongoDbFactory")) {
            return extractUrl(mongoDbFactory);
        } else {
            return String.format("Unable to determine URL for MongoDbFactory of type %s", mongoDbFactory.getClass().getName());
        }
    }

    private String extractUrl(MongoDbFactory mongoDbFactory) {
        Cluster cluster = invokeMethod(this.<MongoClient>getField(mongoDbFactory, "mongoClient"), "getCluster");

        return cluster.getSettings().getHosts().stream()
            .map(serverAddress -> String.format("mongodb://%s:%d/%s", serverAddress.getHost(), serverAddress.getPort(), mongoDbFactory.getDb().getName()))
            .collect(Collectors.joining(","));
    }

}
