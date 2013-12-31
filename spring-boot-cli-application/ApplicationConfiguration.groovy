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

import com.gopivotal.cloudfoundry.test.core.FakeRedisConnectionFactory
import com.gopivotal.cloudfoundry.test.core.FakeMongoDbFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory

@ComponentScan(basePackages = "com.gopivotal.cloudfoundry.test")
@EnableAutoConfiguration
@Grab('com.gopivotal.cloudfoundry.test:core:1.0.0.BUILD-SNAPSHOT')
@Grab('com.gopivotal.cloudfoundry.test:spring-common:1.0.0.BUILD-SNAPSHOT')
@Grab('h2')
@Grab('mysql-connector-java')
@Grab('spring-boot-starter-jdbc')
class ApplicationConfiguration {

    @Bean
    static RedisConnectionFactory redisConnectionFactory() {
        return new FakeRedisConnectionFactory()
    }

    @Bean
    static MongoDbFactory mongoDbFactory() {
        return new FakeMongoDbFactory()
    }

    
    @Bean
    static ConnectionFactory rabbitConnectionFactory() {
        return new CachingConnectionFactory(null, 0)
    }

}
