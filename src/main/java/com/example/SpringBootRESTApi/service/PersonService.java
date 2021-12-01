package com.example.SpringBootRESTApi.service;

import com.example.SpringBootRESTApi.model.Person;
import com.example.SpringBootRESTApi.dao.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service        //makes Spring context be aware of this class as a service.
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;

    public void addPerson(Person person){

        personRepository.save(person);
    }


    public String selectAllPeople(){
        List<Person> listOfEveryPersonEntityInDatabase = personRepository.findAll();
        //todo: comeback to see if casting works.

        StringBuilder everyPersonInDatabaseAsString = new StringBuilder();

        listOfEveryPersonEntityInDatabase.forEach( everyPersonInDatabaseAsString ::  append);

        return everyPersonInDatabaseAsString.toString();
    }

    public String selectPersonById(Person person){

        Optional<Person> personFoundById = personRepository.findById(person.getId());

        if (personFoundById.isPresent() == false) {
           return "ERROR! PERSON NOT FOUND";
        }

        Person personFoundInDatabase = personFoundById.get();
        return personFoundInDatabase.toString();
    }

    public void deletePersonById(Person person){

        personRepository.deleteById(person.getId());
    }

    public void updatePersonById(@Valid Person personToUpdate){
        personRepository.save(personToUpdate);
    }

}
