/*
 * Cloud Foundry Java Test Applications
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

package util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class Probe {

    private final List<String> inputArguments;

	private final String[] classpath;

	public Probe() {
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		this.inputArguments = runtimeMxBean.getInputArguments();
		this.classpath = runtimeMxBean.getClassPath().split(":");
	}

    public List<String> getInputArguments() {
        return this.inputArguments;
    }

	public String[] getClassPath() {
		return this.classpath;
	}

}
