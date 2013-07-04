package util;

import java.lang.management.ManagementFactory;
import java.util.List;

public class Probe {

	private final List<String> inputArguments;
  
    public Probe() {
    	this.inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
    }

    public List<String> getInputArguments() {
    	return this.inputArguments;
    }
  
}
