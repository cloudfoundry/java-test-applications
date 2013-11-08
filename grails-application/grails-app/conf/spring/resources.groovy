import com.gopivotal.cloudfoundry.test.core.HealthUtils;
import com.gopivotal.cloudfoundry.test.core.InitializationUtils;
import com.gopivotal.cloudfoundry.test.core.MemoryUtils;
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;

beans = {

    healthUtils(HealthUtils)

    initializationUtils(InitializationUtils) {
        fail()
    }

    memoryUtils(MemoryUtils) {
        outOfMemory()
    }

    runtimeUtils(RuntimeUtils)

}
