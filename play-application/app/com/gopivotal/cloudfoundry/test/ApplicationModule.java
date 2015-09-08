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
import com.google.inject.Provider;
import com.gopivotal.cloudfoundry.test.core.FakeMongoDbFactory;
import com.gopivotal.cloudfoundry.test.core.FakeRedisConnectionFactory;
import com.gopivotal.cloudfoundry.test.core.InitializationUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.CloudFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import play.db.DB;

import javax.sql.DataSource;

public class ApplicationModule extends AbstractModule {

    private DataSourceProvider provider;
    private Cloud cloud;

    public ApplicationModule() {
        new InitializationUtils().fail();
        this.provider = new DataSourceProvider();
        CloudFactory cloudFactory = new CloudFactory();
        this.cloud = cloudFactory.getCloud();
    }

    @Override
    protected void configure() {
        bind(DataSource.class).toProvider(this.provider);
        bind(RedisConnectionFactory.class).to(FakeRedisConnectionFactory.class);
        bind(MongoDbFactory.class).to(FakeMongoDbFactory.class);
        bind(ConnectionFactory.class).to(CachingConnectionFactory.class);
    }

    private class DataSourceProvider implements Provider<DataSource> {

        @Override
        public DataSource get() {
            try {
                return cloud.getSingletonServiceConnector(DataSource.class, null);
            } catch (CloudException e) {
                return DB.getDataSource();
            }
        }
    }

}
