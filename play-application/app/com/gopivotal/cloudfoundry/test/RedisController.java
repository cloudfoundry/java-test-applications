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

import com.gopivotal.cloudfoundry.test.core.DataSourceUtils;
import com.gopivotal.cloudfoundry.test.core.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.sql.DataSource;

@org.springframework.stereotype.Controller
public final class RedisController {

    private final RedisConnectionFactory redisConnectionFactory;

    private final RedisUtils redisUtils;

    @Autowired
    public RedisController(RedisConnectionFactory redisConnectionFactory, RedisUtils redisUtils) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisUtils = redisUtils;
    }

    public Result checkAccess() {
        return toResult(this.redisUtils.checkAccess(this.redisConnectionFactory));
    }

    public Result url() {
        return toResult(this.redisUtils.getUrl(this.redisConnectionFactory));
    }

    private static <V> Result toResult(V value) {
        return Controller.ok(Json.toJson(value));
    }

    private static Result toResult(String value) {
        return Controller.ok(value);
    }

}
