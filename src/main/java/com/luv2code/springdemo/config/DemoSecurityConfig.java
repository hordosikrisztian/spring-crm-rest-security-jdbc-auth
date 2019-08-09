package com.luv2code.springdemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * Secures all REST endpoints under "/api/customers"
		 * and adds following security authorisations.
		 *
		 * EMPLOYEE role can perform the following:
		 * 1. Get a list of all customers. GET /api/customers
		 * 2. Get a single customer. GET /api/customers/{customerId}
		 *
		 * MANAGER role can perform the following:
		 * 1. Add a new customer. POST /api/customers
		 * 2. Update an existing customer. PUT /api/customers
		 *
		 * ADMIN role can perform the following:
		 * 1. Delete a customer. DELETE /api/customers/{customerId}
		 */

		http.authorizeRequests()
				// EMPLOYEE
				.antMatchers(HttpMethod.GET, "/api/customers").hasRole("EMPLOYEE")
				.antMatchers(HttpMethod.GET, "/api/customers/**").hasRole("EMPLOYEE")

				// MANAGER and ADMIN
				.antMatchers(HttpMethod.POST, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/customers/**").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/customers/**").hasAnyRole("MANAGER", "ADMIN")

				// ADMIN
				.antMatchers(HttpMethod.DELETE, "/api/customers/**").hasRole("ADMIN")
			.and()
				.httpBasic()
			.and()
				.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	public UserDetailsManager userDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

		jdbcUserDetailsManager.setDataSource(securityDataSource);

		return jdbcUserDetailsManager;
	}

}
