package com.bio.dao;

import com.bio.beans.Person;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IPersonsDao {
    void insertPerson(Person person );

    //删除指定IdPerson
    void deletePersonByIdPerson(int idPerson);
    //更新某Person
    void updatePerson(Person person);

    List<Person> selectAllPersons();

    //按照IdPerson获取
    Person selectPersonByIdPerson(int idPerson);
}
