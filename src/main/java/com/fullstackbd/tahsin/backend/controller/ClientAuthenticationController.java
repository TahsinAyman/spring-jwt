package com.fullstackbd.tahsin.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstackbd.tahsin.backend.entity.ApiKey;
import com.fullstackbd.tahsin.backend.entity.Client;
import com.fullstackbd.tahsin.backend.service.ClientAuthenticationService;

@RestController
@RequestMapping("/api/v1/auth/client")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClientAuthenticationController {
	
	@Autowired
	private ClientAuthenticationService clientAuthenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiKey> register(@RequestBody Client client) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(clientAuthenticationService.register(client));
	}
	
	
}
