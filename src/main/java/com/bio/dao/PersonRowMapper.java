package com.bio.dao;

import com.bio.beans.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*for JUnit test*/
public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        person.setIdperson(resultSet.getInt("idperson"));
        person.setAge(resultSet.getInt("age"));
        person.setName(resultSet.getString("name"));
        person.setID_code(resultSet.getString("ID_code"));
        person.setTel1(resultSet.getString("tel1"));
        person.setGender(resultSet.getString("gender"));
        person.setRelative(resultSet.getInt("relative"));
        return person;
    }
}
