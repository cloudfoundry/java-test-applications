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

package org.cloudfoundry.test;

import org.cloudfoundry.test.core.AbstractServiceUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Produces(MediaType.TEXT_PLAIN)
public abstract class AbstractEjbServiceUtils<T extends AbstractServiceUtils<?>> {

    private final T serviceUtils;

    protected AbstractEjbServiceUtils(T serviceUtils) {
        this.serviceUtils = serviceUtils;
    }

    @GET
    @Path("/check-access")
    public final String checkAccess() {
        return this.serviceUtils.checkAccess();
    }

    @GET
    @Path("/url")
    public final String url() {
        return this.serviceUtils.url();
    }

}
