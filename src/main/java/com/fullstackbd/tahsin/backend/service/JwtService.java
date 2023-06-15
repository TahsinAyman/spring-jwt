package com.fullstackbd.tahsin.backend.service;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
	String extractUsername(String token);
	<T> T extractClaim(String token, Function<Claims, T> claimsResolver);
	String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
	String generateToken(UserDetails userDetails);
	Boolean isTokenValid(String token, UserDetails userDetails);
	Boolean logout(String token) throws Exception;
	Boolean checkAuthorization(String url, String token);
}
