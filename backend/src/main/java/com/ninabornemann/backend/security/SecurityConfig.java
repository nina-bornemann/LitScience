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

  /*  private final String defaultSuccessUrl;

    public SecurityConfig(@Value("${DEFAULT_SUCCESS_URL}") String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }
*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //System.out.println(defaultSuccessUrl);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/api/*").authenticated()
                        .anyRequest().permitAll())
                .oauth2Login(o -> o.defaultSuccessUrl("http://localhost:8080"));
        return http.build();
    }
}
