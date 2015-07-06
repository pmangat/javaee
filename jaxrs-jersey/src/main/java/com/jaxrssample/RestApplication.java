package com.jaxrssample;


import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public RestApplication() {
    	register(MyResource.class);
        register(EmployeeResource.class);
    }
}