package com.github.Timmy8.configuratin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                        .requestMatchers(HttpMethod.GET, "/manager-api/swagger-ui/**", "/manager-api/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/manager-api/clients").permitAll()
                        .requestMatchers("/manager-api/**").hasRole("MANAGER")
                        .anyRequest().denyAll())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
