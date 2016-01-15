package com.voting.system;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class CustomSecurityMockMvcRequestPostProcessors {
	public static RequestPostProcessor admin() {
		return user("admin").password("password").roles("USER", "ADMIN");
	}
	public static RequestPostProcessor visitor1() {
		return user("visitor1").password("password").roles("USER");
	}
}
