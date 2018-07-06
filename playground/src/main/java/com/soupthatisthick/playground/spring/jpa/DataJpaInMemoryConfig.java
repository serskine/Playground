package com.soupthatisthick.playground.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

@Configuration
@EnableTransactionManagement
@Profile(value = "inMemory-datasource")
public class DataJpaInMemoryConfig {

	@Autowired
	private Environment env;

	@Bean(name = "primaryInMemoryDatasource")
	public DataSource primaryInMemoryDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
				.setType(EmbeddedDatabaseType.HSQL)
				.build();

		if (db==null) {
			LOG.warning("Failed to initialize the primaryInMemoryDatasource!");
		} else {
			LOG.info("Initialized primaryInMemoryDatasource DB.");
			return db;
		}

		return db;
	}
}
