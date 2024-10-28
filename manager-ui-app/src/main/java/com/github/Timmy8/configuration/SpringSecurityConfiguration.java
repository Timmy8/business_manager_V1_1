package com.github.Timmy8.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET,"/registration", "/appointment").permitAll()
                        .requestMatchers(HttpMethod.POST,"/registration", "/appointment").permitAll()
                        .requestMatchers("/styles/**", "/script/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").authenticated()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
