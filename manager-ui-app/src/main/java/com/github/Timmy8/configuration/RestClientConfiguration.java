package com.github.Timmy8.configuration;

import com.github.Timmy8.restClient.DefaultAppointmentsRestClient;
import com.github.Timmy8.restClient.DefaultClientsRestClient;
import com.github.Timmy8.restClient.DefaultProposalsRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public DefaultClientsRestClient clientsRestClient(
            @Value("${business-manager.services.manager-api.uri:http://localhost:8081}") String manager_api_base_uri,
            @Value("${business-manager.services.manager-api.username:}") String manager_api_security_username,
            @Value("${business-manager.services.manager-api.password:}") String manager_api_security_password){

        return new DefaultClientsRestClient(RestClient
                .builder()
                .baseUrl(manager_api_base_uri)
                .requestInterceptor(new BasicAuthenticationInterceptor(manager_api_security_username, manager_api_security_password))
                .build());
    }

    @Bean
    public DefaultProposalsRestClient proposalsRestClient(
            @Value("${business-manager.services.manager-api.uri:http://localhost:8081}") String manager_api_base_uri,
            @Value("${business-manager.services.manager-api.username:}") String manager_api_security_username,
            @Value("${business-manager.services.manager-api.password:}") String manager_api_security_password){

        return new DefaultProposalsRestClient(RestClient
                .builder()
                .baseUrl(manager_api_base_uri)
                .requestInterceptor(new BasicAuthenticationInterceptor(manager_api_security_username, manager_api_security_password))
                .build());
    }

    @Bean
    public DefaultAppointmentsRestClient defaultAppointmentsRestClient(
            @Value("${business-manager.services.manager-api.uri:http://localhost:8081}") String manager_api_base_uri,
            @Value("${business-manager.services.manager-api.username:}") String manager_api_security_username,
            @Value("${business-manager.services.manager-api.password:}") String manager_api_security_password){

        return new DefaultAppointmentsRestClient(RestClient
                .builder()
                .baseUrl(manager_api_base_uri)
                .requestInterceptor(new BasicAuthenticationInterceptor(manager_api_security_username, manager_api_security_password))
                .build());
    }
}
