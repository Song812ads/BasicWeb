package com.example.clientsecuritycache.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic userFetch() {
		return TopicBuilder.name("userFetch")
						.partitions(10)
						.replicas(2)
						.build();
	}
	
	
}
