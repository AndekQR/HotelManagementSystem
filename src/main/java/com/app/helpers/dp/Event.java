package com.app.helpers.dp;

import java.awt.*;
import java.util.Date;

public class Event {
    public int id;
    public String text;
    public Date start;
    public Date end;
    public Color color = null;
    public int resource;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event=(Event) o;

        if (id != event.id) return false;
        if (resource != event.resource) return false;
        if (!text.equals(event.text)) return false;
        if (!start.equals(event.start)) return false;
        return end.equals(event.end);
    }

    @Override
    public int hashCode() {
        int result=id;
        result=31 * result + text.hashCode();
        result=31 * result + start.hashCode();
        result=31 * result + end.hashCode();
        result=31 * result + resource;
        return result;
    }
}
