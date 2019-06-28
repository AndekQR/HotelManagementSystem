package com.app.helpers.dp;

import java.awt.*;
import java.util.Random;

public class RowsData {


    public String name;
    public int id;
    public Color color = getRandomColor();


    private Color getRandomColor(){
        Random random = new Random();
        float a = random.nextFloat();
        float b = random.nextFloat();
        float c = random.nextFloat();

        return new Color(a, b, c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RowsData rowsData=(RowsData) o;

        if (id != rowsData.id) return false;
        return name.equals(rowsData.name);
    }

    @Override
    public int hashCode() {
        int result=name.hashCode();
        result=31 * result + id;
        return result;
    }
}
