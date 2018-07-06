package com.JsonGenerator.type;


import com.JsonGenerator.Choice;

import java.util.Arrays;

public class Checkbox extends BaseQuestion {
    Choice[] choices;

    public Checkbox(String name, String title) {
        super(name, title);
        this.type = "checkbox";
    }

    public Choice[] getChoices() {
        return choices;
    }

    public void setChoices(Choice[] choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "Checkbox{" +
                "choices=" + Arrays.toString(choices) +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
