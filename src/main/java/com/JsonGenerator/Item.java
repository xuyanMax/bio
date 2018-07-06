package com.JsonGenerator;

import java.util.ArrayList;
import java.util.List;

public class Item {
    String name;
    String title;
    List<Validator> Validators = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Item(String name, String title) {
        this.name = name;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public List<Validator> getValidators() {
        return Validators;
    }

    public void setValidators(List<Validator> validators) {
        Validators = validators;
    }
}
