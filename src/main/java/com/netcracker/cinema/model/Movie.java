package com.netcracker.cinema.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Movie {

    private long id;
    private String title;
    private String description;
    private int duration;
    private int IMDB;
    private int periodicity;
    private int basePrice;
    private String poster;
    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (duration != movie.duration) return false;
        if (IMDB != movie.IMDB) return false;
        if (periodicity != movie.periodicity) return false;
        if (basePrice != movie.basePrice) return false;
        if (!title.equals(movie.title)) return false;
        if (!description.equals(movie.description)) return false;
        if (!poster.equals(movie.poster)) return false;
        if (!startDate.equals(movie.startDate)) return false;
        return endDate.equals(movie.endDate);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + duration;
        result = 31 * result + IMDB;
        result = 31 * result + periodicity;
        result = 31 * result + basePrice;
        result = 31 * result + poster.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
