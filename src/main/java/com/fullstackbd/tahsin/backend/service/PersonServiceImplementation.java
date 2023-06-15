package com.fullstackbd.tahsin.backend.service;

import com.fullstackbd.tahsin.backend.entity.Person;
import com.fullstackbd.tahsin.backend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class PersonServiceImplementation implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> fetchAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> fetchAllPerson(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return personRepository.findAll(pageable).getContent();
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        Person oldPerson = personRepository.findById(id).get();
        if (Objects.nonNull(oldPerson.getName())) {
            oldPerson.setName(person.getName());
        }
        if (Objects.nonNull(oldPerson.getAge())) {
            oldPerson.setAge(person.getAge());
        }
        return personRepository.save(oldPerson);
    }

    @Override
    public Person deletePerson(Long id) {
        Person deletedPerson = personRepository.findById(id).get();
        personRepository.deleteById(id);
        return deletedPerson;
    }

    @Override
    public Person fetchPersonById(Long id) {
        Person person = personRepository.findById(id).get();
        return person;
    }
}
