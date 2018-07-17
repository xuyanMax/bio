package com.JsonGenerator.type;


import com.JsonGenerator.Choice;

import java.util.Arrays;
import java.util.List;

public class RadioGroup extends BaseQuestion {

    List<Choice> choices;
    public RadioGroup(String name, String title) {
        super(name, title);
        this.type = "radiogroup";
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "RadioGroup{" +
                "choices=" + choices +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
