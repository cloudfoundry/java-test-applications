import com.gopivotal.cloudfoundry.test.core.HealthUtils;
import com.gopivotal.cloudfoundry.test.core.DataSourceUtils;
import com.gopivotal.cloudfoundry.test.core.InitializationUtils;
import com.gopivotal.cloudfoundry.test.core.MemoryUtils;
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

beans = {

    healthUtils(HealthUtils)

    dataSourceUtils(DataSourceUtils)

    initializationUtils(InitializationUtils) {
        fail()
    }

    memoryUtils(MemoryUtils) {
        outOfMemory()
    }

    runtimeUtils(RuntimeUtils)

}
