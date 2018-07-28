package com.JsonGenerator.element;

public class Choice{
    String value;
    String text;

    public Choice(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "value='" + value + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}