package com.bio.beans;

import org.springframework.stereotype.Component;

import java.io.Serializable;

public class Person {
    private int idperson;

    private String name;
    private String gender;
    private int age;
    private String ID_code;
    private String sn_in_center;
    private int idcenter;

    public int getIdcenter() {
        return idcenter;
    }

    public void setIdcenter(int idcenter) {
        this.idcenter = idcenter;
    }

    private String global_sn;
    private int relative;
    private String tel1;

    private String tel2;
    private String barcode;
    private String email;

    public int getRelative() {
        return relative;
    }

    public void setRelative(int relative) {
        this.relative = relative;
    }

    public String getGlobal_sn() {
        return global_sn;
    }

    public void setGlobal_sn(String global_sn) {
        this.global_sn = global_sn;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSn_in_center() {
        return sn_in_center;
    }

    public void setSn_in_center(String sn_in_center) {
        this.sn_in_center = sn_in_center;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdperson(int idperson) {
        this.idperson = idperson;
    }

    public int getIdperson() {
        return idperson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setID_code(String ID_code) {
        this.ID_code = ID_code;
    }

    public int getAge() {
        return age;
    }

    public String getID_code() {
        return ID_code;
    }

    @Override
    public String toString() {
        return "person["
                +"name=" + name
                +", gender=" + gender
                +", age=" + age
//                +", ID_code=" + ID_code
                +", sn_in_center=" + sn_in_center
                +", global_sn=" + global_sn
//                +", idcenter=" + idcenter
//                +", identity=" + identity
                +", barcode=" + barcode
                +", tel1=" + tel1
                +", tel2=" + tel2
                +", email=" + email
                +", relative=" + relative
                +"\n]";
    }



}