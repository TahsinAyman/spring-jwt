package com.fullstackbd.tahsin.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.fullstackbd.tahsin.backend.entity.ApiKey;
import com.fullstackbd.tahsin.backend.entity.Client;
import com.fullstackbd.tahsin.backend.repository.ApiKeyRepository;
import com.fullstackbd.tahsin.backend.repository.ClientRepository;

@Service
public class ClientAuthenticationServiceImplementation implements ClientAuthenticationService {

	@Autowired
	private EncryptionService encryptionService;
	@Autowired
	private ApiKeyRepository apiKeyRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	@CacheEvict(value = "apiKeys", allEntries = true)
	@Override
	public ApiKey register(Client client) throws Exception {
		String apiKey = encryptionService.encrypt(client);
		Client savedClient = clientRepository.save(client);
		ApiKey key = ApiKey
				.builder()
				.key(apiKey)
				.isLocked(false)
				.client(savedClient)
				.build();
		return apiKeyRepository.save(key);
	}
	
}
