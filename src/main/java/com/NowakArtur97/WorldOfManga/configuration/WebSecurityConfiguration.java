package com.NowakArtur97.WorldOfManga.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.NowakArtur97.WorldOfManga.handler.LoginAuthenticationFailureHandler;
import com.NowakArtur97.WorldOfManga.handler.LoginAuthenticationSuccessHandler;
import com.NowakArtur97.WorldOfManga.handler.UserAccessDeniedHandler;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	
	private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;

	private final LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
	
	private final UserAccessDeniedHandler userAccessDeniedHandler;
	
	@Autowired
	public WebSecurityConfiguration(UserService userService, LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler, 
			LoginAuthenticationFailureHandler loginAuthenticationFailureHandler, UserAccessDeniedHandler userAccessDeniedHandler) {
		this.userService = userService;
		this.loginAuthenticationSuccessHandler = loginAuthenticationSuccessHandler;
		this.loginAuthenticationFailureHandler = loginAuthenticationFailureHandler;
		this.userAccessDeniedHandler = userAccessDeniedHandler;
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(userService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

		return daoAuthenticationProvider;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/auth/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/user/**").anonymous()
				.antMatchers("/").permitAll()
			.and()
				.exceptionHandling().accessDeniedHandler(userAccessDeniedHandler)
			.and()
				.formLogin().loginPage("/user/login").loginProcessingUrl("/authenticateTheUser")
				.successHandler(loginAuthenticationSuccessHandler)
				.failureHandler(loginAuthenticationFailureHandler).permitAll(false)
			.and()
				.logout().logoutUrl("/auth/logout").logoutSuccessUrl("/user/login?logout=true").permitAll(false);
			
	}

}
