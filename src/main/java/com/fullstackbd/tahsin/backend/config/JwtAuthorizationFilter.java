package com.fullstackbd.tahsin.backend.config;

import java.io.IOException;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fullstackbd.tahsin.backend.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final String urlPattern = "/api/v1/auth/.*";
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		String requestUrl = request.getRequestURI();
		if (requestUrl.matches(urlPattern)) {
			filterChain.doFilter(request, response);
			return;
		} else {
			filter(request, response, filterChain);
		}
	}

	private void filter(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
			System.out.println("AUTHORIZATION");
			String authHeader = request.getHeader("AUTHORIZATION");
			String token = authHeader.substring(7);
			Boolean isAuthorized = jwtService.checkAuthorization(request.getRequestURI(), token);
			if (isAuthorized) {
				filterChain.doFilter(request, response);
				return;
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		}
	}

}
