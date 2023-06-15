package com.fullstackbd.tahsin.backend.controller;

import com.fullstackbd.tahsin.backend.entity.Person;
import com.fullstackbd.tahsin.backend.interceptor.PersonControllerInterceptor;
import com.fullstackbd.tahsin.backend.model.Message;
import com.fullstackbd.tahsin.backend.model.SSEPayload;
import com.fullstackbd.tahsin.backend.service.PersonService;
import com.fullstackbd.tahsin.backend.service.SSEPublisher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RestController
@RequestMapping("api/v1/person")
@CrossOrigin(origins = "*")
@Slf4j
public class PersonController implements WebMvcConfigurer {
    @Autowired
    private PersonService personService;

    @Autowired
    private SSEPublisher ssePublisher;
    
  
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new PersonControllerInterceptor());
	}

	@GetMapping("/fetch/page/{page}/{limit}")
    public ResponseEntity<?> fetchAllPersonByPage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {          
		List<Person> persons = personService.fetchAllPerson(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(
                persons
        );
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAllPerson() {	
        List<Person> persons = personService.fetchAllPerson();
        return ResponseEntity.status(HttpStatus.OK).body(
                persons
        );	
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<?> fetchPersonById(@PathVariable("id") Long id) {
	
		try {
            Person person = personService.fetchPersonById(id);
            return ResponseEntity.status(HttpStatus.OK).body(person);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Message.
                            builder().
                            message("ID Not Found").
                            result(false).
                            statusCode(HttpStatus.NOT_FOUND.value()).
                            build()
            );
        }
	
        
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPerson(HttpServletRequest request, @RequestBody Person person) {	
		try {
            Person newPerson = personService.addPerson(person);
            ssePublisher.publishEvent(
                    SSEPayload.<Person>builder()
                            .type("person/add")
                            .payload(newPerson)
                            .body(person)
                            .status(HttpStatus.OK.value())
                            .url(request.getRequestURL().toString())
                            .method("POST")
                            .build()
            );
            return ResponseEntity.status(HttpStatus.OK).body(
                    newPerson
            );
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Message.
                            builder().
                            message("Invalid Credentials").
                            result(false).
                            statusCode(HttpStatus.BAD_REQUEST.value()).
                            build()
            );
        }
	
        
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updatePerson(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody Person person) {
	
		try {
            Person updatedPerson = personService.updatePerson(id, person);
            ssePublisher.publishEvent(
                    SSEPayload.<Person>builder()
                            .type("person/update")
                            .payload(updatedPerson)
                            .body(person)
                            .url(request.getRequestURL().toString())
                            .method("PUT")
                            .status(HttpStatus.OK.value())
                            .query(List.of(id))
                            .build()
            );
            return ResponseEntity.status(HttpStatus.OK).body(
                    updatedPerson
            );
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Message.
                            builder().
                            message("ID Not Found").
                            result(false).
                            statusCode(HttpStatus.NOT_FOUND.value()).
                            build()
            );
        }
	
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePerson(HttpServletRequest request, @PathVariable("id") Long id) {
	
		try {
            Person person = personService.deletePerson(id);
            ssePublisher.publishEvent(
                    SSEPayload.<Person>builder()
                            .type("person/add")
                            .payload(person)
                            .url(request.getRequestURL().toString())
                            .method("DELETE")
                            .query(List.of(id))
                            .build()
            );
            return ResponseEntity.status(HttpStatus.OK).body(
                    person
            );
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Message.
                            builder().
                            message("ID Not Found").
                            result(false).
                            statusCode(HttpStatus.NOT_FOUND.value()).
                            build()
            );
        }
	
    	
    }
}
