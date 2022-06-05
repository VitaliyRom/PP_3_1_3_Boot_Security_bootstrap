package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity

public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurity(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService,
                       SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }
    @Autowired
    protected void configureGlobalSecurity(AuthenticationManagerBuilder auf) throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailsServiceImpl> u = auf.userDetailsService(userDetailsService);
        u.passwordEncoder(new BCryptPasswordEncoder(11));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                        .disable()
                .authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/login")
                .successHandler(successUserHandler)
                //.defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();

        http.logout()
                .permitAll();

    }


}