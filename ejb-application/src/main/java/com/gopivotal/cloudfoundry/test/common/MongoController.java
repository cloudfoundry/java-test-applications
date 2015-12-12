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

package com.gopivotal.cloudfoundry.test.common;

import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.data.mongodb.MongoDbFactory;

import com.gopivotal.cloudfoundry.test.core.FakeMongoDbFactory;
import com.gopivotal.cloudfoundry.test.core.MongoDbUtils;

@Path("/mongodb")
@Produces(MediaType.APPLICATION_JSON)
public class MongoController extends ServiceController<MongoDbFactory> {

    @Inject
    void setServiceConnector(@New FakeMongoDbFactory serviceConnector) {
        this.serviceConnector = serviceConnector;
    }

    @Inject
    void setServiceUtils(@New MongoDbUtils serviceUtils) {
        this.serviceUtils = serviceUtils;
    }
}
