package com.example.supply_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.supply_service.config.Department;
import com.example.supply_service.entity.Product;

import jakarta.transaction.Transactional;


@Transactional
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
    @Modifying
    @Transactional
    @Query("delete from Product u where u.id = ?1")
	public void deletewithId(Long id);
	
	@Query("select u from Product u where u.name like %?1")
	public List<String> findAllByName(String name);
	
	@Modifying
	@Query("update Product u set u.name = ?1 where u.id = ?2")
	public void setUpName(String name, Long id);
	
	@Modifying
	@Query("update Product u set u.prize = ?1 where u.id = ?2")
	public void setUpPrize(String name, Long id);
	
	@Modifying
	@Query("update Product u set u.discount = ?1 where u.id = ?2")
	public void setUpDiscount(String name, Long id);
	
	@Modifying
	@Query("update Product u set u.description = ?1 where u.id = ?2")
	public void setUpDescription(String name, Long id);
	
	@Modifying
	@Query("update Product u set u.image = ?1 where u.id = ?2")
	public void setUpImage(String name, Long id);
	
	@Modifying
	@Query("update Product u set u.quantity = ?1 where u.id = ?2")
	public void setUpQuantity(String name, Long id);

}
