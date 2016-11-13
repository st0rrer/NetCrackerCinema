package com.netcracker.cinema.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by gaya on 11.11.2016.
 */
@Getter
@Setter
@ToString
public class Price {

    private long id;
    private int price;
    private Seance seanceId;
    private Zone zoneId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price1 = (Price) o;

        if (getId() != price1.getId()) return false;
        if (getPrice() != price1.getPrice()) return false;
        if (getSeanceId() != null ? !getSeanceId().equals(price1.getSeanceId()) : price1.getSeanceId() != null) return false;
        return getZoneId() != null ? getZoneId().equals(price1.getZoneId()) : price1.getZoneId() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getPrice();
        result = 31 * result + (getSeanceId() != null ? getSeanceId().hashCode() : 0);
        result = 31 * result + (getZoneId() != null ? getZoneId().hashCode() : 0);
        return result;
    }
}
