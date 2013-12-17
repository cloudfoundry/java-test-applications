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

import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utility class for analysing a {@link DataSource}.
 */
public final class DataSourceUtils {

    private static final String SELECT_ONE = "SELECT 1";

    public String checkDatabaseAccess(DataSource dataSource) {
        if (dataSource == null) {
            return "No datasource found";
        }
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
        if (dataSource == null) {
            return "No datasource found";
        }
        if (isClass(dataSource, "com.jolbox.bonecp.BoneCPDataSource")) {
            return ((com.jolbox.bonecp.BoneCPDataSource) dataSource).getJdbcUrl();
        } else if (isClass(dataSource, "org.apache.commons.dbcp.BasicDataSource")) {
            return ((org.apache.commons.dbcp.BasicDataSource) dataSource).getUrl();
        } else if (isClass(dataSource, "org.apache.tomcat.dbcp.dbcp.BasicDataSource")) {
            return ((org.apache.tomcat.dbcp.dbcp.BasicDataSource) dataSource).getUrl();
        } else if (isClass(dataSource, "org.apache.tomcat.jdbc.pool.DataSource")) {
            return ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).getUrl();
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.embedded" +
                ".EmbeddedDatabaseFactory$EmbeddedDataSourceProxy")) {
            try {
                Field field = dataSource.getClass().getDeclaredField("dataSource");
                ReflectionUtils.makeAccessible(field);

                return getUrl((DataSource) field.get(dataSource));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy")) {
            return getUrl(((org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy) dataSource)
                    .getTargetDataSource());
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.SimpleDriverDataSource")) {
            return ((org.springframework.jdbc.datasource.SimpleDriverDataSource) dataSource).getUrl();
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy")) {
            return getUrl(((org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy) dataSource)
                    .getTargetDataSource());
        }

        return String.format("Unable to determine URL for DataSource of type %s", dataSource.getClass().getName());
    }

    private boolean isClass(DataSource dataSource, String className) {
        return dataSource.getClass().getName().equals(className);
    }
}
