package com.JsonGenerator;

public class Item {
    String name;
    String title;

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
}
