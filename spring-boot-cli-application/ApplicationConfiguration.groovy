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

import org.cloudfoundry.test.fake.FakeMongoDbFactory
import org.cloudfoundry.test.fake.FakeRedisConnectionFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.web.servlet.config.annotation.EnableWebMvc

import javax.sql.DataSource

@Configuration
@ComponentScan('org.cloudfoundry.test')
@DependencyManagementBom('org.springframework.boot:spring-boot-dependencies:2.1.3.RELEASE')
@EnableWebMvc
@Grab('org.cloudfoundry.test:core:1.0.0.BUILD-SNAPSHOT')
class ApplicationConfiguration {

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    MongoDbFactory mongoDbFactory() {
        return new FakeMongoDbFactory();
    }

    @Bean
    ConnectionFactory rabbitConnectionFactory() {
        return new CachingConnectionFactory(null, 0);
    }

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        return new FakeRedisConnectionFactory();
    }

}
