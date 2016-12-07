package com.netcracker.cinema.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamIntValue;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Seance implements Serializable {

    @PodamIntValue(minValue = 1, maxValue = 2000)
    private long id;
    private Date seanceDate;
    private long movieId;
    private long hallId;

}
