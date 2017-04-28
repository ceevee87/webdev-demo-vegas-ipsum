/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.rest.cvcoding;

// standard java stuff
import java.util.HashSet;
import java.util.Set;

// jersey server (via jersey-bundle)
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author johnr
 */
// Creating an Application allows me to skip putting all of this servlet 
// configuration stuff in my web.xml.
// Apache Tomcat implements a Servlet 3.0 container, so a web.xml is not
// required (although, I have one anyway but for other reasons)
@ApplicationPath("/api")
public class VegasIpsumApp extends Application {

    Set<Object> singletons;

    public VegasIpsumApp() {
        this.singletons = new HashSet<>();
        singletons.add(new VegasIpsumResource());
    }
    
    @Override
    public Set<Object> getSingletons() {
       return singletons;
    }

    @Override    
    public Set<Class<?>> getClasses() { return null; }
}