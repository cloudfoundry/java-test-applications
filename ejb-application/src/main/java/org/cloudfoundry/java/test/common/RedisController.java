/*
 * Copyright 2016 the original author or authors.
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

package org.cloudfoundry.java.test.common;

import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.cloudfoundry.java.test.core.FakeRedisConnectionFactory;
import org.cloudfoundry.java.test.core.RedisUtils;

@Path("/redis")
@Produces(MediaType.APPLICATION_JSON)
public class RedisController extends ServiceController<RedisConnectionFactory> {

    @Inject
    void setServiceConnector(@New FakeRedisConnectionFactory serviceConnector) {
        this.serviceConnector = serviceConnector;
    }

    @Inject
    void setServiceUtils(@New RedisUtils serviceUtils) {
        this.serviceUtils = serviceUtils;
    }
}
