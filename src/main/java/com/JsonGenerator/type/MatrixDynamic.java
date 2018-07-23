package com.JsonGenerator.type;

import com.JsonGenerator.Column;

import java.util.ArrayList;
import java.util.List;

public class MatrixDynamic extends BaseQuestion {
    private static int rowCount = 1;
    private String addRowLocation;

    public static int getRowCount() {
        return rowCount;
    }

    public static void setRowCount(int rowCount) {
        MatrixDynamic.rowCount = rowCount;
    }

    public String getAddRowLocation() {
        return addRowLocation;
    }

    public void setAddRowLocation(String addRowLocation) {
        this.addRowLocation = addRowLocation;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    private List<Column> columns = new ArrayList<>();

    public MatrixDynamic(String name, String title) {
        super(name, title);
        this.addRowLocation = "bottom";
        this.type = "matrixdynamic";
    }

    @Override
    public String toString() {
        return "MatrixDynamic{" +
                "addRowLocation='" + addRowLocation + '\'' +
                ", columns=" + columns +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
