package com.bio.service;

import com.bio.beans.Person;
import com.bio.dao.ICenterDao;
import com.bio.dao.IPersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonImpl implements IPersonService{

    @Autowired
    private IPersonDao personsDao;

    @Override
    public void addPerson(Person person) {
        personsDao.insertPerson(person);
    }

    public void setPersonsDao(IPersonDao personsDao) {
        this.personsDao = personsDao;
    }

    @Override
    public void removeById(Integer idPerson) {
        personsDao.deletePersonByIdPerson(idPerson);
    }

    @Override
    public void modifyPerson(Person person) {
        personsDao.updatePerson(person);
    }

//    @Override
//    public List<String> findAllPersonsNames() {
//        List<String> res = personsDao.selectAllPersons().
//                stream().
//                map(Person::getName).
//                collect(Collectors.toList());
//        return res;
//    }

//    @Override
//    public String findPersonNameById(int idPerson) {
//         return personsDao.selectPersonByIdPerson(idPerson).getName();
//    }

    @Override
    public List<Person> findAllPersons() {
        return personsDao.selectAllPersons();
    }

    @Override
    public Person findPersonById(int idperson) {
        return personsDao.selectPersonByIdPerson(idperson);
    }

    @Override
    public Person findPersonByID_code(String ID_code, String name) {
        return personsDao.selectPersonByID_code(ID_code, name);
    }
}
