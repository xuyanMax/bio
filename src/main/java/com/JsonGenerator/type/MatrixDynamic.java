package com.JsonGenerator.type;

public class MatrixDynamic extends BaseQuestion {
    private static int rowCount = 1;
    private String addRowLocation;
    public MatrixDynamic(String name, String title) {
        super(name, title);
        this.addRowLocation = "bottom";
        this.type = "matrixdynamic";
    }

    @Override
    public String toString() {
        return "MatrixDynamic{" +
                "addRowLocation='" + addRowLocation + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
