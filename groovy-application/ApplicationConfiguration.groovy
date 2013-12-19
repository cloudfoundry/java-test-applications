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

import com.gopivotal.cloudfoundry.test.controller.ApplicationController
import com.gopivotal.cloudfoundry.test.controller.DataSourceController
import com.gopivotal.cloudfoundry.test.controller.RedisController
import com.gopivotal.cloudfoundry.test.core.*
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory

import javax.sql.DataSource

@EnableAutoConfiguration
public class ApplicationConfiguration {

    @Bean
    static DataSourceUtils dataSourceUtils() {
        return new DataSourceUtils();
    }

    @Bean
    static HealthUtils healthUtils() {
        return new HealthUtils();
    }

    @Bean
    static MemoryUtils memoryUtils() {
        def memory = new MemoryUtils()
        memory.outOfMemory()

        return memory
    }

    @Bean
    static RedisUtils redisUtils() {
        return new RedisUtils()
    }

    @Bean
    static RuntimeUtils runtimeUtils() {
        return new RuntimeUtils()
    }

    @Bean
    static RedisConnectionFactory redisConnectionFactory() {
        return new FakeRedisConnectionFactory()
    }

    @Bean
    static ApplicationController applicationController() {
        return new ApplicationController(healthUtils(), runtimeUtils())
    }

    @Bean
    static DataSourceController dataSourceController(DataSource dataSource) {
        return new DataSourceController(dataSourceUtils(), dataSource)
    }

    @Bean
    static RedisController redisController(RedisConnectionFactory redisConnectionFactory) {
        return new RedisController(redisUtils(), redisConnectionFactory)
    }

}
