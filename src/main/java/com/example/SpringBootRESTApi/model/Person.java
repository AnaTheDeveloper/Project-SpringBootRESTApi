package com.example.SpringBootRESTApi.model;

import lombok.Data;

import javax.persistence.*;

@Data  //generates all the boilerplate (getters/setters/tostring)
@Entity     //shows that the class is a persistent Java class/represents the table
//@Table - Defining class name as table name
public class Person {

    @Id     //Represents the primary key

   // @Column(name="namecolumn") - defines the column in the database that maps the annotated field.
    public Integer id;
    public String name;


}
