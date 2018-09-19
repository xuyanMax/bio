package com.bio.beans;

public class Center {
    private int idcenter;
    private String center;
    private Integer idperson;
    private int postcode;
    private int local_num;
    private String admin_name;
    private String admin_tel;
    private Integer current_qtversion;
    private String sup3;

    public Integer getIdperson() {
        return idperson;
    }

    public void setIdperson(Integer idperson) {
        this.idperson = idperson;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public int getIdcenter() {
        return idcenter;
    }

    public void setIdcenter(int idcenter) {
        this.idcenter = idcenter;
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

    public Integer getCurrent_qtversion() {
        return current_qtversion;
    }

    public void setCurrent_qtversion(Integer current_qtversion) {
        this.current_qtversion = current_qtversion;
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
                "idcenter=" + idcenter +
                ", center='" + center + '\'' +
                ", idperson=" + idperson +
                ", postcode=" + postcode +
                ", local_num=" + local_num +
                ", admin_name='" + admin_name + '\'' +
                ", admin_tel='" + admin_tel + '\'' +
                ", current_qtversion=" + current_qtversion +
                ", sup3='" + sup3 + '\'' +
                '}';
    }
}
