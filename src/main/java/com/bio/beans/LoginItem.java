package com.bio.beans;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

public class LoginItem {
    private int idlogin;
    private int idperson;
    private String time;
    private String ip;
    private String sup2;
    private String sup3;

    public int getIdlogin() {
        return idlogin;
    }

    public void setIdlogin(int idlogin) {
        this.idlogin = idlogin;
    }

    public int getIdperson() {
        return idperson;
    }

    public void setIdperson(int idperson) {
        this.idperson = idperson;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSup2() {
        return sup2;
    }

    public void setSup2(String sup2) {
        this.sup2 = sup2;
    }

    public String getSup3() {
        return sup3;
    }

    public void setSup3(String sup3) {
        this.sup3 = sup3;
    }
}
