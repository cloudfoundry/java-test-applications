package controllers;

import java.lang.management.ManagementFactory;
import java.util.List;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
    	List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        return ok(index.render("Input arguments: " + inputArguments));
    }
  
}
