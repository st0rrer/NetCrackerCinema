package com.netcracker.cinema.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by gaya on 05.11.2016.
 */

@Getter
@Setter
@ToString
public class Movie implements Serializable {

    private int id;
    private String name;
    private String description;
    private int duration;
    private Float IMDB;
    private int periodicity;
    private int basePrice;
    private String poster;
    private LocalDate startDate;
    private LocalDate endDate;

    public Movie(@PodamFloatValue(maxValue = 10) Float IMDB) {
        this.IMDB = IMDB;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (getId() != movie.getId()) return false;
        if (getDuration() != movie.getDuration()) return false;
        if (getPeriodicity() != movie.getPeriodicity()) return false;
        if (getBasePrice() != movie.getBasePrice()) return false;
        if (getName() != null ? !getName().equals(movie.getName()) : movie.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(movie.getDescription()) : movie.getDescription() != null)
            return false;
        if (getIMDB() != null ? !getIMDB().equals(movie.getIMDB()) : movie.getIMDB() != null) return false;
        if (getPoster() != null ? !getPoster().equals(movie.getPoster()) : movie.getPoster() != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(movie.getStartDate()) : movie.getStartDate() != null)
            return false;
        return getEndDate() != null ? getEndDate().equals(movie.getEndDate()) : movie.getEndDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getDuration();
        result = 31 * result + (getIMDB() != null ? getIMDB().hashCode() : 0);
        result = 31 * result + getPeriodicity();
        result = 31 * result + getBasePrice();
        result = 31 * result + (getPoster() != null ? getPoster().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        return result;
    }
}

