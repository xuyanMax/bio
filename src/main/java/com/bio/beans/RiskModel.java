package com.bio.beans;

/**
 * @Author: xyx
 * @Date: 2019-02-21 00:45
 * @Version 1.0
 */
public class RiskModel {

    private int idriskmodel;

    private String modelname;

    private String sqlselect_factor;

    private String sqlselect_risk;

    public String getSqlselect_risk() {
        return sqlselect_risk;
    }

    public void setSqlselect_risk(String sqlselect_risk) {
        this.sqlselect_risk = sqlselect_risk;
    }

    public int getIdriskmodel() {
        return idriskmodel;
    }

    public void setIdriskmodel(int idriskmodel) {
        this.idriskmodel = idriskmodel;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getSqlselect_factor() {
        return sqlselect_factor;
    }

    public void setSqlselect_factor(String sqlselect_factor) {
        this.sqlselect_factor = sqlselect_factor;
    }

    @Override
    public String toString() {
        return "RiskModel{" +
                "idriskmodel=" + idriskmodel +
                ", modelname='" + modelname + '\'' +
                ", sqlselect_factor='" + sqlselect_factor + '\'' +
                ", sqlselect_risk='" + sqlselect_risk + '\'' +
                '}';
    }
}
