package com.bio.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-05-21 09:25
 * @Version 1.0
 */
public class SurveyUtilTest {

    @Test
    public void test1() {
        String str = " question7";
        System.out.println(SurveyUtil.parseIdquestion(str));
    }


    @Test
    public void test2() {
        String str = "question7 ";
        System.out.println(SurveyUtil.parseIdquestion(str));
    }
}