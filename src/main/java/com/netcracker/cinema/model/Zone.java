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
public class Zone implements Serializable {

    @PodamIntValue(minValue = 1, maxValue = 6)
    private long id;
    private String name;
}
