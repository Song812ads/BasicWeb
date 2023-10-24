package com.example.supply_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.supply_service.entity.Product;
import com.example.supply_service.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import reactor.core.publisher.Flux;

//@CrossOrigin
@RequestMapping("/product")
//@Slf4j
@RestController
//@AllArgsConstructor
public class ProductController {
	
//		@Autowired
//		private final WebClient webClient;
	
		@Autowired
		private ProductRepository repo;
	
	   @PostMapping("/add")
	   @PreAuthorize("hasRole('supply_client')")
	   public ResponseEntity<Map<String, Boolean>> hello(@RequestBody ObjectNode product) throws JsonMappingException, JsonProcessingException {
		   final ObjectMapper objMapper = new ObjectMapper();
		   Product pro = objMapper.readValue(product.toString(),Product.class);
		   if (repo.findAllByName(pro.getName()).isEmpty()) {
			   repo.save(pro);
			   Map<String, Boolean> check = new HashMap<>();
			   check.put("confirm",true);
			   return ResponseEntity.ok().body(check);
		   }
		   else {
		   Map<String,Boolean> check = new HashMap<>();
		   check.put("confirm",false);
		   return ResponseEntity.ok().body(check);	   
		   }
	   }
	   
	   @GetMapping("/all")
	   @PreAuthorize("hasRole('supply_client')")
	   public ResponseEntity<Flux<Product>> all (){
		   return ResponseEntity.ok().body(Flux.fromIterable(repo.findAll()));
	   }
	   

	   @PostMapping("/change")
	   @PreAuthorize("hasRole('supply_client')")
	   @Transactional
	   public ResponseEntity<String> change(@RequestBody ObjectNode product) throws JsonMappingException, JsonProcessingException{
		   final ObjectMapper objMapper = new ObjectMapper();
		   Long id = Long.parseLong(product.get("id").toString().trim());
		   Product pro = objMapper.readValue(product.toString(),Product.class);
		   if (!repo.existsById(id)) {
			   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		   }
		   else {
			   if (!pro.getName().isEmpty()) {
				   repo.setUpName(pro.getName(), id);
			   }
			   if (!pro.getPrize().isEmpty()) {
				   repo.setUpPrize(pro.getPrize(), id);
			   }
			   if (!pro.getQuantity().isEmpty()) {
				   repo.setUpName(pro.getQuantity(), id);
			   }
			   if (!pro.getDiscount().isEmpty()) {
				   repo.setUpName(pro.getDescription(), id);
			   }
			   if (!pro.getImage().isEmpty()) {
				   repo.setUpName(pro.getImage(), id);
			   }
			   if (!pro.getDescription().isEmpty()) {
				   repo.setUpName(pro.getDescription(), id);
			   }
			   return ResponseEntity.ok().build();
		   }
		   
	   }
	   
	   @PostMapping("/erase")
	   @PreAuthorize("hasRole('supply_client')")
	   public ResponseEntity<Object> erase (@RequestBody ObjectNode product){
		   Long id = Long.parseLong(product.get("id").toString().trim());
		   System.out.println(id);
		   if (repo.existsById(id)) {
			   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		   }
		   else {
			   repo.deletewithId(id);
			   return ResponseEntity.ok().build();
		   }
	   }


}
