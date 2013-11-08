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

class Application {

    @SuppressWarnings("GroovyAssignabilityCheck")
    public static void main(String[] args) {
        def urls = Application.classLoader.rootLoader.URLs.findAll {
            !it.path.endsWith('servlet-api-2.4.jar') && !it.path.endsWith('./')
        }
        urls.addAll new File('lib').listFiles().collect { it.toURI().toURL() }
        urls << new File('classes').toURI().toURL()

        def classLoader = new URLClassLoader(urls as URL[], (ClassLoader) null)
        Thread.currentThread().setContextClassLoader(classLoader)

        def initializationUtils = Class.forName("com.gopivotal.cloudfoundry.test.core.InitializationUtils", true,
                classLoader)
        initializationUtils.getMethod("fail").invoke(initializationUtils.newInstance())

        def springApplication = Class.forName("org.springframework.boot.SpringApplication", true, classLoader)
        def applicationConfiguration = Class.forName("ApplicationConfiguration", true, classLoader)

        args += '--server.port=' + System.env['PORT']

        springApplication.getMethod("run", Object.class, String[].class).invoke(null, applicationConfiguration, args)
    }

}
