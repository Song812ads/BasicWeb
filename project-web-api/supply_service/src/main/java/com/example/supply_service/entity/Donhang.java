package com.example.supply_service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="DonHang")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Donhang {

	@Id
	@SequenceGenerator(name="donhang_sequence", sequenceName="donhang_seuqence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "donhang_sequence")
	private Long id;
	
	@OneToOne(
			cascade = CascadeType.ALL
	)
	@JoinColumn(
			name="product_id",
			referencedColumnName = "id"
	)
	private Product product;
}
