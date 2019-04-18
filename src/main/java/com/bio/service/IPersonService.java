package com.bio.service;

import com.bio.beans.Person;

import java.util.List;

public interface IPersonService {
    void addPerson(Person person);

    void removeByIdperson(Integer idPerson);

    void modifyPerson(Person person);

    List<Person> findAllPersons(String ID_code);

    List<Person> findAllPersonsByCenterId(Integer idcenter);

    Person findPersonByIdperson(int id);

    Person findPersonByID_code(String ID_code);

    Person findPersonByID_codeAndIdcenter(String ID_code, Integer idcenter);

    int countPersonsByIdCenter(int idcenter);

    String findCenterAdminInfo(Integer idperson);

}