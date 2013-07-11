package grails.application

import java.lang.management.ManagementFactory

class ApplicationController {

	static {
		if (System.getenv()['FAIL_INIT'] != null) {
			throw new RuntimeException('$FAIL_INIT caused initialisation to fail')
		}
	}

	def index() {
		if (System.getenv()['FAIL_OOM'] != null) {
			oom()
		}

		def runtimeMxBean = ManagementFactory.getRuntimeMXBean()
		def data = new TreeMap()
		data["Class Path"] = runtimeMxBean.classPath.split(':')
		data["Input Arguments"] = runtimeMxBean.inputArguments
		data["Request Headers"] = headers

		return [data: data]
	}

	def getHeaders() {
		def data = new TreeMap()

		for(headerName in request.headerNames) {
			data[headerName] = request.getHeader(headerName)
		}

		data
	}

	def oom() {
		// Repeatedly exhaust the heap until the JVM is killed.
		while (true) {
			def i = 1
			try {
				while (true) {
					Object[] _ = new Object[i]
					i *= 2
				}
			} catch (OutOfMemoryError oom) {
				println "Out of memory, i = ${i}"
				System.out.flush()
			}
		}
	}
}
