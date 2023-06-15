package com.fullstackbd.tahsin.backend.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fullstackbd.tahsin.backend.service.ApiKeyService;

import java.util.Objects;
import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {
	private final String urlPattern = "/api/v1/auth/client/.*";
	
	@Autowired
	private ApiKeyService apiKeyService;
	
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
	) throws ServletException, IOException {	
		String requestUrl = request.getRequestURI();
		log.warn(requestUrl);
		if (requestUrl.matches(urlPattern) || requestUrl.startsWith("/api/v1/auth/user/verify-email/")) {
			filterChain.doFilter(request, response);
			return;
		} else {
			log.error("API KEY");
			filter(request, response, filterChain);
		}
		
	}
	
	
	private void filter(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		String apiAuthKey = request.getHeader("x-api-key");
		if (Objects.nonNull(apiAuthKey) && apiKeyService.check(apiAuthKey)) {
			filterChain.doFilter(request, response);
			return;
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
}
