package com.fullstackbd.tahsin.backend.repository;

import com.fullstackbd.tahsin.backend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}