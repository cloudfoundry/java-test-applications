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

/**
 * Services abstraction.
 */
public interface ServiceUtils<SC> {

    /**
     * Checks whether the service can be accessed.
     *
     * @param serviceConnector the service connector
     *
     * @return "ok" if and only if the service can be accessed
     */
    String checkAccess(SC serviceConnector);

    /**
     * Gets the URL of the service.
     *
     * @param serviceConnector the service connector
     *
     * @return a String representation of the service URL
     */
    String getUrl(SC serviceConnector);
}
