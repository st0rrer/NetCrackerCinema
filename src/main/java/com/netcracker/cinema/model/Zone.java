package com.netcracker.cinema.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamIntValue;

import java.io.Serializable;

/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
@ToString
public class Zone implements Serializable {

    @PodamIntValue(minValue = 1, maxValue = 6)
    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zone)) return false;

        Zone zone = (Zone) o;

        if (getId() != zone.getId()) return false;
        return getName() != null ? getName().equals(zone.getName()) : zone.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
