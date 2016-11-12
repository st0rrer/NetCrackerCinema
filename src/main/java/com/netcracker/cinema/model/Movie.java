package com.netcracker.cinema.model;


import com.vaadin.ui.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Movie  {

    private long id;
    private String title;
    private String description;
    private int duration;
    private int IMDB;
    private int periodicity;
    private int basePrice;
    private Image poster;
    private Date startDate;
    private Date endDate;
}
