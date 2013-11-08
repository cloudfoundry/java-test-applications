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

package com.gopivotal.cloudfoundry.test.core;

import org.junit.Test;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;

public final class DataSourceUtilsTest {

    private final DataSourceUtils dataSourceUtils = new DataSourceUtils();

    private final DataSource testDataSource = mock(DataSource.class);

    @Test
    public void className() {
        assertEquals(testDataSource.getClass().getName(), this.dataSourceUtils.getClassName(testDataSource));
    }

}
