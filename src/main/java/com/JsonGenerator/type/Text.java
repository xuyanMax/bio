package com.JsonGenerator.type;


import com.JsonGenerator.element.Validator;

public class Text extends BaseQuestion {
    Validator validators;

    public Text(String name, String title) {
        super(name, title);
        this.type = "text";
    }

    public Validator getValidators() {
        return validators;
    }

    public void setValidators(Validator validators) {
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
