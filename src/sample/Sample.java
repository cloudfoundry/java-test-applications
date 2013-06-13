package sample;

import java.lang.management.ManagementFactory;
import java.util.Arrays;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class Sample {
	
	public static void main(String args[]) throws InstanceNotFoundException, MalformedObjectNameException, NullPointerException, AttributeNotFoundException, MBeanException, ReflectionException {
		ObjectName runtimeName = ObjectName.getInstance("java.lang:type=Runtime");
		String[] inputArguments = (String[])ManagementFactory.getPlatformMBeanServer().getAttribute(runtimeName, "InputArguments");
		System.out.println("Input arguments: " + Arrays.toString(inputArguments));
	}

}
