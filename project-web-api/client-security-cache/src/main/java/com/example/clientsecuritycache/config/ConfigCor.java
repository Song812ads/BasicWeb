package com.example.clientsecuritycache.config;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ConfigCor extends org.springframework.web.cors.CorsConfiguration {

	 private final JwtAuthConverter jwtAuthConverter;

		@Bean                                                       
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http 
				.csrf((csrf) -> csrf.disable())    
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(authorize -> authorize
					.anyRequest().authenticated()
				);
			http
            .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt
                        .decoder(jwtDecoder())
                    .jwtAuthenticationConverter(jwtAuthConverter)
                    )
                );
		            
			http
	        .sessionManagement((session) -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                );
			return http.build();
		}
	   
		@Bean
	    JwtDecoder jwtDecoder() {
	        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8920/auth/realms/Song_control/protocol/openid-connect/certs").build();
	    }
	  	
				  
}
