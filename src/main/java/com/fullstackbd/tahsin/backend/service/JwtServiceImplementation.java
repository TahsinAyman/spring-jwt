package com.fullstackbd.tahsin.backend.service;


import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fullstackbd.tahsin.backend.entity.Role;
import com.fullstackbd.tahsin.backend.entity.Services;
import com.fullstackbd.tahsin.backend.entity.Token;
import com.fullstackbd.tahsin.backend.entity.User;
import com.fullstackbd.tahsin.backend.repository.TokenRepository;
import com.fullstackbd.tahsin.backend.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
public class JwtServiceImplementation implements JwtService {

	@Value("${jwt.secret_key}")
	private String SECRET_KEY;		
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public String extractUsername(String token) {
		return this.extractClaim(token, Claims::getSubject);
	}
	@Override
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = this.extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(this.getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	private Date extractExpiration(String token) {
		return this.extractClaim(token, Claims::getExpiration);
	}
	private Boolean isTokenExpired(String token) {
		return this.extractExpiration(token).before(new Date());
	}
	@Override
	public Boolean logout(String token) throws Exception  {
		Token jwtToken = tokenRepository.findByToken(token).orElseThrow(() -> new Exception("Token not found"));
		if (SecurityContextHolder.getContext().getAuthentication() != null && jwtToken.getIsValid() == true) {
			jwtToken.setIsValid(false);
			tokenRepository.save(jwtToken);
			SecurityContextHolder.clearContext();
			return true;
		} else {
			return false;
		}
	}
	@Override
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 24))
				.signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
				
	}
	@Override
	public String generateToken(UserDetails userDetails) {
		return this.generateToken(new HashMap<>(), userDetails);
	}
	
	@Cacheable("token")
	@Override
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		String username = this.extractUsername(token);
		Boolean isTokenValid;
		try {			
			Token jwtToken = tokenRepository.findByToken(token).get();
			isTokenValid = jwtToken.getIsValid();
		} catch (Exception e) {
			isTokenValid = false;
		}
		Boolean isUsernameMatches = username.equals(userDetails.getUsername());
		Boolean isTokenExpired = !this.isTokenExpired(token);

		return isUsernameMatches && isTokenExpired && isTokenValid;
	} 
	
	@Cacheable("users")
	@Override
	public Boolean checkAuthorization(String url, String token) {
		String email = this.extractUsername(token);
		User user = userRepository.findByEmail(email).orElse(null);
		if (Objects.nonNull(user)) {
			List<Services> services = user.getServices();
			for (Services service: services) {
				System.out.println(services);
				if (url.matches(service.getUrl().trim())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
