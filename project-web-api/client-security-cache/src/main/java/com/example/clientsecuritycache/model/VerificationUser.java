package com.example.clientsecuritycache.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Document(collection = "Token")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationUser {
	
	private static  final int EXPIRATION_TIME = 5;
    @Id
    private String id;

    private String token;

    private Date expirationTime;

    private String email;

    public VerificationUser(String email, String token) {
        super();
        this.token = token;
        this.email = email;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public void VerificationToken(String token) {
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
    
    

}
