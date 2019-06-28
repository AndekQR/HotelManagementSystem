package com.app.helpers.dp;

import java.util.Date;

public class EventCreateParams {
    public Date start;
    public Date end;
    public String text;
    public int people;
    public int resource;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventCreateParams that=(EventCreateParams) o;

        if (people != that.people) return false;
        if (resource != that.resource) return false;
        if (!start.equals(that.start)) return false;
        if (!end.equals(that.end)) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result=start.hashCode();
        result=31 * result + end.hashCode();
        result=31 * result + text.hashCode();
        result=31 * result + people;
        result=31 * result + resource;
        return result;
    }



}
