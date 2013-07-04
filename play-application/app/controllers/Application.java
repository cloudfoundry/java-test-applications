package controllers;

import play.api.modules.spring.Spring;

import play.*;
import play.mvc.*;

import views.html.*;
import util.Probe;

public class Application extends Controller {
 
    public static Result index() {
    	Probe probe = Spring.getBeanOfType(Probe.class);
        return ok(index.render("Input arguments: " + probe.getInputArguments()));
    }
  
}
