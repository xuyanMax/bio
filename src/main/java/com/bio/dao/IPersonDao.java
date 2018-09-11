package com.bio.dao;

import com.bio.beans.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*只需要写接口即可，不需要写实现，spring的配置文件中会去扫描mapper，
*会自动创建一个代理对象来执行相应的方法，要注意的是: 这个接口中的方法名要和上面mapper映射文件中的id号一样的，
*否则是无法映射到具体的statement上面，会报错。
*/
public interface IPersonDao {
    void insertPerson(Person person);

    //删除指定IdPerson
    void deletePersonByIdPerson(int idPerson);
    //更新某Person
    void updatePerson(Person person);

    List<Person> selectAllPersons(String ID_code);

    //按照IdPerson获取
    Person selectPersonByIdPerson(int idPerson);
    Person selectPersonByID_code(String ID_code);

    int selectPersonsByIdcenter(int idcenter);

}
