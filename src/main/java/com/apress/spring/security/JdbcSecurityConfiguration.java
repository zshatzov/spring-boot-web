package com.apress.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class JdbcSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {		
		super.init(auth);
		auth.userDetailsService(userDetailsService);
	}

	@Bean	
	public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate){
		RowMapper<User> userRowMapper = (rs, row) -> {
			return new User(
					rs.getString("account_name"),
					rs.getString("password"),
					rs.getBoolean("enabled"),
					rs.getBoolean("account_non_expired"),
					rs.getBoolean("credentials_non_expired"),
					rs.getBoolean("account_non_locked"),
					AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
		};
			
		return username -> jdbcTemplate.queryForObject("select * from account where account_name = ?", 
					userRowMapper, username);
	}
}
