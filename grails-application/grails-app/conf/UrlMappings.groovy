class UrlMappings {

    static mappings = {
        '/'                         (controller: 'Application', action: 'health')
        '/class-path'               (controller: 'Application', action: 'classPath')
        '/environment-variables'    (controller: 'Application', action: 'environmentVariables')
        '/request-headers'          (controller: 'Application', action: 'requestHeaders')
        '/input-arguments'          (controller: 'Application', action: 'inputArguments')
        '/system-properties'        (controller: 'Application', action: 'systemProperties')
        '/datasource/check-access'  (controller: 'DataSource',  action: 'checkAccess')
        '/datasource/url'           (controller: 'DataSource',  action: 'url')
        '/redis/check-access'       (controller: 'Redis',       action: 'checkAccess')
        '/redis/url'                (controller: 'Redis',       action: 'url')
        '/mongodb/check-access'     (controller: 'MongoDb',     action: 'checkAccess')
        '/mongodb/url'              (controller: 'MongoDb',     action: 'url')
        '/rabbit/check-access'      (controller: 'Rabbit',      action: 'checkAccess')
        '/rabbit/url'               (controller: 'Rabbit',      action: 'url')
    }
}
