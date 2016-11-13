package com.netcracker.cinema.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
@ToString
public class Hall  {

    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hall hall = (Hall) o;

        if (id != hall.id) return false;
        return name.equals(hall.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}
