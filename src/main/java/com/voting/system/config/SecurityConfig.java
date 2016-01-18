package com.voting.system.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("visitor1").password("password").roles("USER").and()
				.withUser("visitor2").password("password").roles("USER").and()
				.withUser("visitor3").password("password").roles("USER").and()
				.withUser("admin").password("password").roles("USER", "ADMIN");
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//.antMatchers("/login**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated().and()
				// TODO disable Cross Site Request Forgery(CSRF) for test purpose only!!!
				.csrf().disable()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				//.logoutSuccessHandler(logoutSuccessHandler)
				.invalidateHttpSession(true)
				//.addLogoutHandler(logoutHandler)
				//.deleteCookies(cookieNamesToClear)
				.and()
				// ...
				.formLogin();
	}
}