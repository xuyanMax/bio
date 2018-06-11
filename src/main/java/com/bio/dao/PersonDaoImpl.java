package com.bio.dao;

import com.bio.beans.Person;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

// 1. used for Spring IPersonService's implementation
// 2. used for JUnit test
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
        String sql = "select * from persons where ID_code = ? and name = ? limit 1";
        return this.getJdbcTemplate().queryForObject(sql, new PersonRowMapper(), ID_code, name);
    }
}
