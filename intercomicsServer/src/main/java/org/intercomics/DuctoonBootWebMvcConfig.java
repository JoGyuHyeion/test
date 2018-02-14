package org.intercomics;

import org.intercomics.interceptor.SampleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class DuctoonBootWebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new SampleInterceptor()).addPathPatterns("/test");
	}

}
