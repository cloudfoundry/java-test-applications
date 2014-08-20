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

import com.gopivotal.cloudfoundry.test.core.FakeMongoDbFactory;
import com.gopivotal.cloudfoundry.test.core.FakeRedisConnectionFactory;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import play.db.DB;

import javax.sql.DataSource;

import play.db.*;

@Configuration
@ComponentScan(basePackages="com.gopivotal.cloudfoundry.test")
public class ApplicationConfiguration {

    @Bean
    DataSource dataSource() {
        return DB.getDataSource();
    }

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        return new FakeRedisConnectionFactory();
    }

    @Bean
    MongoDbFactory mongoDbFactory() {
        return new FakeMongoDbFactory();
    }

    @Bean
    ConnectionFactory rabbitConnectionFactory() {
    		return new CachingConnectionFactory(null, 0);
    }
}
