class UrlMappings {

    static mappings = {
        '/'                        (controller: 'Application', action: 'health')
        '/class-path'              (controller: 'Application', action: 'classPath')
        '/datasource/check-access' (controller: 'DataSource',  action: 'checkAccess')
        '/datasource/url'          (controller: 'DataSource',  action: 'url')
        '/environment-variables'   (controller: 'Application', action: 'environmentVariables')
        '/input-arguments'         (controller: 'Application', action: 'inputArguments')
        '/mongodb/check-access'    (controller: 'MongoDb',     action: 'checkAccess')
        '/mongodb/url'             (controller: 'MongoDb',     action: 'url')
        '/out-of-memory'           (controller: 'Application', action: [POST:'outOfMemory'])
        '/rabbit/check-access'     (controller: 'Rabbit',      action: 'checkAccess')
        '/rabbit/url'              (controller: 'Rabbit',      action: 'url')
        '/redis/check-access'      (controller: 'Redis',       action: 'checkAccess')
        '/redis/url'               (controller: 'Redis',       action: 'url')
        '/request-headers'         (controller: 'Application', action: 'requestHeaders')
        '/security-providers'      (controller: 'Application', action: 'securityProviders')
        '/system-properties'       (controller: 'Application', action: 'systemProperties')
    }
}
