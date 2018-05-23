package com.bio.service;

import com.bio.beans.Person;
import com.bio.dao.IPersonsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("personService")
public class PersonImpl implements IPersonService{

    @Autowired
    private IPersonsDao personsDao;

    public void setPersonsDao(IPersonsDao personsDao) {
        this.personsDao = personsDao;
    }

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public void removeById(Integer idPerson) {

    }

    @Override
    public void modifyPerson(Person person) {

    }

    @Override
    public List<String> findAllPersonsNames() {
        return null;
    }

    @Override
    public String findPersonNameById(int idPerson) {
        return null;
    }

    @Override
    public List<Person> findAllPersons() {
        return null;
    }

    @Override
    public Person findPersonById() {
        return null;
    }


}
