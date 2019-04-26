package com.bio.beans;

/**
 * @Author: xyx
 * @Date: 2019-02-20 23:58
 * @Version 1.0
 */
public class Qtnaireversion_riskmodel {

    private int id;

    private int qtnaire_version;

    private int idriskmodel;

    private String lifetime_min;

    private String lifetime_max;

    private String fyrs_min;

    private String fyrs_max;

    private String low_risk;

    private String high_risk;


    @Override
    public String toString() {
        return "Qtnaireversion_riskmodel{" +
                "id=" + id +
                ", qtnaire_version=" + qtnaire_version +
                ", idriskmodel=" + idriskmodel +
                ", lifetime_min='" + lifetime_min + '\'' +
                ", lifetime_max='" + lifetime_max + '\'' +
                ", fyrs_min='" + fyrs_min + '\'' +
                ", fyrs_max='" + fyrs_max + '\'' +
                ", low_risk='" + low_risk + '\'' +
                ", high_risk='" + high_risk + '\'' +
                '}';
    }

    public String getLow_risk() {
        return low_risk;
    }

    public void setLow_risk(String low_risk) {
        this.low_risk = low_risk;
    }

    public String getHigh_risk() {
        return high_risk;
    }

    public void setHigh_risk(String high_risk) {
        this.high_risk = high_risk;
    }

    public String getLifetime_min() {
        return lifetime_min;
    }

    public void setLifetime_min(String lifetime_min) {
        this.lifetime_min = lifetime_min;
    }

    public String getLifetime_max() {
        return lifetime_max;
    }

    public void setLifetime_max(String lifetime_max) {
        this.lifetime_max = lifetime_max;
    }

    public String getFyrs_min() {
        return fyrs_min;
    }

    public void setFyrs_min(String fyrs_min) {
        this.fyrs_min = fyrs_min;
    }

    public String getFyrs_max() {
        return fyrs_max;
    }

    public void setFyrs_max(String fyrs_max) {
        this.fyrs_max = fyrs_max;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQtnaire_version() {
        return qtnaire_version;
    }

    public void setQtnaire_version(int qtnaire_version) {
        this.qtnaire_version = qtnaire_version;
    }

    public int getIdriskmodel() {
        return idriskmodel;
    }

    public void setIdriskmodel(int idriskmodel) {
        this.idriskmodel = idriskmodel;
    }

}
