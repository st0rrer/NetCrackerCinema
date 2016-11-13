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

    private long id;
    private int rowNumber;
    private int number;
    private Zone zoneId;
    private Hall hallId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;

        Place place = (Place) o;

        if (getId() != place.getId()) return false;
        if (getRowNumber() != place.getRowNumber()) return false;
        if (getNumber() != place.getNumber()) return false;
        if (getZoneId() != null ? !getZoneId().equals(place.getZoneId()) : place.getZoneId() != null) return false;
        return getHallId() != null ? getHallId().equals(place.getHallId()) : place.getHallId() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getRowNumber();
        result = 31 * result + getNumber();
        result = 31 * result + (getZoneId() != null ? getZoneId().hashCode() : 0);
        result = 31 * result + (getHallId() != null ? getHallId().hashCode() : 0);
        return result;
    }


}
