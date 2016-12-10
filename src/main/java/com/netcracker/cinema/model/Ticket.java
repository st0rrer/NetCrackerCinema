package com.netcracker.cinema.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamBooleanValue;
import uk.co.jemos.podam.common.PodamIntValue;
import uk.co.jemos.podam.common.PodamLongValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @PodamLongValue(minValue = 1115, maxValue = 1120)
    private long code;
    private String email;
    @PodamIntValue(minValue = 20, maxValue = 500)
    private Integer price;
    @PodamBooleanValue()
    private boolean isPaid;
    private long seanceId;
    private long placeId;

}
