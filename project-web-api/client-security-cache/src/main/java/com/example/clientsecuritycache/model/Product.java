package com.example.clientsecuritycache.model;


import lombok.Data;

@Data
public class Product {

	private Long id;
	
	private String name;
	private String quantity;
	private String prize;
	private String discount;
	private String description;
	private String image;
	
}
