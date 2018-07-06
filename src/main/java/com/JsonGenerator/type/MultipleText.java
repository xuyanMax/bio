package com.JsonGenerator.type;


import com.JsonGenerator.Item;

import java.util.ArrayList;
import java.util.List;

public class MultipleText extends BaseQuestion {
    List<Item> items = new ArrayList<>();
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
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
