package com.h2rd.refactoring.web;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class MyApplication extends ResourceConfig {

    public MyApplication() {
        register(RequestContextFilter.class);
        register(JacksonFeature.class);
        packages(true, "com.h2rd.refactoring.web.rest");
    }
}
