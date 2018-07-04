package com.soupthatisthick.playground.spring;

import com.soupthatisthick.playground.util.exception.AppException;
import com.soupthatisthick.playground.util.podam.ResourceUtil;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executor;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaRepositories({"com.soupthatisthick.playground.*"})
@EntityScan({"com.soupthatisthick.playground.*"})
@ComponentScan({"com.soupthatisthick.playground.*"})
@PropertySources({
		@PropertySource(value = "classpath:/com/soupthatisthick/playground/config/application.properties"),
		@PropertySource(value = "classpath:/com/soupthatisthick/playground/config/application-test.properties", ignoreResourceNotFound = true),
})
public class PlaygroundApp extends SpringBootServletInitializer implements InitializingBean {

	@Autowired
	private DataSource primaryDatasource;

	@Autowired
	private DataSource primaryInMemoryDatasource;

	public static void main(String[] args) throws UnsupportedEncodingException {
		SpringApplication.run(PlaygroundApp.class, args);

		try {
			String banner = ResourceUtil.readResourceIntoString("/com/soupthatisthick/playground/config/banner.txt");
			LOG.debug("\n{}", banner);
		} catch (Exception e) {
			LOG.warning(e.getMessage());
		}

		LOG.debug("Swagger URL: http://localhost:8000/swagger-ui.html");

	}

	@Bean
	public WebMvcConfigurer enableCors() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry corsRegistry) {
				corsRegistry.addMapping("/**");
			}
		};
	}

	@Bean(name = "ThreadPoolTaskExecutor")
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(500);
		executor.initialize();
		return executor;
	}

	@Bean(name = "liquibase")
	public SpringLiquibase liquibase() {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:/db.changelog/primary/liquibase-changeLog.xml");
		liquibase.setDataSource(applicationDatasource());
		LOG.info("Created liquibase for database versioning management.");
		return liquibase;
	}

	@Primary
	@Bean(name = "applicationDatasource")
	public DataSource applicationDatasource() {
		if (primaryDatasource!=null) {
			return primaryDatasource;
		}

		LOG.warning("applicationDatasource(): Value is null. Using the primaryInMemoryDatasource instead");

		if (primaryInMemoryDatasource != null) {
			LOG.warning("applicationDatasource(): Value is null. Using the primaryInMemoryDatasource instead");
			return primaryInMemoryDatasource;
		}

		throw new AppException("We could not create a datasource for the application.");

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.debug("primaryDatasource: {}", primaryDatasource);
		LOG.info("Initializing applications after properties have been set.");
		LOG.info("applicationDatasource: {}", (applicationDatasource()==null) ? "null" : "not null");
	}
}
