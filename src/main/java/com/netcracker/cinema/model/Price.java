package com.netcracker.cinema.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Price implements Serializable {

    private long id;
    private Integer price;
    private long seanceId;
    private long zoneId;

}
