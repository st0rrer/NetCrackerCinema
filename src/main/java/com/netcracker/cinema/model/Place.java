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
public class Place  {

    private long idd;
    private int rowNumber;
    private int number;
    private Zone zoneId;
    private Hall hallId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (idd != place.idd) return false;
        if (rowNumber != place.rowNumber) return false;
        if (number != place.number) return false;
        if (!zoneId.equals(place.zoneId)) return false;
        return hallId.equals(place.hallId);

    }

    @Override
    public int hashCode() {
        int result = (int) (idd ^ (idd >>> 32));
        result = 31 * result + rowNumber;
        result = 31 * result + number;
        result = 31 * result + zoneId.hashCode();
        result = 31 * result + hallId.hashCode();
        return result;
    }
}
