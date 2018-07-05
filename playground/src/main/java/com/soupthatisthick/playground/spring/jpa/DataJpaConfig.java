package com.soupthatisthick.playground.spring.jpa;

import com.soupthatisthick.playground.util.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

@Configuration
@EnableTransactionManagement
@Profile(value = "primary-datasource")
public class DataJpaConfig {

	private static final String PROPERTY_URL = "spring.datasource.url";
	private static final String PROPERTY_USERNAME = "spring.datasource.username";
	private static final String PROPERTY_PASSWORD = "spring.datasource.password";
	private static final String PROPERTY_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";

	@Autowired
	private Environment env;

	@ConfigurationProperties(prefix = "datasource.primary")
	public DataSource primaryDatasource() {
		DataSource dataSource = null;

		final String url = env.getProperty(PROPERTY_URL);
		final String username = env.getProperty(PROPERTY_USERNAME);
		final String password = env.getProperty(PROPERTY_PASSWORD);
		final String driverClassName = env.getProperty(PROPERTY_DRIVER_CLASS_NAME);

		StringBuilder sb = new StringBuilder();
		sb.append("Attempting to initialize the primaryDatasource!\n");
		sb.append(" - ").append(PROPERTY_URL).append(": ").append(url).append("\n");
		sb.append(" - ").append(PROPERTY_USERNAME).append(": ").append(username).append("\n");
		sb.append(" - ").append(PROPERTY_PASSWORD).append(": ").append(password).append("\n");
		sb.append(" - ").append(PROPERTY_DRIVER_CLASS_NAME).append(": ").append(driverClassName).append("\n");

		LOG.info(sb.toString());

		DataSourceBuilder.create()
				.url(url)
				.username(username)
				.password(password)
				.driverClassName(driverClassName)
				.build();

		LOG.debug("\n***\n*** Primary Data Source\n***\n" + JsonUtil.toJson(dataSource, true));

		if (dataSource==null) {
			LOG.warning("Failed to initialize the primaryDatasource!");
		}

		return dataSource;
	}

	@Bean(name="jdbcPrimaryTemplate")
	public JdbcTemplate jdbcPrimaryTemplate(@Qualifier(value="primaryDatasource") DataSource primaryDataSource) {
		return new JdbcTemplate(primaryDataSource);
	}
}
