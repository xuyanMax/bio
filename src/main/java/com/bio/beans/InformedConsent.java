package com.bio.beans;

/**
 * @Author: xyx
 * @Date: 2019-04-27 22:20
 * @Version 1.0
 */
public class InformedConsent {

    private String informed_consent1;

    private String informed_consent2;

    public String getInformed_consent1() {
        return informed_consent1;
    }

    public void setInformed_consent1(String informed_consent1) {
        this.informed_consent1 = informed_consent1;
    }

    public String getInformed_consent2() {
        return informed_consent2;
    }

    public void setInformed_consent2(String informed_consent2) {
        this.informed_consent2 = informed_consent2;
    }

    @Override
    public String toString() {
        return "InformedConsent{" +
                "informed_consent1='" + informed_consent1 + '\'' +
                ", informed_consent2='" + informed_consent2 + '\'' +
                '}';
    }
}
