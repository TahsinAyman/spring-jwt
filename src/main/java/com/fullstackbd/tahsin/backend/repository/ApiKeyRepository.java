package com.fullstackbd.tahsin.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstackbd.tahsin.backend.entity.ApiKey;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
	Optional<ApiKey> findByKey(String key);
}
