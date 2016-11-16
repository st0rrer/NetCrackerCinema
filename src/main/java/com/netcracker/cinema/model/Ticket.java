package com.netcracker.cinema.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
@ToString
public class Ticket implements Serializable {

    private long id;
    private long code;
    private String email;
    private int price;
    private boolean isPaid;
    private Seance seanceId;
    private Place placeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;

        if (getId() != ticket.getId()) return false;
        if (getCode() != ticket.getCode()) return false;
        if (getPrice() != ticket.getPrice()) return false;
        if (isPaid() != ticket.isPaid()) return false;
        if (getEmail() != null ? !getEmail().equals(ticket.getEmail()) : ticket.getEmail() != null) return false;
        if (getSeanceId() != null ? !getSeanceId().equals(ticket.getSeanceId()) : ticket.getSeanceId() != null)
            return false;
        return getPlaceId() != null ? getPlaceId().equals(ticket.getPlaceId()) : ticket.getPlaceId() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (getCode() ^ (getCode() >>> 32));
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + getPrice();
        result = 31 * result + (isPaid() ? 1 : 0);
        result = 31 * result + (getSeanceId() != null ? getSeanceId().hashCode() : 0);
        result = 31 * result + (getPlaceId() != null ? getPlaceId().hashCode() : 0);
        return result;
    }
}
