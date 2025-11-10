package com.ninabornemann.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${DEFAULT_SUCCESS_URL}") String defaultSuccessUrl) throws Exception {
        http
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/api/*").authenticated()
                        .anyRequest().permitAll())
                .oauth2Login(o -> o.defaultSuccessUrl(defaultSuccessUrl));
        return http.build();
    }
}
