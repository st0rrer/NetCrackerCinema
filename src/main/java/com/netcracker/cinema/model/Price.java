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
        if (o == null || getClass() != o.getClass()) return false;

        Price price1 = (Price) o;

        if (id != price1.id) return false;
        if (price != price1.price) return false;
        if (!seanceId.equals(price1.seanceId)) return false;
        return zoneId.equals(price1.zoneId);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + price;
        result = 31 * result + seanceId.hashCode();
        result = 31 * result + zoneId.hashCode();
        return result;
    }
}
