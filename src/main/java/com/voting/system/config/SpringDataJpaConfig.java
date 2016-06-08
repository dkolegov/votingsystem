package com.voting.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("spring-data-jpa")
@EnableJpaAuditing
@EnableJpaRepositories("com.voting.system.repository.springdatajpa")
public class SpringDataJpaConfig {

}