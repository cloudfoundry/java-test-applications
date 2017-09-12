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

package org.cloudfoundry.test.core;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AbstractServiceUtils<SC> implements ServiceUtils<SC> {

    private final SC serviceConnector;

    protected AbstractServiceUtils(SC serviceConnector) {
        this.serviceConnector = serviceConnector;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/check-access")
    public final String checkAccess() {
        return checkAccess(this.serviceConnector);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/url")
    public final String url() {
        return getUrl(this.serviceConnector);
    }

    @SuppressWarnings("unchecked")
    protected final <T> T getField(Object target, String fieldName) {
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        ReflectionUtils.makeAccessible(field);
        return (T) ReflectionUtils.getField(field, target);
    }

    @SuppressWarnings("unchecked")
    protected final <T> T invokeMethod(Object target, String methodName, Object... args) {
        Method method = ReflectionUtils.findMethod(target.getClass(), methodName);
        ReflectionUtils.makeAccessible(method);
        return (T) ReflectionUtils.invokeMethod(method, target, args);
    }

    protected final boolean isClass(SC serviceConnector, String className) {
        return serviceConnector.getClass().getName().equals(className);
    }

}
