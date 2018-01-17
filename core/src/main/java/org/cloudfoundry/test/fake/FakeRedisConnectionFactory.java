/*
 * Copyright 2014-2018 the original author or authors.
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

package org.cloudfoundry.test.fake;


import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.lang.Nullable;

/**
 * Fake RedisConnectionFactory, since we don't have "in-memory" redis (equivalent to, say, H2 database)
 * <p/>
 * Objects of this class can be used wherever auto-reconfiguration is expected to take place. If auto-reconfiguration works as expected, they will be replaced with real connection factory object. If
 * not, tests will fail as expected.
 */
public final class FakeRedisConnectionFactory implements RedisConnectionFactory {


    @Override
    public RedisClusterConnection getClusterConnection() {
        return null;
    }

    @Override
    public RedisConnection getConnection() {
        return null;
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return false;
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return null;
    }

    @Nullable
    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return null;
    }

}
