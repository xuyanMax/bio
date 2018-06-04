package com.bio.service;

import com.bio.beans.Person;

import java.util.List;

public interface IPersonService {
    void addPerson(Person person);
    void removeById(Integer idPerson);
    void modifyPerson(Person person);

    //List<String> findAllPersonsNames();
//    String findPersonNameById(int idPerson);

    List<Person> findAllPersons();
    Person findPersonById(int id);
    Person findPersonByID_code(String ID_code, String name);

}