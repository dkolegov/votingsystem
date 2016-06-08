package com.voting.system.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Profile({"jpa", "spring-data-jpa"})
@PropertySource("classpath:application.properties")
public class SharedJpaConfig {
	
    @Autowired
    private Environment        env;
    
	@Autowired
	private DataSource dataSource;
	
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        // gDickens: BOTH Persistence Unit and Packages to Scan are NOT compatible, persistenceUnit will win
        em.setPersistenceUnitName("votingsystem");
        em.setPackagesToScan("com.voting.system");
        em.setJpaVendorAdapter(jpaVendorAdaper());
        em.afterPropertiesSet();
        return em.getObject();
    }
    
    @Bean
    public JpaVendorAdapter jpaVendorAdaper() {
    	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        		
        vendorAdapter.setDatabase(env.getProperty("spring.jpa.database", Database.class));
        vendorAdapter.setShowSql(env.getProperty("spring.jpa.show-sql", Boolean.class));
        vendorAdapter.setGenerateDdl(true); //Auto creating scheme when true
        return vendorAdapter;
    }
    
    @Bean(name="transactionManager")
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
        return jpaTransactionManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    	return new PersistenceExceptionTranslationPostProcessor();
    }


}
