package com.JsonGenerator.type;


import com.JsonGenerator.element.Choice;

import java.util.List;

public class Checkbox extends BaseQuestion {
    List<Choice> choices;

    public Checkbox(String name, String title) {
        super(name, title);
        this.type = "checkbox";
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "Checkbox{" +
                "choices=" + choices +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
