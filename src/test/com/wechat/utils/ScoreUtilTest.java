package com.wechat.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-23 00:08
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")

public class ScoreUtilTest {

    @Test
    public void getRisk() {
        String val = "21.5185965";
        Double d = 21.5185965;
        Assert.assertEquals(d, ScoreUtil.getRisk(val));
    }

    @Test
    public void getRiskEmpty() {
        String val = "";
        Double d = new Double(0);
        Assert.assertEquals(d, ScoreUtil.getRisk(val));
    }

    @Test
    public void evaluateRiskHigh() {
        double target = 120, low = -100, high = 100;
        Assert.assertEquals(1, ScoreUtil.evaluateRisk(low, high, target));
    }

    @Test
    public void evaluateRiskMiddle() {
        double target = 10, low = -100, high = 100;
        Assert.assertEquals(0, ScoreUtil.evaluateRisk(low, high, target));
    }

    @Test
    public void evaluateRiskLow() {
        double target = -120, low = -100, high = 100;
        Assert.assertEquals(-1, ScoreUtil.evaluateRisk(low, high, target));
    }

    @Test
    public void displayQuestionnaireResult() {
        List<String> missing = new ArrayList<>();

        missing.add("whom");
        missing.add("from");
        missing.add("tomb");


        System.out.println(missing.stream().reduce((a, b) -> (a + ", " + b)).get());
        System.out.println(ScoreUtil.displayQuestionnaireResult(100, "10.919231", "1231.199191", 1, missing, "10", "100"));

    }
}