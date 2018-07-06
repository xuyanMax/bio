package com.JsonGenerator;


import com.JsonGenerator.type.BaseQuestion;

import java.util.List;

public class Page {
    String name;
    List<BaseQuestion> elements;
    String navigationButtonsVisibility;

    public Page() {
        this.navigationButtonsVisibility = "show";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BaseQuestion> getElements() {
        return elements;
    }

    public void setElements(List<BaseQuestion> elements) {
        this.elements = elements;
    }

    public String getNavigationButtonsVisibility() {
        return navigationButtonsVisibility;
    }

    public void setNavigationButtonsVisibility(String navigationButtonsVisibility) {
        this.navigationButtonsVisibility = navigationButtonsVisibility;
    }

    @Override
    public String toString() {
        return "Page{" +
                "name='" + name + '\'' +
                ", elements=" + elements +
                ", navigationButtonsVisibility='" + navigationButtonsVisibility + '\'' +
                '}';
    }
}
