package com.ambrizals.todoapp.config;

import com.ambrizals.todoapp.filters.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurityConfig
 * 
 * Enable Global Method Security info in here : https://stackoverflow.com/questions/33255149/secure-mvc-controller-methods-with-secured-annotation
 */
@Configuration("WebSecurityConfig")
@Order(1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
      .anonymous().authorities("TAMU");		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);	
	}

  
}
