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
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DataSourceUtilsTest {

    public static final String TEST_URL = "jdbc:test-url";

    private final DataSourceUtils dataSourceUtils = new DataSourceUtils();

    private final DataSource testDataSource = mock(DataSource.class);

    private final Connection testConnection = mock(Connection.class);

    private final PreparedStatement preparedStatement = mock(PreparedStatement.class);

    @Test
    public void checkAccessOk() throws SQLException {
        when(this.testDataSource.getConnection()).thenReturn(this.testConnection);
        when(this.testConnection.prepareStatement("SELECT 1")).thenReturn(this.preparedStatement);
        when(this.preparedStatement.execute()).thenReturn(true);
        assertEquals("ok", this.dataSourceUtils.checkDatabaseAccess(testDataSource));
    }

    @Test
    public void checkAccessFailure() throws SQLException {
        when(this.testDataSource.getConnection()).thenReturn(this.testConnection);
        when(this.testConnection.prepareStatement("SELECT 1")).thenReturn(this.preparedStatement);
        when(this.preparedStatement.execute()).thenThrow(new SQLException("test message"));
        assertEquals("failed with test message", this.dataSourceUtils.checkDatabaseAccess(testDataSource));
    }

    @Test
    public void tomcatDbcpUrl() {
        org.apache.tomcat.dbcp.dbcp.BasicDataSource dataSource = mock(org.apache.tomcat.dbcp.dbcp.BasicDataSource
                .class);
        when(dataSource.getUrl()).thenReturn(TEST_URL);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void commonsDbcpUrl() {
        org.apache.commons.dbcp.BasicDataSource dataSource = mock(org.apache.commons.dbcp.BasicDataSource.class);
        when(dataSource.getUrl()).thenReturn(TEST_URL);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void simpleDriverUrl() {
        SimpleDriverDataSource dataSource = mock(SimpleDriverDataSource.class);
        when(dataSource.getUrl()).thenReturn(TEST_URL);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void indeterminateUrl() {
        DataSource dataSource = mock(DataSource.class);
        assertEquals("indeterminate URL: unrecognised datasource class", this.dataSourceUtils.getUrl(dataSource));
    }

}
