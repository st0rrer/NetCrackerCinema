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
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (getId() != movie.getId()) return false;
        if (getDuration() != movie.getDuration()) return false;
        if (getIMDB() != movie.getIMDB()) return false;
        if (getPeriodicity() != movie.getPeriodicity()) return false;
        if (getBasePrice() != movie.getBasePrice()) return false;
        if (getTitle() != null ? !getTitle().equals(movie.getTitle()) : movie.getTitle() != null) return false;
        if (getDescription() != null ? !getDescription().equals(movie.getDescription()) : movie.getDescription() != null)
            return false;
        if (getPoster() != null ? !getPoster().equals(movie.getPoster()) : movie.getPoster() != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(movie.getStartDate()) : movie.getStartDate() != null)
            return false;
        return getEndDate() != null ? getEndDate().equals(movie.getEndDate()) : movie.getEndDate() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getDuration();
        result = 31 * result + getIMDB();
        result = 31 * result + getPeriodicity();
        result = 31 * result + getBasePrice();
        result = 31 * result + (getPoster() != null ? getPoster().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        return result;
    }
}
