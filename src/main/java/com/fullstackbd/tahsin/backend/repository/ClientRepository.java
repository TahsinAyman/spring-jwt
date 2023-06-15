package com.fullstackbd.tahsin.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstackbd.tahsin.backend.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
