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

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utility class for analysing a {@link DataSource}.
 */
public final class DataSourceUtils {

    private static final String SELECT_ONE = "SELECT 1";

    public String checkDatabaseAccess(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement selectOne = connection.prepareStatement(SELECT_ONE);
            selectOne.execute();
            return "ok";
        } catch (SQLException e) {
            return "failed with " + e.getMessage();
        }
    }

    public String getUrl(DataSource dataSource) {
        String url;
        if (dataSource instanceof org.apache.tomcat.dbcp.dbcp.BasicDataSource) {
            url = ((org.apache.tomcat.dbcp.dbcp.BasicDataSource) dataSource).getUrl();
        } else if (dataSource instanceof org.apache.commons.dbcp.BasicDataSource) {
            url = ((org.apache.commons.dbcp.BasicDataSource) dataSource).getUrl();
        } else if (dataSource instanceof SimpleDriverDataSource) {
            url = ((SimpleDriverDataSource) dataSource).getUrl();
        } else {
            url = "indeterminate URL: unrecognised datasource class";
        }
        return url;
    }
}
