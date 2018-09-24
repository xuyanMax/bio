package com.JsonGenerator.type;

public class BaseQuestion {
    String name;
    String type;
    String title;
    String description;
    boolean isRequired = true;

    public BaseQuestion(String name, String title) {
        this.name = name;
        this.title = title;
        this.description="";
        this.isRequired = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }
}