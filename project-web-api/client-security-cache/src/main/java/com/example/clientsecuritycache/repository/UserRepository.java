package com.example.clientsecuritycache.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clientsecuritycache.model.UserLogIn;

@Repository
public interface UserRepository extends MongoRepository<UserLogIn, String> {
	
    @Query("{email:'?0'}")
    UserLogIn findItemByEmail(String email);
    

}
