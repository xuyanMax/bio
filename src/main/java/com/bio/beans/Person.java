package com.bio.beans;

public class Person {

    private Integer idperson;
    private String name;
    private String gender;
    private int age;
    private String original_ID_code;
    private String ID_code;
    private String ID_code_cut;
    private String sn_in_center;
    private String global_sn;
    private int idcenter;
    private String email;
    private int relative;
    private String tel1;
    private String tel2;
    private String barcode;

    public Integer getIdperson() {
        return idperson;
    }

    public void setIdperson(Integer idperson) {
        this.idperson = idperson;
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

    public String getOriginal_ID_code() {
        return original_ID_code;
    }

    public void setOriginal_ID_code(String original_ID_code) {
        this.original_ID_code = original_ID_code;
    }

    public String getID_code() {
        return ID_code;
    }

    public void setID_code(String ID_code) {
        this.ID_code = ID_code;
    }

    public String getID_code_cut() {
        return ID_code_cut;
    }

    public void setID_code_cut(String ID_code_cut) {
        this.ID_code_cut = ID_code_cut;
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

    @Override
    public String toString() {
        return "Person{" +
                "idperson=" + idperson +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", original_ID_code='" + original_ID_code + '\'' +
                ", ID_code='" + ID_code + '\'' +
                ", ID_code_cut='" + ID_code_cut + '\'' +
                ", sn_in_center='" + sn_in_center + '\'' +
                ", global_sn='" + global_sn + '\'' +
                ", idcenter=" + idcenter +
                ", email='" + email + '\'' +
                ", relative=" + relative +
                ", tel1='" + tel1 + '\'' +
                ", tel2='" + tel2 + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}