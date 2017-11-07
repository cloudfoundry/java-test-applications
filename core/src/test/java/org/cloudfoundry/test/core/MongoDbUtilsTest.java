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

import com.mongodb.MongoClientURI;
import org.cloudfoundry.test.fake.FakeMongoDbFactory;
import org.junit.Test;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class MongoDbUtilsTest {

    private final MongoDbFactory mongoDbFactory = mock(MongoDbFactory.class);

    private final MongoDbUtils mongoDbUtils = new MongoDbUtils(null);

    @Test
    public void checkAccessFailure() {
        when(this.mongoDbFactory.getDb()).thenThrow(new UnsupportedOperationException("test-message"));
        assertThat(this.mongoDbUtils.checkAccess(this.mongoDbFactory)).isEqualTo("failed with test-message");
    }

    @Test
    public void checkAccessOk() {
        assertThat(this.mongoDbUtils.checkAccess(this.mongoDbFactory)).isEqualTo("ok");
    }

    @Test
    public void fakeUrl() {
        FakeMongoDbFactory mongoDbFactory = new FakeMongoDbFactory();
        assertThat(this.mongoDbUtils.getUrl(mongoDbFactory)).isEqualTo("mongodb://fake");
    }

    @Test
    public void indeterminateUrl() {
        assertThat(this.mongoDbUtils.getUrl(this.mongoDbFactory)).isEqualTo(String.format("Unable to determine URL for MongoDbFactory of type %s",
            this.mongoDbFactory.getClass().getName()));
    }

    @Test
    public void realUrl() {
        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClientURI("mongodb://localhost/test-database"));
        assertThat(this.mongoDbUtils.getUrl(mongoDbFactory)).isEqualTo("mongodb://localhost:27017/test-database");
    }

}
