package com.bio.service;

import com.bio.beans.Person;
import com.bio.dao.IPersonsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonImpl implements IPersonService{

    @Autowired
    private IPersonsDao personsDao;

    public void setPersonsDao(IPersonsDao personsDao) {
        this.personsDao = personsDao;
    }

    @Override
    public void addPerson(Person person) {
        personsDao.insertPerson(person);
    }

    @Override
    public void removeById(Integer idPerson) {
        personsDao.deletePersonByIdPerson(idPerson);
    }

    @Override
    public void modifyPerson(Person person) {
        personsDao.updatePerson(person);
    }

    @Override
    public List<String> findAllPersonsNames() {
        List<String> res = personsDao.selectAllPersons().
                stream().
                map(Person::getName).
                collect(Collectors.toList());
        return res;
    }

    @Override
    public String findPersonNameById(int idPerson) {
         return personsDao.selectPersonByIdPerson(idPerson).getName();
    }

    @Override
    public List<Person> findAllPersons() {
        return personsDao.selectAllPersons();
    }

    @Override
    public Person findPersonById(int id) {
        return personsDao.selectPersonByIdPerson(id);
    }


}
