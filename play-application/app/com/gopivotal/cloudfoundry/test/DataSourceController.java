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

package com.gopivotal.cloudfoundry.test;

import com.gopivotal.cloudfoundry.test.core.HealthUtils;
import com.gopivotal.cloudfoundry.test.core.DataSourceUtils;
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.sql.DataSource;

@org.springframework.stereotype.Controller
public final class DataSourceController {

    private DataSource dataSource;

    private final DataSourceUtils dataSourceUtils = new DataSourceUtils();

    @Autowired(required=false)
    public void setDataSource(DataSource dataSource) {
    		this.dataSource = dataSource;
    }

    public Result checkAccess() {
        return toResult(this.dataSourceUtils.checkAccess(this.dataSource));
    }

    public Result url() {
        return toResult(this.dataSourceUtils.getUrl(this.dataSource));
    }

    private static <V> Result toResult(V value) {
        return Controller.ok(Json.toJson(value));
    }

    private static Result toResult(String value) {
        return Controller.ok(value);
    }

}
