/*
 * Copyright 2014-2017 the original author or authors.
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

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Utility class for analysing a {@link RedisConnectionFactory}.
 */
@RestController
@RequestMapping("/redis")
public final class RedisUtils extends AbstractServiceUtils<RedisConnectionFactory> {

    public RedisUtils(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory);
    }

    public String checkAccess(RedisConnectionFactory redisConnectionFactory) {
        RedisConnection connection = null;
        try {
            connection = redisConnectionFactory.getConnection();
            connection.echo("hello".getBytes());

            return "ok";
        } catch (Exception e) {
            return String.format("failed with %s", e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                return String.format("Redis connection close failed with %s", e.getMessage());
            }
        }
    }

    public String getUrl(RedisConnectionFactory redisConnectionFactory) {
        if (isClass(redisConnectionFactory, "org.cloudfoundry.test.fake.FakeRedisConnectionFactory")) {
            return "redis://fake";
        } else if (isClass(redisConnectionFactory, "org.springframework.data.redis.connection.jedis.JedisConnectionFactory")) {
            String hostname = invokeMethod(redisConnectionFactory, "getHostName");
            Integer port = invokeMethod(redisConnectionFactory, "getPort");

            return String.format("redis://%s:%d", hostname, port);
        } else {
            return String.format("Unable to determine URL for RedisConnectionFactory of type %s", redisConnectionFactory.getClass().getName());
        }
    }

}
