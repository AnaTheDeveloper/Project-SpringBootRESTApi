package com.example.SpringBootRESTApi.api;
import javax.validation.Valid;

import com.example.SpringBootRESTApi.model.Person;
import com.example.SpringBootRESTApi.model.ReturnMessage;
import com.example.SpringBootRESTApi.service.PersonService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/person")    //path that client (postman) can use to send queries to the controller

@RestController     //Marks this class as a controller that can process the incoming HTTP requests.

@AllArgsConstructor     //generates a constructor with all the fields that are available
public class PersonController {

    PersonService personService;


    @PostMapping(path = "/post", produces = MediaType.APPLICATION_JSON_VALUE)      //indicates that a function processes a POST request.
    @ResponseBody()
    public ResponseEntity addPerson(@Valid @NonNull @RequestBody Person personToAdd) throws JsonProcessingException {

        boolean doesPersonNotExistResult = personService.checkIfPersonObjectDoesNotExistsInDatabase(personToAdd);

        if(doesPersonNotExistResult == false){

            ReturnMessage returnMessage = new ReturnMessage();
            returnMessage.setReturnMessage("This person already exists.");
            ObjectWriter javaObjectScanner_turnsJavaIntoJSON = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = javaObjectScanner_turnsJavaIntoJSON.writeValueAsString(returnMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
        }
        personService.addPerson(personToAdd);
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setReturnMessage("Added person to the database with an id of : " + personToAdd.getId());
        //Converts java msg back to client into an object then into json
        ObjectWriter javaObjectScanner_turnsJavaIntoJSON = new ObjectMapper().writer().withDefaultPrettyPrinter(); //JSON WRITER
        String json = javaObjectScanner_turnsJavaIntoJSON.writeValueAsString(returnMessage);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @GetMapping (path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)   //indicates that the function processes a GET request.
    @ResponseBody()
    public ResponseEntity getAllPeople() throws JsonProcessingException {
        String everyPersonEntityInDatabase = personService.selectAllPeople();
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setReturnMessage(everyPersonEntityInDatabase);
        ObjectWriter javaObjectScanner_turnsJavaIntoJSON = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = javaObjectScanner_turnsJavaIntoJSON.writeValueAsString(returnMessage);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }


    @GetMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public ResponseEntity getPersonById(@RequestParam(name = "idToSearchWith") String idToSearchWith) throws JsonProcessingException {
        System.out.println("endpoint was hit!!!");//@PathVariable
        String foundPersonEntityById = personService.selectPersonById(idToSearchWith);
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setReturnMessage("Retrieved information: " + foundPersonEntityById);
        ObjectWriter javaObjectScanner_turnsJavaIntoJSON = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = javaObjectScanner_turnsJavaIntoJSON.writeValueAsString(returnMessage);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
    //JWT token

    @DeleteMapping (path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE  )//indicates that a function processes a DELETE request.
    @ResponseBody()//Lets java know we want to return something to the client
    public ResponseEntity deletePersonById(@RequestParam(name = "idToDeleteWith")String idToDeleteWith) throws JsonProcessingException {

         if (idToDeleteWith.equals("2")) {
             ReturnMessage returnMessage = new ReturnMessage();
             returnMessage.setReturnMessage("Need more privileges to delete this account");
             //turns java into object into json
             ObjectWriter javaObjectScanner_turnsJavaIntoJSON = new ObjectMapper().writer().withDefaultPrettyPrinter(); //JSON WRITER
             String json = javaObjectScanner_turnsJavaIntoJSON.writeValueAsString(returnMessage);
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);   //Customising the feed back we get from postman. Was originally 200 okay but it cant be because we didnt delete the value of one because we restricted uit earlier.
         }
         personService.deletePersonById(idToDeleteWith);
         //Return a msg
         ReturnMessage returnMessage = new ReturnMessage();
         returnMessage.setReturnMessage("Deleted Person With ID : " + idToDeleteWith);
        //Converts java msg back to client into an object then into json
         ObjectWriter javaObjectScanner_turnsJavaIntoJSON = new ObjectMapper().writer().withDefaultPrettyPrinter(); //JSON WRITER
         String json = javaObjectScanner_turnsJavaIntoJSON.writeValueAsString(returnMessage);
         return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PatchMapping  (path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)    //indicates that a function processes a PUT request.
    @ResponseBody()
    public ResponseEntity updatePersonById(@Valid @NonNull @RequestBody Person personToUpdate) throws JsonProcessingException {
        if(personToUpdate.getId() < 1){
            ReturnMessage returnMessage = new ReturnMessage();
            returnMessage.setReturnMessage("This person cannot be updated");
            ObjectWriter javaObjectScanner_turnsJavaIntoJSON = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = javaObjectScanner_turnsJavaIntoJSON.writeValueAsString(returnMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
        }
        personService.updatePersonById(personToUpdate);
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setReturnMessage("Updated person in the database with an id of : " + personToUpdate.getId());
        ObjectWriter javaObjectScanner_turnsJavaIntoJSON = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = javaObjectScanner_turnsJavaIntoJSON.writeValueAsString(returnMessage);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }



    /* @Valid - ensures the validation of the whole object in method calls.
       @NonNull - generates a null-check statement for you.
       @RequestBody - used to indicating a method parameter should be bind to the body of the HTTP request. Spring will convert the incomming query from json to java.
     */


}
