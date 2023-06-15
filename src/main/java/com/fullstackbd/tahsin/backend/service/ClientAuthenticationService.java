package com.fullstackbd.tahsin.backend.service;

import com.fullstackbd.tahsin.backend.entity.ApiKey;
import com.fullstackbd.tahsin.backend.entity.Client;

public interface ClientAuthenticationService {

	ApiKey register(Client client) throws Exception;

}
