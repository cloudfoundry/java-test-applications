class UrlMappings {

    static mappings = {
        '/'                         (controller: 'Application', action: 'health')
        '/class-path'               (controller: 'Application', action: 'classPath')
        '/datasource/check-access'  (controller: 'Application', action: 'checkDatabaseAccess')
        '/datasource/url'           (controller: 'Application', action: 'dataSourceUrl')
        '/environment-variables'    (controller: 'Application', action: 'environmentVariables')
        '/input-arguments'          (controller: 'Application', action: 'inputArguments')
        '/system-properties'        (controller: 'Application', action: 'systemProperties')
    }
}
