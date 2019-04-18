package com.bio.service.impl;

import com.bio.beans.Center;
import com.bio.beans.Person;
import com.bio.dao.ICenterDao;
import com.bio.dao.IPersonDao;
import com.bio.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*could be used for jdbc JUnit test*/
/*could be used for Spring, must have a implementation of service*/
@Service
public class PersonImpl implements IPersonService {

    @Autowired
    private IPersonDao personDao;//personDao要与jdbcApplicationContext.xml中的 <bean id="personDao" 名称一致

    @Autowired
    private ICenterDao centerDao;

    @Override
    public List<Person> findAllPersonsByCenterId(Integer idcenter) {
        return personDao.selectAllPersonsByIdCenter(idcenter);
    }

    @Override
    public void addPerson(Person person) {
        personDao.insertPerson(person);
    }

    public void setPersonsDao(IPersonDao personsDao) {
        this.personDao = personsDao;
    }

    @Override
    public void removeByIdperson(Integer idPerson) {
        personDao.deletePersonByIdPerson(idPerson);
    }

    @Override
    public void modifyPerson(Person person) {
        personDao.updatePerson(person);
    }

    @Override
    public List<Person> findAllPersons(String ID_code) {
        return personDao.selectAllPersons(ID_code);
    }

    @Override
    public Person findPersonByIdperson(int idperson) {
        return personDao.selectPersonByIdPerson(idperson);
    }

    @Override
    public Person findPersonByID_code(String ID_code) {
        return personDao.selectPersonByID_code(ID_code);
    }

    @Override
    public Person findPersonByID_codeAndIdcenter(String ID_code, Integer idcenter) {
        return personDao.selectPersonByIdAndIdcenter(ID_code, idcenter);
    }

    @Override
    public int countPersonsByIdCenter(int idcenter) {
        return personDao.selectPersonsByIdcenter(idcenter);
    }

    @Override
    public String findCenterAdminInfo(Integer idperson) {
        Person p = personDao.selectPersonByIdPerson(idperson);
        int idcenter = p.getIdcenter();
        Center center = centerDao.selectCenterIdById(idcenter);
        StringBuilder builder = new StringBuilder();
        builder.append("您的单位管理员姓名为:");
        if (center.getAdmin_name() != null) {
            builder.append(center.getAdmin_name()).append(", ");
        }
        if (center.getAdmin_tel() != null) {
            builder.append(center.getAdmin_tel());
        }
        return builder.toString();
    }
}
