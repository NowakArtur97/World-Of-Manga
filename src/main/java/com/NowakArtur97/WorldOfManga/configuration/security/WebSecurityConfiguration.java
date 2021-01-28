package com.NowakArtur97.WorldOfManga.configuration.security;

import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;

    private final LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;

    private final UserAccessDeniedHandler userAccessDeniedHandler;

    @Autowired
    WebSecurityConfiguration(UserService userService, LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler,
                             LoginAuthenticationFailureHandler loginAuthenticationFailureHandler,
                             UserAccessDeniedHandler userAccessDeniedHandler) {
        this.userService = userService;
        this.loginAuthenticationSuccessHandler = loginAuthenticationSuccessHandler;
        this.loginAuthenticationFailureHandler = loginAuthenticationFailureHandler;
        this.userAccessDeniedHandler = userAccessDeniedHandler;
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**", "/h2/**")
                .hasRole("ADMIN")
                .antMatchers("/auth/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/**")
                .anonymous()
                .antMatchers("/").permitAll()
                .and().csrf().ignoringAntMatchers("/h2/**")
                .and().headers().frameOptions().sameOrigin()
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
