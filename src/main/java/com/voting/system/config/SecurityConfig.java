package com.voting.system.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
    public void setDataSource(@Qualifier("springSecurityDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//.antMatchers("/login**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login")
			    .usernameParameter("username").passwordParameter("password")
			    .and()
			    .logout().logoutSuccessUrl("/login")
//			    .and()
//			    .exceptionHandling().accessDeniedPage("/403")
			    .and()
				// TODO disable Cross Site Request Forgery(CSRF) for the test purpose only!!!
				.csrf().disable();
	}

	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(11);
	}
}