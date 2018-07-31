package com.bio;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Person;
import com.bio.service.IPersonService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBTest {
    private IPersonService personService;
    @Before
    public void test0(){
        String resource = "junit-applicationContext.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(resource);
        personService = (IPersonService) ac.getBean("personService");

    }
    @Test
    public void test1(){
        Person person = new Person();
        person.setName("hdv");
        person.setAge(18);
        personService.addPerson(person);
        System.out.println(person);
    }
    @Test
    public void test2(){
        String id = "13010419920518241X";
        Person person = personService.findPersonByID_code(PersonInfoUtils.md5(id), "sasa");
        Person person1 = personService.findPersonById(1);
        System.out.println(person1);
        System.out.println(person);

    }

}