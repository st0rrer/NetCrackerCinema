package com.netcracker.cinema.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Ticket implements Serializable {

    private long id;
    private long code;
    private String email;
    private Integer price;
    private boolean isPaid;
    private long seanceId;
    private long placeId;
}
