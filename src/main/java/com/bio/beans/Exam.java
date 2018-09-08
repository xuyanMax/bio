package com.bio.beans;

public class Exam {
    Integer idexam;
    String barcode;
    Integer idperson;
    String input_date;
    Integer sup2;

    public Integer getIdexam() {
        return idexam;
    }

    public void setIdexam(Integer idexam) {
        this.idexam = idexam;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getIdperson() {
        return idperson;
    }

    public void setIdperson(Integer idperson) {
        this.idperson = idperson;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public Integer getSup2() {
        return sup2;
    }

    public void setSup2(Integer sup2) {
        this.sup2 = sup2;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "idexam=" + idexam +
                ", barcode='" + barcode + '\'' +
                ", idperson=" + idperson +
                ", input_date='" + input_date + '\'' +
                ", sup2=" + sup2 +
                '}';
    }
}
