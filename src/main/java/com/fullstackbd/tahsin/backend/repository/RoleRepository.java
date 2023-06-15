package com.fullstackbd.tahsin.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstackbd.tahsin.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
