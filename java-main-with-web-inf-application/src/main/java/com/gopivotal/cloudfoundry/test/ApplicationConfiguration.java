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
import com.gopivotal.cloudfoundry.test.core.HealthUtils;
import com.gopivotal.cloudfoundry.test.core.InitializationUtils;
import com.gopivotal.cloudfoundry.test.core.MemoryUtils;
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="com.gopivotal.cloudfoundry.test.controller")
@EnableAutoConfiguration
public class ApplicationConfiguration {

    public static void main(String[] args) {
        new InitializationUtils().fail();
        SpringApplication.run(ApplicationConfiguration.class, args);
    }

    @Bean
    HealthUtils healthUtils() {
        return new HealthUtils();
    }

    @Bean
    DataSourceUtils dataSourceUtils() {
        return new DataSourceUtils();
    }

    @Bean
    MemoryUtils memoryUtils() {
        MemoryUtils memory = new MemoryUtils();
        memory.outOfMemory();

        return memory;
    }

    @Bean
    RuntimeUtils runtimeUtils() {
        return new RuntimeUtils();
    }
}
