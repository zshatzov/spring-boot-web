package com.apress.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class JournalWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	private JdbcTemplate jdbcTemplate;

	public JournalWebSecurityConfigurerAdapter() {
	}

	public JournalWebSecurityConfigurerAdapter(boolean disableDefaults) {
		super(disableDefaults);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.eraseCredentials(true);
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests()
			.antMatchers("/", "/webjars/**", "/css/**", "/static/**").permitAll()
			.antMatchers("/journal/**").authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll()
			.usernameParameter("username").passwordParameter("password")
			.and()
			.logout().permitAll(); 
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
