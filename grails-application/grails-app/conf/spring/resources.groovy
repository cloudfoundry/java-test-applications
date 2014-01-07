import com.gopivotal.cloudfoundry.test.core.DataSourceUtils
import com.gopivotal.cloudfoundry.test.core.FakeRedisConnectionFactory
import com.gopivotal.cloudfoundry.test.core.FakeMongoDbFactory
import com.gopivotal.cloudfoundry.test.core.HealthUtils
import com.gopivotal.cloudfoundry.test.core.InitializationUtils
import com.gopivotal.cloudfoundry.test.core.MemoryUtils
import com.gopivotal.cloudfoundry.test.core.RedisUtils
import com.gopivotal.cloudfoundry.test.core.MongoDbUtils
import com.gopivotal.cloudfoundry.test.core.RabbitUtils
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory

beans = {

    dataSourceUtils(DataSourceUtils)

    redisUtils(RedisUtils)

    mongoDbUtils(MongoDbUtils)

    rabbitUtils(RabbitUtils)

    healthUtils(HealthUtils)

    initializationUtils(InitializationUtils) {
        fail()
    }

    memoryUtils(MemoryUtils) {
        outOfMemory()
    }

    runtimeUtils(RuntimeUtils)

    redisConnectionFactory(FakeRedisConnectionFactory)

    mongoDbFactory(FakeMongoDbFactory)

    rabbitConnectionFactory(CachingConnectionFactory, null, 0)

}
