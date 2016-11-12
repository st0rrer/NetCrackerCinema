package com.netcracker.cinema.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
public class Seance {

    private long id;
    private Date seanceDate;
    private Date seanceTime;
    private Movie movieId;
    private Hall hallId;
}
