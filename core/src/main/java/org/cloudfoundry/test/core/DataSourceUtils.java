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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Utility class for analysing a {@link DataSource}.
 */
@RestController
@RequestMapping("/datasource")
public final class DataSourceUtils extends AbstractServiceUtils<DataSource> {

    private static final String SELECT_ONE = "SELECT 1";

    public DataSourceUtils(DataSource dataSource) {
        super(dataSource);
    }

    public String checkAccess(DataSource dataSource) {
        try {
            dataSource
                .getConnection()
                .prepareStatement(SELECT_ONE)
                .execute();

            return "ok";
        } catch (SQLException e) {
            return String.format("failed with %s", e.getMessage());
        }
    }

    public String getUrl(DataSource dataSource) {
        if (isClass(dataSource, "com.zaxxer.hikari.HikariDataSource")) {
            return invokeMethod(dataSource, "getJdbcUrl");
        } else if (isClass(dataSource, "org.apache.tomcat.jdbc.pool.DataSource")) {
            return invokeMethod(dataSource, "getUrl");
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory$EmbeddedDataSourceProxy")) {
            return getUrl(getField(dataSource, "dataSource"));
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.SimpleDriverDataSource")) {
            return invokeMethod(dataSource, "getUrl");
        } else {
            return String.format("Unable to determine URL for DataSource of type %s", dataSource.getClass().getName());
        }
    }

}
