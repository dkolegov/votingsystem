package com.voting.system;

import org.springframework.security.web.context.*;

import com.voting.system.config.SecurityConfig;

public class SecurityWebApplicationInitializer
	extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
		super(SecurityConfig.class);
	}
}