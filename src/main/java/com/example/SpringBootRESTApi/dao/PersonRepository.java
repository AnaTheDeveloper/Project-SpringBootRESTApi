package com.example.SpringBootRESTApi.dao;


import com.example.SpringBootRESTApi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository     //marks this interface as a Spring Data JPA repository.
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
