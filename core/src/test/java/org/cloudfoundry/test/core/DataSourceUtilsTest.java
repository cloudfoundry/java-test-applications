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

package org.cloudfoundry.test.core;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.Driver;
import org.junit.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DataSourceUtilsTest {

    private final Connection connection = mock(Connection.class);

    private final DataSource dataSource = mock(DataSource.class);

    private final DataSourceUtils dataSourceUtils = new DataSourceUtils(null);

    private final PreparedStatement preparedStatement = mock(PreparedStatement.class);

    @Test
    public void checkAccessFailure() throws SQLException {
        when(this.dataSource.getConnection()).thenReturn(this.connection);
        when(this.connection.prepareStatement("SELECT 1")).thenReturn(this.preparedStatement);
        when(this.preparedStatement.execute()).thenThrow(new SQLException("test message"));

        assertThat(this.dataSourceUtils.checkAccess(this.dataSource)).isEqualTo("failed with test message");
    }

    @Test
    public void checkAccessOk() throws SQLException {
        when(this.dataSource.getConnection()).thenReturn(this.connection);
        when(this.connection.prepareStatement("SELECT 1")).thenReturn(this.preparedStatement);
        when(this.preparedStatement.execute()).thenReturn(true);

        assertThat(this.dataSourceUtils.checkAccess(this.dataSource)).isEqualTo("ok");
    }

    @Test
    public void embeddedDataSourceUrl() {
        EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();

        assertEquals("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false", this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void hikariUrl() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:test-url");

        assertEquals("jdbc:test-url", this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void indeterminateUrl() {
        assertThat(this.dataSourceUtils.getUrl(this.dataSource)).isEqualTo(String.format("Unable to determine URL for DataSource of type %s", this.dataSource.getClass().getName()));
    }

    @Test
    public void simpleDataSourceUrl() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource(Driver.load(), "jdbc:test-url");

        assertEquals("jdbc:test-url", this.dataSourceUtils.getUrl(dataSource));
    }

    @Test
    public void tomcatUrl() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setUrl("jdbc:test-url");

        assertEquals("jdbc:test-url", this.dataSourceUtils.getUrl(dataSource));
    }

}
