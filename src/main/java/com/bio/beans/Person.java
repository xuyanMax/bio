package com.bio.beans;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Person implements Serializable {
    private Integer idPerson;
    private String name;
    private String gender;
    private int age;
    private int ID_code;
    private String sn_in_center;
    private String global_sn;
    private int idcenter;
    private String identity;
    private String barcode;
    private String tel1;
    private String tel2;
    private String email;
    private int relative;

    public Person() {
        super();
    }

    public Person(Integer idPerson, String name, String gender, int age,
                  int ID_code, String sn_in_center, String global_sn,
                  int idcenter, String identity, String barcode, String tel1,
                  String tel2, String email, int relative) {
        this.idPerson = idPerson;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.ID_code = ID_code;
        this.sn_in_center = sn_in_center;
        this.global_sn = global_sn;
        this.idcenter = idcenter;
        this.identity = identity;
        this.barcode = barcode;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.email = email;
        this.relative = relative;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getID_code() {
        return ID_code;
    }

    public void setID_code(int ID_code) {
        this.ID_code = ID_code;
    }

    public String getSn_in_center() {
        return sn_in_center;
    }

    public void setSn_in_center(String sn_in_center) {
        this.sn_in_center = sn_in_center;
    }

    public String getGlobal_sn() {
        return global_sn;
    }

    public void setGlobal_sn(String global_sn) {
        this.global_sn = global_sn;
    }

    public int getIdcenter() {
        return idcenter;
    }

    public void setIdcenter(int idcenter) {
        this.idcenter = idcenter;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRelative() {
        return relative;
    }

    public void setRelative(int relative) {
        this.relative = relative;
    }
    @Override
    public String toString() {
        return "person [idperson=" + idPerson
                +", name=" + name
                +", gender=" + gender
                +", age=" + age
                +", ID_code=" + ID_code
                +", sn_in_center=" + sn_in_center
                +", global_sn=" + global_sn
                +", idcenter=" + idcenter
                +", identity=" + identity
                +", barcode=" + barcode
                +", tel1=" + tel1
                +", tel2=" + tel2
                +", email=" + email
                +", relative=" + relative
                +"]";
    }
}