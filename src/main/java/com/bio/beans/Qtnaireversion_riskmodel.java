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

    @Override
    public String toString() {
        return "Qtnaireversion_riskmodel{" +
                "id=" + id +
                ", qtnaire_version=" + qtnaire_version +
                ", idriskmodel=" + idriskmodel +
                '}';
    }
}
