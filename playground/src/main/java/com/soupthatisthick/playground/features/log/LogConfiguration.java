package com.soupthatisthick.playground.features.log;

import com.soupthatisthick.playground.features.log.model.LogRequestInterceptor;
import com.soupthatisthick.playground.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

@Configuration
public class LogConfiguration extends WebMvcConfigurerAdapter {

	@Resource
	private LogService logService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		final LogRequestInterceptor logRequestInterceptor = new LogRequestInterceptor(logService);
		LOG.info("Adding " + logRequestInterceptor.getClass().getSimpleName());
		registry.addInterceptor(logRequestInterceptor);
	}
}
