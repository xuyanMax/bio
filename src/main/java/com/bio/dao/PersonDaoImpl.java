package com.bio.dao;

import com.bio.beans.Person;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

// for self-JUnit test only
public class PersonDaoImpl extends JdbcDaoSupport implements IPersonDao {
    @Override
    public void insertPerson(Person person) {
        String sql = "insert into persons(name,age) values(?,?)";
        this.getJdbcTemplate().update(sql, person.getName(), person.getAge());
    }

    @Override
    public void deletePersonByIdPerson(int idPerson) {

    }

    @Override
    public void updatePerson(Person person) {

    }

    @Override
    public List<Person> selectAllPersons() {
        return null;
    }

    @Override
    public Person selectPersonByIdPerson(int idPerson) {
        String sql = "select * from persons where idperson = ?";
        return this.getJdbcTemplate().queryForObject(sql, new PersonRowMapper(), idPerson);

    }

    @Override
    public Person selectPersonByID_code(String ID_code, String name) {
        String sql = "select * from persons where ID_code = ? and name = ?";
        return this.getJdbcTemplate().queryForObject(sql, new PersonRowMapper(), ID_code, name);
    }
}
