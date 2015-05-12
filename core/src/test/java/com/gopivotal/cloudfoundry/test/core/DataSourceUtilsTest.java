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

import com.jolbox.bonecp.BoneCPDataSource;
import org.junit.Test;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseConfigurer;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
        assertEquals("ok", this.dataSourceUtils.checkAccess(this.testDataSource));
    }

    @Test
    public void checkAccessFailure() throws SQLException {
        when(this.testDataSource.getConnection()).thenReturn(this.testConnection);
        when(this.testConnection.prepareStatement("SELECT 1")).thenReturn(this.preparedStatement);
        when(this.preparedStatement.execute()).thenThrow(new SQLException("test message"));
        assertEquals("failed with test message", this.dataSourceUtils.checkAccess(this.testDataSource));
    }

    @Test
    public void tomcatDbcpUrl() {
        org.apache.tomcat.dbcp.dbcp2.BasicDataSource dataSource = new org.apache.tomcat.dbcp.dbcp2.BasicDataSource();
        dataSource.setUrl(TEST_URL);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void commonsDbcpUrl() {
        org.apache.commons.dbcp.BasicDataSource dataSource = new org.apache.commons.dbcp.BasicDataSource();
        dataSource.setUrl(TEST_URL);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void simpleDriverUrl() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl(TEST_URL);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void indeterminateUrl() {
        DataSource dataSource = mock(DataSource.class);
        assertEquals(String.format("Unable to determine URL for DataSource of type %s",
                dataSource.getClass().getName()), this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void boneCPUrl() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setJdbcUrl(TEST_URL);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void tomcatJdbcPoolUrl() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setUrl(TEST_URL);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void lazyConnectionUrl() {
        BoneCPDataSource targetDataSource = new BoneCPDataSource();
        targetDataSource.setJdbcUrl(TEST_URL);
        LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy(targetDataSource);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void transactionAwareUrl() {
        BoneCPDataSource targetDataSource = new BoneCPDataSource();
        targetDataSource.setJdbcUrl(TEST_URL);
        TransactionAwareDataSourceProxy dataSource = new TransactionAwareDataSourceProxy(targetDataSource);
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void embeddedDataSourceUrl() {
        BoneCPDataSource targetDataSource = new BoneCPDataSource();
        targetDataSource.setJdbcUrl(TEST_URL);
        DataSourceFactory dataSourceFactory = mock(DataSourceFactory.class);
        when(dataSourceFactory.getDataSource()).thenReturn(targetDataSource);

        EmbeddedDatabaseFactory embeddedDatabaseFactory = new EmbeddedDatabaseFactory();
        embeddedDatabaseFactory.setDataSourceFactory(dataSourceFactory);
        embeddedDatabaseFactory.setDatabaseConfigurer(mock(EmbeddedDatabaseConfigurer.class));
        EmbeddedDatabase dataSource = embeddedDatabaseFactory.getDatabase();
        assertEquals(TEST_URL, this.dataSourceUtils.getUrl(dataSource));
    }

}
