/*
 * Copyright 2014-2017 the original author or authors.
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

package org.cloudfoundry.test.core;

import org.cloudfoundry.test.fake.FakeRedisConnectionFactory;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class RedisUtilsTest {

    private final RedisConnection redisConnection = mock(RedisConnection.class);

    private final RedisConnectionFactory redisConnectionFactory = mock(RedisConnectionFactory.class);

    private final RedisUtils redisUtils = new RedisUtils(null);

    @Test
    public void checkAccessFailure() {
        when(this.redisConnectionFactory.getConnection()).thenReturn(this.redisConnection);
        when(this.redisConnection.echo("hello".getBytes())).thenThrow(new UnsupportedOperationException("test-message"));
        assertThat(this.redisUtils.checkAccess(this.redisConnectionFactory)).isEqualTo("failed with test-message");
    }

    @Test
    public void checkAccessOk() {
        when(this.redisConnectionFactory.getConnection()).thenReturn(this.redisConnection);
        assertThat(this.redisUtils.checkAccess(this.redisConnectionFactory)).isEqualTo("ok");
    }

    @Test
    public void fakeUrl() {
        FakeRedisConnectionFactory redisConnectionFactory = new FakeRedisConnectionFactory();
        assertThat(this.redisUtils.getUrl(redisConnectionFactory)).isEqualTo("redis://fake");
    }

    @Test
    public void indeterminateUrl() {
        assertThat(this.redisUtils.getUrl(this.redisConnectionFactory)).isEqualTo(String.format("Unable to determine URL for RedisConnectionFactory of type %s",
            this.redisConnectionFactory.getClass().getName()));
    }

    @Test
    public void jedisUrl() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("test-host");
        assertThat(this.redisUtils.getUrl(jedisConnectionFactory)).isEqualTo("redis://test-host:6379");
    }

}
