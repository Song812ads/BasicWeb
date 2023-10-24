package com.example.supply_service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.supply_service.entity.Product;
import com.example.supply_service.repository.ProductRepository;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	ProductRepository repo;
	
	@PostConstruct
	public void preloadCache() {
		
		Cache cache = cacheManager.getCache("applicationCache");
		
		System.out.println("****** Initializing Cache");
		
		List<Product> deptList = repo.findAll();
		
		for (Product product : deptList) {
			cache.put(product.getId(), product);
		}
	}
	
	@Scheduled(fixedRate=15000, initialDelay=15000)
	public void clearCache() {
		System.out.println("****** clearing the Cache");
		cacheManager.getCacheNames().parallelStream().forEach(
				name -> cacheManager.getCache(name).clear()
				);
	}
	
}
