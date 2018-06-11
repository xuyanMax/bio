package com.bio.service;

import com.bio.beans.Person;
import com.bio.dao.IPersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service/*used for jdbc JUnit test*/
public class PersonImpl implements IPersonService{

    @Autowired
    private IPersonDao personDao;//personDao要与jdbcApplicationContext.xml中的 <bean id="personDao" 名称一致

    @Override
    public void addPerson(Person person) {
        personDao.insertPerson(person);
    }

    public void setPersonsDao(IPersonDao personsDao) {
        this.personDao = personsDao;
    }

    @Override
    public void removeById(Integer idPerson) {
        personDao.deletePersonByIdPerson(idPerson);
    }

    @Override
    public void modifyPerson(Person person) {
        personDao.updatePerson(person);
    }

//    @Override
//    public List<String> findAllPersonsNames() {
//        List<String> res = personDao.selectAllPersons().
//                stream().
//                map(Person::getName).
//                collect(Collectors.toList());
//        return res;
//    }

//    @Override
//    public String findPersonNameById(int idPerson) {
//         return personDao.selectPersonByIdPerson(idPerson).getName();
//    }

    @Override
    public List<Person> findAllPersons() {
        return personDao.selectAllPersons();
    }

    @Override
    public Person findPersonById(int idperson) {
        return personDao.selectPersonByIdPerson(idperson);
    }

    @Override
    public Person findPersonByID_code(String ID_code, String name) {
        return personDao.selectPersonByID_code(ID_code, name);
    }
}
