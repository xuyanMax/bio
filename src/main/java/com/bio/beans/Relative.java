package com.bio.beans;

public class Relative {

    Integer idrelative;

    Integer idperson1;

    Integer idperson2;

    Integer relationship;

    public Integer getIdrelative() {
        return idrelative;
    }

    public void setIdrelative(Integer idrelative) {
        this.idrelative = idrelative;
    }

    public Integer getIdperson1() {
        return idperson1;
    }

    public void setIdperson1(Integer idperson1) {
        this.idperson1 = idperson1;
    }

    public Integer getIdperson2() {
        return idperson2;
    }

    public void setIdperson2(Integer idperson2) {
        this.idperson2 = idperson2;
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "Relative{" +
                "idrelative=" + idrelative +
                ", idperson1=" + idperson1 +
                ", idperson2=" + idperson2 +
                ", relationship='" + relationship + '\'' +
                '}';
    }
}
