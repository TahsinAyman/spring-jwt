package com.fullstackbd.tahsin.backend.service;

import com.fullstackbd.tahsin.backend.model.AuthenticationRequest;
import com.fullstackbd.tahsin.backend.model.AuthenticationResponse;
import com.fullstackbd.tahsin.backend.model.RegisterRequest;

import jakarta.mail.MessagingException;

public interface UserAuthenticationService {
	String uniqueEncryptedTextGenerator();
	void register(RegisterRequest request, String emailVerifyUrl) throws MessagingException, Exception;
	AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;
	Boolean logout(String token) throws Exception;
	void verifyEmail(String token) throws Exception;

}
