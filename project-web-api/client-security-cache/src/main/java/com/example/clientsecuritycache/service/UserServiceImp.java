package com.example.clientsecuritycache.service;


import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.clientsecuritycache.model.UserLogIn;
import com.example.clientsecuritycache.model.VerificationUser;
import com.example.clientsecuritycache.repository.UserRepository;
import com.example.clientsecuritycache.repository.VerifyRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Autowired
	private VerifyRepository verifyRepository;
	
	@Override
	public boolean save(UserLogIn userLogIn) throws IOException, InterruptedException {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(userLogIn.getName()));
		
		List<UserLogIn> users = mongoTemplate.find(query,UserLogIn.class);
		if  (!users.isEmpty()) {return false; }
		else {return true;
		
		}
	}

	@Override
	public String checklogin(String name, String password) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		List<UserLogIn> users = mongoTemplate.find(query,UserLogIn.class);
		if  (users.isEmpty()) return "Ten dang nhap khong ton tai"; 
		else {
			UserLogIn target = users.get(0);
			if (!target.getPassword().equals(password)) {
				return "Pass sai hay nhap lai";
			}
			return "Login success";
 		}
	}
	


	@Override
	public Map<String, Object> checkMail(String userLogIn) {
		boolean check_mail;
		String url;
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(userLogIn));
		
		List<UserLogIn> users = mongoTemplate.find(query,UserLogIn.class);
		if  (users.isEmpty()) { check_mail = false; url=""; }
		else {
			check_mail=true; 
			url = generate(userLogIn);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("check",check_mail);
		map.put("url", url);
		return map ;
	}

	
    @Override
    public String generate(String email) {
    	String token = UUID.randomUUID().toString();
    	saveVerification(email,token);
    	String url = "http://localhost:8123/app/user/verification?token="+token;
    	return url;
    	
    }
    
    @Override
	public void saveVerification(String email, String token) {
		VerificationUser verificationUser = new VerificationUser(email,token);
		verifyRepository.save(verificationUser);
	}

	@Override
    public String validateVerificationToken(String token) {
        VerificationUser verificationToken
                = verifyRepository.findToken(token);

        if (verificationToken == null) {
            return "invalid";
        }

        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
        	log.info("expired");
            verifyRepository.delete(verificationToken);
            return "expired";
        }
        log.info("valid");
        return "valid";
    }

	@Override
	public boolean updatePassword(String token, String pass) {
		log.info(token);
		log.info(pass);
		
		VerificationUser user = mongoTemplate.findOne(Query.query(Criteria.where("token").is(token)), VerificationUser.class);
        Calendar cal = Calendar.getInstance();
        if (user == null) return false;
        else if ((user.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
        	log.info("expired");
            verifyRepository.delete(user);
            return false;
        }
		
		
		String mail = user.getEmail();
		UserLogIn userpass = mongoTemplate.findOne(Query.query(Criteria.where("email").is(mail)), UserLogIn.class);
		userpass.setPassword(pass);
		return true;
	}
    
    

}
