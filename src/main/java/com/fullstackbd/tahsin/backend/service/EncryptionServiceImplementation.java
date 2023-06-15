package com.fullstackbd.tahsin.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImplementation implements EncryptionService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String encrypt(Object obj) throws Exception {
    	String json = objectMapper.writeValueAsString(obj);        
        return DatatypeConverter.printHexBinary(json.getBytes());
    }

    public <T> T decrypt(String encryptedData, Class<T> clazz) throws Exception {
    	String json = new String(DatatypeConverter.parseHexBinary(encryptedData));
        return objectMapper.readValue(json, clazz);
    }
}
