package com.bio.service;

import com.bio.beans.Person;

import java.util.List;

public interface IPersonService {
    void addPerson(Person person);
    void removeById(Integer idPerson);
    void modifyPerson(Person person);
    List<Person> findAllPersons(String ID_code);
    Person findPersonById(int id);
    Person findPersonByID_code(String ID_code);
    Person findPersonByID_codeAndIdcenter(String ID_code, Integer idcenter);
    int countPersonsByIdCenter(int idcenter);

}