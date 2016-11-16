package com.netcracker.cinema.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
@ToString
public class Seance implements Serializable {

    private int id;
    private LocalDate seanceDate;
    private LocalTime seanceTime;
    private Movie movieId;
    private Hall hallId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seance)) return false;

        Seance seance = (Seance) o;

        if (getId() != seance.getId()) return false;
        if (getSeanceDate() != null ? !getSeanceDate().equals(seance.getSeanceDate()) : seance.getSeanceDate() != null)
            return false;
        if (getSeanceTime() != null ? !getSeanceTime().equals(seance.getSeanceTime()) : seance.getSeanceTime() != null)
            return false;
        if (getMovieId() != null ? !getMovieId().equals(seance.getMovieId()) : seance.getMovieId() != null)
            return false;
        return getHallId() != null ? getHallId().equals(seance.getHallId()) : seance.getHallId() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getSeanceDate() != null ? getSeanceDate().hashCode() : 0);
        result = 31 * result + (getSeanceTime() != null ? getSeanceTime().hashCode() : 0);
        result = 31 * result + (getMovieId() != null ? getMovieId().hashCode() : 0);
        result = 31 * result + (getHallId() != null ? getHallId().hashCode() : 0);
        return result;
    }
}
