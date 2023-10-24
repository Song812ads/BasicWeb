package com.example.clientsecuritycache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableDiscoveryClient
public class ClientSecurityCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientSecurityCacheApplication.class, args);
	}

}
