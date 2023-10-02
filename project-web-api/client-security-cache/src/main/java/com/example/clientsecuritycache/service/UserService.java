package com.example.clientsecuritycache.service;

import java.io.IOException;
import java.util.Map;

import com.example.clientsecuritycache.model.UserLogIn;


// de thay doi service va tien hanh test
public interface UserService {

	public boolean save(UserLogIn userLogIn) throws IOException, InterruptedException;


	public String checklogin(String name, String password);


	Map<String, Object>  checkMail(String userLogin);

	public String generate(String email);
	
	public void saveVerification(String email, String token);


	public String validateVerificationToken(String token);
	
	public boolean updatePassword(String token, String pass);


}
