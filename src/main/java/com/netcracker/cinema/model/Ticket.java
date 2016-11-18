package com.netcracker.cinema.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamLongValue;

import java.io.Serializable;

/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Ticket implements Serializable {

    @PodamLongValue(minValue = 1, maxValue = 200000)
    private long id;
    @PodamLongValue(minValue = 100000, maxValue = 999999)
    private long code;
    private String email;
    @PodamIntValue(minValue = 20, maxValue = 500)
    private Integer price;
    private boolean isPaid;
    private Seance seanceId;
    private Place placeId;


}
