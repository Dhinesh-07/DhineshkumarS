package com.atdxt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/users").authenticated() // Restrict access to "/users" to authenticated users
                                .anyRequest().permitAll() // Allow access to all other URLs
                )
                .formLogin(formLogin ->
                        formLogin.defaultSuccessUrl("/users") // Redirect to "/users" after successful login
                )
                .logout(logout ->
                        logout.logoutSuccessUrl("/login") // Redirect to "/login" after successful logout
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.maximumSessions(1) // Allow only one session per user

                ); // Disable CSRF protection (for simplicity)
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.build();
    }
}
