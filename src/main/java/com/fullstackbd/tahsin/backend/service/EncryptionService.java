package com.fullstackbd.tahsin.backend.service;



public interface EncryptionService {
	String encrypt(Object obj) throws Exception;

	<T> T decrypt(String encryptedData, Class<T> clazz) throws Exception;
}
