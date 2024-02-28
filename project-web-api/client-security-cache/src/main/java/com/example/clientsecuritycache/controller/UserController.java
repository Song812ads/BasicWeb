package com.example.clientsecuritycache.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.clientsecuritycache.model.Product;
import com.example.clientsecuritycache.model.UserLogIn;
import com.example.clientsecuritycache.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@CrossOrigin
@RestController
@RequestMapping("/app/user")
@Slf4j
public class UserController {
	//define 1 loi vao mo hinh service:
	@Autowired
	private UserService userService;

	WebClient client = WebClient.create("http://localhost:8060");
   
   @GetMapping("/fetch")
   public ResponseEntity<Flux<Product>> fetch(){
	   Flux<Product> result = client.get().uri("/product/fetch").accept(MediaType.APPLICATION_JSON)
			   .retrieve()
			   .bodyToFlux(Product.class);
	   return ResponseEntity.ok().body(result);
   }
   
   @GetMapping("/fetchItem")
   public ResponseEntity<Mono<Product>> fetchItem(@RequestParam("id")String id){
	   String uri = new String("/product/fetchItem?id=")+id;
	   Mono<Product> result = client.get().uri(uri)
			   .retrieve()
			   .bodyToMono(Product.class);
	   return ResponseEntity.ok().body(result);
   }

}
