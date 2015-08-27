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

import com.google.inject.AbstractModule;
import com.gopivotal.cloudfoundry.test.core.FakeMongoDbFactory;
import com.gopivotal.cloudfoundry.test.core.FakeRedisConnectionFactory;
import com.gopivotal.cloudfoundry.test.core.InitializationUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class ApplicationModule extends AbstractModule {

    public ApplicationModule() {
        new InitializationUtils().fail();
    }

    @Override
    protected void configure() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        bind(DataSource.class).toInstance(builder.setType(EmbeddedDatabaseType.H2).build());
        bind(RedisConnectionFactory.class).to(FakeRedisConnectionFactory.class);
        bind(MongoDbFactory.class).to(FakeMongoDbFactory.class);
        bind(ConnectionFactory.class).to(CachingConnectionFactory.class);
    }

}
