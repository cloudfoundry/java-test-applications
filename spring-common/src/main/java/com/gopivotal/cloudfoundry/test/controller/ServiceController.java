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

package com.gopivotal.cloudfoundry.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gopivotal.cloudfoundry.test.core.ServiceUtils;

abstract class ServiceController<SC> {
	private final SC serviceConnector;
	private final ServiceUtils<SC> serviceUtils; 
	
    protected ServiceController(ServiceUtils<SC> serviceUtils, SC serviceConnector) {
		this.serviceUtils = serviceUtils;
        this.serviceConnector = serviceConnector;
	}

    @RequestMapping(method = RequestMethod.GET, value = "/check-access")
    String checkAccess() {
        return this.serviceUtils.checkAccess(this.serviceConnector);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/url")
    String url() {
        return this.serviceUtils.getUrl(this.serviceConnector);
    }
	
}
