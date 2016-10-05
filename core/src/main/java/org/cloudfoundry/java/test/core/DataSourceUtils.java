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

package org.cloudfoundry.java.test.core;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utility class for analysing a {@link DataSource}.
 */
@Component
public final class DataSourceUtils extends AbstractServiceUtils<DataSource> {

    private static final String SELECT_ONE = "SELECT 1";

    public String checkAccess(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement selectOne = connection.prepareStatement(SELECT_ONE);
            selectOne.execute();
            return "ok";
        } catch (SQLException e) {
            return "failed with " + e.getMessage();
        }
    }

//

    public String getUrl(DataSource dataSource) {
        if (isClass(dataSource, "com.jolbox.bonecp.BoneCPDataSource")) {
            return invokeMethod(dataSource, "getJdbcUrl");
        } else if (isClass(dataSource, "org.apache.commons.dbcp.BasicDataSource")) {
            return invokeMethod(dataSource, "getUrl");
        } else if (isClass(dataSource, "org.apache.commons.dbcp2.BasicDataSource")) {
            return invokeMethod(dataSource, "getUrl");
        } else if (isClass(dataSource, "org.apache.tomcat.dbcp.dbcp.BasicDataSource")) {
            return invokeMethod(dataSource, "getUrl");
        } else if (isClass(dataSource, "org.apache.tomcat.dbcp.dbcp2.BasicDataSource")) {
            return invokeMethod(dataSource, "getUrl");
        } else if (isClass(dataSource, "org.apache.tomcat.jdbc.pool.DataSource")) {
            return invokeMethod(dataSource, "getUrl");
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.embedded" +
                ".EmbeddedDatabaseFactory$EmbeddedDataSourceProxy")) {
            return getUrl(getDataSource(dataSource, "dataSource"));
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy")) {
            return getUrl(getTargetDataSource(dataSource));
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.SimpleDriverDataSource")) {
            return invokeMethod(dataSource, "getUrl");
        } else if (isClass(dataSource, "org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy")) {
            return getUrl(getTargetDataSource(dataSource));
        } else if (isClass(dataSource, "com.zaxxer.hikari.HikariDataSource")) {
            return invokeMethod(dataSource, "getJdbcUrl");
        } else if (isClass(dataSource, "org.apache.openejb.resource.jdbc.managed.local.ManagedDataSource")) {
            return getUrl(getDataSource(dataSource, "delegate"));
        } else if (isClass(dataSource, "org.apache.tomee.jdbc.TomEEDataSourceCreator$TomEEDataSource")) {
            return invokeMethod(dataSource, "getUrl");
        }

        return String.format("Unable to determine URL for DataSource of type %s", dataSource.getClass().getName());
    }

    private String invokeMethod(DataSource dataSource, String methodName) {
        Method method = ReflectionUtils.findMethod(dataSource.getClass(), methodName);
        return (String) ReflectionUtils.invokeMethod(method, dataSource);
    }

    private DataSource getTargetDataSource(DataSource dataSource) {
        Method method = ReflectionUtils.findMethod(dataSource.getClass(), "getTargetDataSource");
        return (DataSource) ReflectionUtils.invokeMethod(method, dataSource);
    }

    private DataSource getDataSource(DataSource dataSource, String fieldName) {
        try {
            Field field = dataSource.getClass().getDeclaredField(fieldName);
            ReflectionUtils.makeAccessible(field);

            return (DataSource) field.get(dataSource);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
