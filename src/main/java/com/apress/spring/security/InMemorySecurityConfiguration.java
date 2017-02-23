package com.apress.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Profile("Demo")
public class InMemorySecurityConfiguration {
	
	private static final Logger LOG = LoggerFactory.getLogger(
				InMemorySecurityConfiguration.class); 

	@Autowired
	public void globalAuth(AuthenticationManagerBuilder auth) throws Exception{
		 LOG.info("Configure global in-memory authentication...");
		 
		 auth.inMemoryAuthentication()
		 	.withUser("admin").password("password")
		 		.roles("USER", "ADMIN");
	}
}
