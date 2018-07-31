package com.bio;
import org.junit.Test;

import java.time.LocalDate;

public class Java8DateTest {
    @Test
    public void test(){
        System.out.println(LocalDate.now().toString().replace("-", ""));
    }
}
