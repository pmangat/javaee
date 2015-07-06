package com.springsample;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

	//ContextLoaderListener is added to ServletContext – the purpose of this is to 'glue' WebApplicationContext to the life cycle of ServletContext.
	//DispatcherServlet is created and initialized with WebApplicationContext we have created, and it's mapped to "/*" URLs 
	//and set to eagerly load on application startup.
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
		dispatcher.addMapping("/Employee/*");
		dispatcher.addMapping("/Customer/*");
		dispatcher.addMapping("/Order/*");
		dispatcher.setLoadOnStartup(1);
	}

	//AnnotationConfigWebApplicationContext is created. 
	//It's WebApplicationContext implementation that looks for Spring configuration in classes annotated with @Configuration annotation.
	//setConfigLocation() method gets hint in which package(s) to look for them.
	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		//context.setConfigLocation("com.springsample.WebConfig");	
		context.register(WebConfig.class, JPAConfig.class);
		return context;
	}
}