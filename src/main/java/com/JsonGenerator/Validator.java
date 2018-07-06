package com.JsonGenerator;

public class Validator {
    String type;
    String text;

    public Validator(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Validator{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
