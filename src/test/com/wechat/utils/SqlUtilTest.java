package com.wechat.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-03-18 22:49
 * @Version 1.0
 */

public class SqlUtilTest {

    @Test
    public void riskModelValueBothEmpty() {
        String lifetime_risk = "";
        String yfrs_Risk = "";
        Assert.assertEquals(";", SqlUtil.riskModelValue(lifetime_risk, yfrs_Risk));
    }

    @Test
    public void riskModelValueBothNull() {
        String lifetime_risk = null;
        String yfrs_Risk = null;
        Assert.assertEquals(";", SqlUtil.riskModelValue(lifetime_risk, yfrs_Risk));
    }

    @Test
    public void riskModelValueFyrsRiskEmpty() {
        String lifetime_risk = "12312.44";
        String yfrs_Risk = "";
        Assert.assertEquals(";12312.44", SqlUtil.riskModelValue(lifetime_risk, yfrs_Risk));
    }

    @Test
    public void riskModelValueFyrsRiskNull() {
        String lifetime_risk = "12312.44";
        String yfrs_Risk = null;
        Assert.assertEquals(";12312.44", SqlUtil.riskModelValue(lifetime_risk, yfrs_Risk));
    }

    @Test
    public void riskModelValueLifetimeRiskNull() {
        String lifetime_risk = null;
        String yfrs_Risk = "111.1";
        Assert.assertEquals("111.1;", SqlUtil.riskModelValue(lifetime_risk, yfrs_Risk));
    }

    @Test
    public void riskModelValueLifetimeRiskEmpty() {
        String lifetime_risk = "";
        String yfrs_Risk = "111.1";
        Assert.assertEquals("111.1;", SqlUtil.riskModelValue(lifetime_risk, yfrs_Risk));
    }
}