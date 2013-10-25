import com.gopivotal.cloudfoundry.test.core.InitializationUtils;
import com.gopivotal.cloudfoundry.test.core.MemoryUtils;
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;

// Place your Spring DSL code here
beans = {

    initializationUtils(InitializationUtils) {
        fail()
    }

    memoryUtils(MemoryUtils) {
        outOfMemory()
    }

    runtimeUtils(RuntimeUtils)

}
