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
public class Ticket {

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
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (code != ticket.code) return false;
        if (price != ticket.price) return false;
        if (isPaid != ticket.isPaid) return false;
        if (!email.equals(ticket.email)) return false;
        if (!seanceId.equals(ticket.seanceId)) return false;
        return placeId.equals(ticket.placeId);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (code ^ (code >>> 32));
        result = 31 * result + email.hashCode();
        result = 31 * result + price;
        result = 31 * result + (isPaid ? 1 : 0);
        result = 31 * result + seanceId.hashCode();
        result = 31 * result + placeId.hashCode();
        return result;
    }
}
