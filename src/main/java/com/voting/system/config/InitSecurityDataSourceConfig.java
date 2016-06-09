package com.voting.system.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class InitSecurityDataSourceConfig {

	@Autowired
	private Environment env;
	
	DataSource dataSource;

	@Autowired
    public void setDataSource(@Qualifier("springSecurityDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	@PostConstruct
	public void init() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource(env.getProperty("jdbc.security.initLocation")));
		databasePopulator.addScript(new ClassPathResource(env.getProperty("jdbc.security.dataLocation")));
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
	}
}
