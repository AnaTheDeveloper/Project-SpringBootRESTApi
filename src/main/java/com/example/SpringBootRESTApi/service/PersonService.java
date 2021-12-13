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

    public void addPerson(@Valid Person personToUpdate){

        personRepository.save(personToUpdate);
    }


    public String selectAllPeople(){
        List<Person> listOfEveryPersonEntityInDatabase = personRepository.findAll();

        StringBuilder everyPersonInDatabaseAsString = new StringBuilder();

        listOfEveryPersonEntityInDatabase.forEach( everyPersonInDatabaseAsString ::  append);

        return everyPersonInDatabaseAsString.toString();
    }

    public String selectPersonById(String idToSearchWith){
        Integer idToSearch = Integer.valueOf(idToSearchWith);
        Optional<Person> personFoundById = personRepository.findById(idToSearch);

        if (personFoundById.isPresent() == false) {
           return "ERROR! PERSON NOT FOUND";
        }

        Person personFoundInDatabase = personFoundById.get();
        return personFoundInDatabase.toString();
    }

    public String deletePersonById(String idToDeleteWith){

        Integer idToSearch = Integer.valueOf(idToDeleteWith);
        Optional<Person> personFoundById = personRepository.findById(idToSearch);

        if (personFoundById.isPresent() == false) {
            return "ERROR! PERSON NOT FOUND";
        }

        Person personFoundInDatabase = personFoundById.get();
        personRepository.delete(personFoundInDatabase);

        return "Person Deleted";

    }

    public void updatePersonById(@Valid Person personToUpdate){

        personRepository.save(personToUpdate);
    }

    public boolean checkIfPersonObjectDoesNotExistsInDatabase(Person person) {
        Optional<Person> personObject_mightBeEmpty = personRepository.findById((person.getId()));

        if (personObject_mightBeEmpty.isEmpty()) {
            return true;
        } else {
            return false;
        }


    }

}
