package com.netcracker.cinema.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Rating implements Serializable {
    private String movieName;
    private String hallName;
    private String zoneName;
    private long ticketSold;
    private long price;
    private Date startDate;
    private Date endDate;
}
