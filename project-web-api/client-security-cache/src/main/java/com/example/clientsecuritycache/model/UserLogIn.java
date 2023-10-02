package com.example.clientsecuritycache.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;


@Data
@Document(collection = "userPass")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserLogIn {
	
	@Id
	private String id;
	private String name;
	private String password;
	private String email;
	

}
