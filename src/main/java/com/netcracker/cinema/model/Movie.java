package com.netcracker.cinema.model;


import lombok.*;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamStringValue;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gaya on 05.11.2016.
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Movie implements Serializable {

    @PodamIntValue(minValue = 1, maxValue = 2000)
    private long id;
    private String name;
    @PodamStringValue(length = 1000)
    private String description;
    @PodamIntValue(minValue = 10, maxValue = 340)
    private Integer duration;
    @PodamIntValue(minValue = 10, maxValue = 100)
    private Integer imdb;
    @PodamIntValue(minValue = 1, maxValue = 10)
    private Integer periodicity;
    @PodamIntValue(minValue = 20, maxValue = 500)
    private Integer basePrice;
    private String poster;
    private Date startDate;
    private Date endDate;
}

