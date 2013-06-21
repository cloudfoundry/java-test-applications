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

public class JavaMain {

    public static void main(String[] args) throws InterruptedException {
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        System.out.println("Input arguments: " + inputArguments);

        System.out.println();
        
        if (System.getenv().get("OOM") != null) {
    		oom();
    	}
        
        System.out.println("Sleeping for 5 minutes...");
        Thread.sleep(5 * 60 * 1000);
    }

	private static void oom() {
		// Repeatedly exhaust the heap until the JVM is killed.
		while (true) {
			int i = 1;
			try {
				while (true) {
					Object[] s = new Object[i];
					i *= 2;
				}
			} catch (OutOfMemoryError oom) {
				System.out.println("Out of memory, i = " + i);
			}
		}
	}

}
