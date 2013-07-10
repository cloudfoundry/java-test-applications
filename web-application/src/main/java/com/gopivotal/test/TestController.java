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

package com.gopivotal.test;

import java.lang.management.ManagementFactory;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
final class TestController {

	@RequestMapping(method = RequestMethod.GET, value = "/")
	@ResponseBody
	String root() {
		if (System.getenv().get("FAIL_OOM") != null) {
			oom();
		}
		List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		return "Input arguments: " + inputArguments;
	}

	private static void oom() {
		// Repeatedly exhaust the heap until the JVM is killed.
		while (true) {
			int i = 1;
			try {
				while (true) {
					@SuppressWarnings("unused")
					Object[] _ = new Object[i];
					i *= 2;
				}
			} catch (OutOfMemoryError oom) {
				System.out.println("Out of memory, i = " + i);
				System.out.flush();
			}
		}
	}
}
