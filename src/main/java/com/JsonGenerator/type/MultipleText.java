package com.JsonGenerator.type;


import com.JsonGenerator.element.Item;
import com.JsonGenerator.element.Validator;

import java.util.ArrayList;
import java.util.List;

public class MultipleText extends BaseQuestion {
    List<Item> items = new ArrayList<>();
    List<Validator> validators = new ArrayList<>();

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public MultipleText(String name, String title) {
        super(name, title);
        this.type = "multipletext";
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "MultipleText{" +
                "items=" + items +
                ", validators=" + validators +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
