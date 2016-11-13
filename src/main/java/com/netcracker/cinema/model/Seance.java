package com.netcracker.cinema.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by gaya on 05.11.2016.
 */
@Getter
@Setter
@ToString
public class Seance {

    private long id;
    private LocalDate seanceDate;
    private LocalTime seanceTime;
    private Movie movieId;
    private Hall hallId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seance seance = (Seance) o;

        if (id != seance.id) return false;
        if (!seanceDate.equals(seance.seanceDate)) return false;
        if (!seanceTime.equals(seance.seanceTime)) return false;
        if (!movieId.equals(seance.movieId)) return false;
        return hallId.equals(seance.hallId);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + seanceDate.hashCode();
        result = 31 * result + seanceTime.hashCode();
        result = 31 * result + movieId.hashCode();
        result = 31 * result + hallId.hashCode();
        return result;
    }
}
