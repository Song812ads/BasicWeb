package com.example.supply_service.config;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import reactor.netty.http.client.HttpClient;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebClientConfig {
	

	 private final JwtAuthConverter jwtAuthConverter;
  
	@Bean                                                       
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http 
			.csrf((csrf) -> csrf.disable())    
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/product/fetch").permitAll()
				.requestMatchers("/product/fetchItem/**").permitAll()
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
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8920/auth/realms/song_supply/protocol/openid-connect/certs").build();
    }
	
	@Bean
	public ReactorResourceFactory resourceFactory() {
		ReactorResourceFactory factory = new ReactorResourceFactory();
		factory.setUseGlobalResources(false); 
		return factory;
	}
	
	@Bean
	public WebClient webClient() {
		HttpClient httpClient = HttpClient.create()
								.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
								.responseTimeout(Duration.ofSeconds(2));
		
		WebClient webClient = WebClient.builder()
							.clientConnector(new ReactorClientHttpConnector(httpClient))
							.build();
		return webClient;
	}

  
}
