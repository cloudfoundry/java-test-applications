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

package com.gopivotal.cloudfoundry.test;

import com.gopivotal.cloudfoundry.test.core.RabbitUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public final class RabbitController {

    private final ConnectionFactory rabbitConnectionFactory;

    private final RabbitUtils rabbitUtils;

    @Inject
    public RabbitController(ConnectionFactory rabbitConnectionFactory, RabbitUtils rabbitUtils) {
        this.rabbitConnectionFactory = rabbitConnectionFactory;
        this.rabbitUtils = rabbitUtils;
    }

    public Result checkAccess() {
        return toResult(this.rabbitUtils.checkAccess(this.rabbitConnectionFactory));
    }

    public Result url() {
        return toResult(this.rabbitUtils.getUrl(this.rabbitConnectionFactory));
    }

    private static <V> Result toResult(V value) {
        return Controller.ok(Json.toJson(value));
    }

    private static Result toResult(String value) {
        return Controller.ok(value);
    }

}
