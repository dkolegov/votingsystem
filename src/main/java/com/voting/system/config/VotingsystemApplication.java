package com.voting.system.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import net.sf.ehcache.CacheManager;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@Import({BusinessConfig.class})
@ComponentScan("com.voting.system.web")
@EnableCaching
@EnableAutoConfiguration(exclude={JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class VotingsystemApplication {

    private static final Logger LOG = LoggerFactory.getLogger(VotingsystemApplication.class);

    @Autowired
    private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(VotingsystemApplication.class, args);
	}

	@Bean
	@Autowired
	public EhCacheCacheManager ehCacheCacheManager(CacheManager cacheManager) {
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(cacheManager);
		return ehCacheCacheManager;
	}

	@Bean
	public EhCacheManagerFactoryBean cacheManager() {
		EhCacheManagerFactoryBean ehCacheManager = new EhCacheManagerFactoryBean();
		ehCacheManager.setConfigLocation(new ClassPathResource("cache/ehcache.xml"));
		return ehCacheManager;
	}

    /**
     * Application custom initialization code.
     * <p/>
     * Spring profiles can be configured with a system property
     * -Dspring.profiles.active=javaee
     * <p/>
     */
    @PostConstruct
    public void initApp() {
        LOG.debug("Looking for Spring profiles...");
        if (env.getActiveProfiles().length == 0) {
            LOG.info("No Spring profile configured, running with default configuration.");
        } else {
            for (String profile : env.getActiveProfiles()) {
                LOG.info("Detected Spring profile: {}", profile);
            }
        }
    }
}