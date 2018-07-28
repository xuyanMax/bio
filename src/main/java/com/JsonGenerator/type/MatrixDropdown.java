package com.JsonGenerator.type;

import com.JsonGenerator.element.Column;

import java.util.ArrayList;
import java.util.List;

public class MatrixDropdown extends BaseQuestion {
    List<Column> columns = new ArrayList<>();
    String cellType;

    public MatrixDropdown(String name, String title) {
        super(name, title);
        this.cellType = "text";
        this.type = "matrixdropdown";
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    @Override
    public String toString() {
        return "MatrixDropdown{" +
                "columns=" + columns +
                ", cellType='" + cellType + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
