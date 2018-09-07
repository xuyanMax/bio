package com.JsonGenerator.type;


import com.JsonGenerator.element.Validator;

import java.util.ArrayList;
import java.util.List;

public class Text extends BaseQuestion {
    List<Validator> validators = new ArrayList<>();

    public Text(String name, String title) {
        super(name, title);
        this.type = "text";
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public String toString() {
        return "Text{" +
                "validators=" + validators +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
