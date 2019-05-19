package com.bio.beans;

/**
 * @Author: xyx
 * @Date: 2019-05-16 09:49
 * @Version 1.0
 */
public class Json {

    private int id;

    private double sqlselect_score;

    private String json;

    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSqlselect_score() {
        return sqlselect_score;
    }

    public void setSqlselect_score(double sqlselect_score) {
        this.sqlselect_score = sqlselect_score;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Json{" +
                "id=" + id +
                ", sqlselect_score=" + sqlselect_score +
                ", json='" + json + '\'' +
                ", version=" + version +
                '}';
    }
}
