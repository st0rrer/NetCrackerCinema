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
public class Hall {

    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hall)) return false;

        Hall hall = (Hall) o;

        if (getId() != hall.getId()) return false;
        return getName() != null ? getName().equals(hall.getName()) : hall.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
