package controllers;

import play.api.modules.spring.Spring;

import play.*;
import play.mvc.*;

import views.html.*;
import util.Probe;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;
import java.util.HashMap;


public class Application extends Controller {

	static {
		if (System.getenv().get("FAIL_INIT") != null) {
			throw new RuntimeException("$FAIL_INIT caused initialisation to fail");
		}
	}

	public static Result index() {
		if (System.getenv().get("FAIL_OOM") != null) {
			oom();
		}

		Probe probe = Spring.getBeanOfType(Probe.class);

		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("Class Path", runtimeMxBean.getClassPath().split(":"));
		data.put("Input Arguments", runtimeMxBean.getInputArguments());
		data.put("Request Headers", request().headers());

		return ok(index.render(data));
	}

	private static void oom() {
		// Repeatedly exhaust the heap until the JVM is killed.
		while (true) {
			int i = 1;
			try {
				while (true) {
					@SuppressWarnings("unused")
					Object[] _ = new Object[i];
					i *= 2;
				}
			} catch (OutOfMemoryError oom) {
				System.out.println("Out of memory, i = " + i);
				System.out.flush();
			}
		}
	}

}
