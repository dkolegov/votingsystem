package com.voting.system.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.voting.system.service")
@EnableTransactionManagement
@Import({SecurityConfig.class, DataSourceConfig.class, InitDataSourceConfig.class, JdbcConfig.class, SharedJpaConfig.class, SpringDataJpaConfig.class})
public class BusinessConfig {
		

}