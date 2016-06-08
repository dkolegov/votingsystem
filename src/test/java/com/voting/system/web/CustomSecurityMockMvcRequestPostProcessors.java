package com.voting.system.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class CustomSecurityMockMvcRequestPostProcessors {
	public static RequestPostProcessor admin() {
		return user("admin").password("password").roles("USER", "ADMIN");
	}

	public static RequestPostProcessor visitor1() {
		return user("visitor1").password("password").roles("USER");
	}

	public static RequestPostProcessor visitor2() {
		return user("visitor2").password("password").roles("USER");
	}
	

	public static RequestPostProcessor visitor3() {
		return user("visitor3").password("password").roles("USER");
	}
}
