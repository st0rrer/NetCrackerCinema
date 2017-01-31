package com.netcracker.cinema.model;

import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Seance implements Serializable {

    private long id;
    private Date seanceDate;
    private long movieId;
    private long hallId;

}
