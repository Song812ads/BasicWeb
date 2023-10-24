package com.example.clientsecuritycache.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clientsecuritycache.model.UserLogIn;
import com.example.clientsecuritycache.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

//@CrossOrigin
@RestController
@RequestMapping("/app/user")
@Slf4j
public class UserController {
	//define 1 loi vao mo hinh service:
	@Autowired
	private UserService userService;

	@PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>> savethem(@RequestBody UserLogIn userLogIn) throws IOException, InterruptedException {
        boolean check = userService.save(userLogIn);
        log.info(userLogIn.toString());
		Map<String,Boolean> response = new HashMap<>();
		response.put("confirm",check);
        return ResponseEntity.ok().body(response);
    }

	@PostMapping("/log")
	public ResponseEntity<Map<String,String>> checklogin(@RequestBody ObjectNode objectNode){
		String name = objectNode.get("name").asText();
		String password = objectNode.get("password").asText();
		String check = userService.checklogin(name,password);
		Map<String,String> response = new HashMap<>();
		response.put("confirm",check);
		return ResponseEntity.ok().body(response);		
	}

	@PostMapping("/forget")
    public  ResponseEntity<Map<String, Object>> checkMail(@RequestBody ObjectNode objectNode)  {
		String mail = objectNode.get("mail").asText();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Accept-Encoding", "gzip");
		Map<String, Object> check = userService.checkMail(mail);
        return ResponseEntity.ok()
        		.headers(responseHeaders)
        		.body(check);
    }
	
    @GetMapping("/verification")
    public String verifyRegistration(HttpServletResponse httpServletResponse, @RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) {
        	String dest = "http://localhost:8111/app/user/reset?token="+token;
        	
            httpServletResponse.setHeader("Location", dest);
            httpServletResponse.setStatus(302);
            return "Success";
        }
        return "Bad User_Out Of Time";
    }
    
   @PostMapping("/updatepass")
   public ResponseEntity<Map<String, Boolean>> updatepass(@RequestBody ObjectNode objectNode) {
	   String pass = objectNode.get("pass").asText();
	   String token = objectNode.get("token").asText();
	   boolean resp = userService.updatePassword(token, pass);
	   Map<String, Boolean> check = new HashMap<>();
	   check.put("confirm", resp);
	  return ResponseEntity.ok().body(check);
   }

   @GetMapping("/hello")
   @PreAuthorize("hasRole('client_user')")
   public ResponseEntity<Map<String,String>> hello() {
		Map<String,String> response = new HashMap<>();
		response.put("confirm","Hello");
		return ResponseEntity.ok().body(response);	
   }
   
   @RequestMapping(value = "/hello-2", method = RequestMethod.GET)
   @PreAuthorize("hasRole('client_admin')")
   public ResponseEntity<Map<String,String>> hello2() {
		Map<String,String> response = new HashMap<>();
		response.put("confirm","Hello2");
		return ResponseEntity.ok().body(response);	
   }
   
   @GetMapping("/fetch")
   @PreAuthorize("hasRole('client_user')")
   public ResponseEntity<Flux<Map<String,String>>> fetch(){
	   
	   return ResponseEntity.ok().build();
   }

}
