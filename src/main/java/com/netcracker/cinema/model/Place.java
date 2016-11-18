package com.netcracker.cinema.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamIntValue;

import java.io.Serializable;


/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Place implements Serializable {

    @PodamIntValue(minValue = 1, maxValue = 200)
    private int id;
    @PodamIntValue(minValue = 1, maxValue = 120)
    private Integer rowNumber;
    @PodamIntValue(minValue = 1, maxValue = 120)
    private Integer number;
    private Zone zoneId;
    private Hall hallId;

}
