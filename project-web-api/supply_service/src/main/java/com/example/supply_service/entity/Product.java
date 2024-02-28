package com.example.supply_service.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="product")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

	@Id
	@SequenceGenerator(name="product_sequence", sequenceName="product_seuqence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "product_sequence")
	private Long id;
	
	private String name;
	private String quantity;
	private String prize;
	private String discount;
	private String description;
	private String image;
	
	
	
	
}
