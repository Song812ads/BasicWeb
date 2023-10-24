package com.example.clientsecuritycache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class UserConfig {
  	@Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    @LoadBalanced
    public WebClient employeeWebClient() {
        return WebClient.builder()
                .baseUrl("http://client-security-cache")
                .filter(filterFunction)
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
