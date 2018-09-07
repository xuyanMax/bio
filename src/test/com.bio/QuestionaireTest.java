package com.bio;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class QuestionaireTest {
    @Test
    public void test(){
        String str = "您的民族是：_^[\\u4e00-\\u9fa5]+$";
        System.out.println(str.indexOf("#"));
        String[] strings = {"11","1123","21312"};
        List<String> list = Arrays.asList(strings);
        Arrays.stream(strings).forEach(System.out::println);
    }
}
