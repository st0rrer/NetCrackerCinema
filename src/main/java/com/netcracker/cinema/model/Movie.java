package com.netcracker.cinema.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Movie implements Serializable {

    private long id;
    private String name;
    private String description;
    private Integer duration;
    private Integer imdb;
    private Integer periodicity;
    private Integer basePrice;
    private String poster;
    private Date startDate;
    private Date endDate;
}

