package com.fullstackbd.tahsin.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fullstackbd.tahsin.backend.entity.ApiKey;
import com.fullstackbd.tahsin.backend.repository.ApiKeyRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiKeyServiceImplementation implements ApiKeyService {

	@Autowired
	private ApiKeyRepository apiKeyRepository;
	
	@Cacheable("apiKeys")
	@Override
	public Boolean check(String apiAuthKey) {
		Optional<ApiKey> apiKey = apiKeyRepository.findByKey(apiAuthKey);
		log.error(apiKey.toString());
		if (apiKey.isEmpty()) {
			return false;
		} else {
			Boolean isLocked = apiKey.get().getIsLocked();
			if (isLocked) {
				return false;
			} else {
				return true;
			}
		}
	}

}
