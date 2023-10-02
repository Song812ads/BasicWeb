package com.example.clientsecuritycache.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clientsecuritycache.model.VerificationUser;


@Repository
public interface VerifyRepository extends MongoRepository<VerificationUser, String>{

	@Query("{token:'?0'}")
	VerificationUser findToken(String token);
	
	

}
