package com.netcracker.cinema.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaya on 11.11.2016.
 */
@Getter
@Setter
public class Price {

    private long id;
    private int price;
    private Seance seanceId;
    private Zone zoneId;
}
