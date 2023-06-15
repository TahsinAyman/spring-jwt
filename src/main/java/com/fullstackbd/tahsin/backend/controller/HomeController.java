package com.fullstackbd.tahsin.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstackbd.tahsin.backend.model.Message;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {
	@GetMapping("/")
	public ResponseEntity<Message> home() {
		return ResponseEntity.status(HttpStatus.OK).body(
				Message
					.builder()
					.message("Welcome to the Backend API")
					.statusCode(HttpStatus.OK.value())
					.result(true)
					.build()
		);
	}
}
