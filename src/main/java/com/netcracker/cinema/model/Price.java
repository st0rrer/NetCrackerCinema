package com.netcracker.cinema.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamIntValue;

import java.io.Serializable;

/**
 * Created by gaya on 11.11.2016.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Price implements Serializable {

    @PodamIntValue(minValue = 1, maxValue = 2000)
    private long id;
    @PodamIntValue(minValue = 20, maxValue = 500)
    private Integer price;
    private Seance seanceId;
    private Zone zoneId;

}
