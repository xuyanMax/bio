package com.bio;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JsonGenerate {

    @Test
    public void test1(){
        Student student1 = new Student("xu", 19, "HongXing");
        String[] tools = new String []{"1","2","3"};
        student1.setTools(tools);
        System.out.println(student1);

        Student student2 = new Student ("yan", 20, "Cambridge");
        student2.setTools(tools);

        JSONObject object1 = (JSONObject) JSONObject.toJSON(student1);
        JSONObject object2 = (JSONObject) JSONObject.toJSON(student2);

        System.out.println(JSONObject.toJSONString(student1));
        System.out.println(object1);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        System.out.println(JSONArray.toJSON(students));


    }

    static class Student{
        String name;
        int age;
        String School;
        String[] tools;

        public Student(String name, int age, String school) {
            this.name = name;
            this.age = age;
            School = school;
        }

        public void setTools(String[] tools) {
            this.tools = tools;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", School='" + School + '\'' +
                    ", tools=" + Arrays.toString(tools) +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSchool() {
            return School;
        }

        public void setSchool(String school) {
            School = school;
        }

        public String[] getTools() {
            return tools;
        }
    }
}
