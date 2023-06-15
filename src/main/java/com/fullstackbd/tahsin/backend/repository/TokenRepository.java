package com.fullstackbd.tahsin.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstackbd.tahsin.backend.entity.Token;
import com.fullstackbd.tahsin.backend.entity.User;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Optional<Token> findByToken(String token);

	Optional<Token> findByUser(User user);
}
