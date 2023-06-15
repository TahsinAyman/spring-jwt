package com.fullstackbd.tahsin.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstackbd.tahsin.backend.model.AuthenticationRequest;
import com.fullstackbd.tahsin.backend.model.AuthenticationResponse;
import com.fullstackbd.tahsin.backend.model.Message;
import com.fullstackbd.tahsin.backend.model.RegisterRequest;
import com.fullstackbd.tahsin.backend.service.UserAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/auth/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserAuthenticationController {
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	@PostMapping("/register")
	public ResponseEntity<Message> register(HttpServletRequest request, @RequestBody RegisterRequest user) {
        String emailVerifyUrl = request.getRequestURL().toString().replace(request.getContextPath() + request.getServletPath(), "") + "/api/v1/auth/user/verify-email/";
        try {
			userAuthenticationService.register(user, emailVerifyUrl);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
					Message
						.builder()
						.message(e.toString())
						.statusCode(HttpStatus.FORBIDDEN.value())
						.result(false)
						.build()
			);
		}
		return ResponseEntity.status(HttpStatus.OK).body(
				Message
					.builder()
					.message("A Email Verification Link is sent to your email")
					.statusCode(HttpStatus.OK.value())
					.result(true)
					.build()
		);
	}
	
	
	@GetMapping("/verify-email/{token}")
	public String verifyEmail(@PathVariable("token") String token) {
		try {
			log.info(token);
			userAuthenticationService.verifyEmail(token);
			return "Email Verified";
		} catch (Exception e) {			
			return e.getMessage();
		}
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(userAuthenticationService.authenticate(request));
	}
	
	@GetMapping("/logout") 
	public Boolean logout(@RequestHeader("AUTHORIZATION") String authHeader) throws Exception {
		if (!authHeader.startsWith("Bearer ")) {			
			throw new Exception("Token Not Found");
		}
		String token = authHeader.substring(7);
		return userAuthenticationService.logout(token);
	}
	
	
	@GetMapping("/")
	public String root() {
		return "Implement User Authentication Right below theese routes";
	}
} 
