package com.JsonGenerator.type;


import com.JsonGenerator.Choice;

import java.util.Arrays;

public class RadioGroup extends BaseQuestion {

    Choice[] choices;
    public RadioGroup(String name, String title) {
        super(name, title);
        this.type = "radiogroup";
    }

    public Choice[] getChoices() {
        return choices;
    }

    public void setChoices(Choice[] choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "RadioGroup{" +
                "choices=" + Arrays.toString(choices) +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
