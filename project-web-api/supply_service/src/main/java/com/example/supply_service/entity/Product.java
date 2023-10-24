package com.example.supply_service.entity;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="product")
@Entity
public class Product {

	@Id
	@GenericGenerator(name="sequen", strategy = "sequence", 
	parameters= {
			@Parameter(name="initial_value", value = "1"),
			@Parameter(name="increment_size", value ="1"),
			@Parameter(name="reset_value", value ="1")	
	})
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sequen")
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name="id")
	private Long id;
	
	private String name;
	private String quantity;
	private String prize;
	private String discount;
	private String description;
	private String image;
	
}
