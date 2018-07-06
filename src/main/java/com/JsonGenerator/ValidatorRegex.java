package com.JsonGenerator;

public class ValidatorRegex extends Validator{
    String regex;

    public ValidatorRegex(String regex) {
        super("regex");
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return "ValidatorRegex{" +
                "regex='" + regex + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
