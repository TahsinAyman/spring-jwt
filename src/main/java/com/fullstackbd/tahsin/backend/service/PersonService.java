package com.fullstackbd.tahsin.backend.service;

import com.fullstackbd.tahsin.backend.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> fetchAllPerson();

    Person addPerson(Person person);

    List<Person> fetchAllPerson(Integer page, Integer limit);

    Person updatePerson(Long id, Person person);

    Person deletePerson(Long id);

    Person fetchPersonById(Long id);
}
