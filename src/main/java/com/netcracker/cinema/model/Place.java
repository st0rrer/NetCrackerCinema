package com.netcracker.cinema.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Place implements Serializable {

    private long id;
    private Integer rowNumber;
    private Integer number;
    private long zoneId;
    private long hallId;

}
