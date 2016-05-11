package com.sapanywhere.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sapanywhere.app.service.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();

		httpSecurity.authorizeRequests().antMatchers("/libs/**").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/css/**").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/js/**").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/imgs/**").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/oauth/**").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/404.html").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/500.html").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/", "/index").permitAll();
		
		httpSecurity.authorizeRequests().antMatchers("/login.html").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/register.html").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/user/register").permitAll();

		httpSecurity.authorizeRequests().anyRequest().authenticated().and()
				.formLogin().loginPage("/login")
				.usernameParameter("userEmail")
				.passwordParameter("password")
				.failureUrl("/login.html?error")
				.defaultSuccessUrl("/overview.html")
				.permitAll()
				.and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
