package com.bio.beans;

public class Center {
    private int centerid;
    private String center;
    private int idperson;

    public int getIdperson() {
        return idperson;
    }

    public void setIdperson(int idperson) {
        this.idperson = idperson;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    private int postcode;
    private int local_num;
    private String admin_name;
    private String admin_tel;
    private int sup2;
    private String sup3;

    public int getCenterid() {
        return centerid;
    }

    public void setCenterid(int centerid) {
        this.centerid = centerid;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public int getLocal_num() {
        return local_num;
    }

    public void setLocal_num(int local_num) {
        this.local_num = local_num;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_tel() {
        return admin_tel;
    }

    public void setAdmin_tel(String admin_tel) {
        this.admin_tel = admin_tel;
    }

    public int getSup2() {
        return sup2;
    }

    public void setSup2(int sup2) {
        this.sup2 = sup2;
    }

    public String getSup3() {
        return sup3;
    }

    public void setSup3(String sup3) {
        this.sup3 = sup3;
    }

    @Override
    public String toString() {
        return "Center{" +
                "centerid=" + centerid +
                ", center='" + center + '\'' +
                ", idperson=" + idperson +
                ", postcode=" + postcode +
                ", local_num=" + local_num +
                ", admin_name='" + admin_name + '\'' +
                ", admin_tel='" + admin_tel + '\'' +
                ", sup2=" + sup2 +
                ", sup3='" + sup3 + '\'' +
                '}';
    }
}
