package com.netcracker.cinema.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by gaya on 05.11.2016.
 */

public class Seance implements Serializable {

    private long seanceId;
    private Date seanceDate;
    private Date seanceTime;
    private Movie movieId;
    private Schedule scheduleId;
    private Hall halsIid;
    private List<Ticket> tickes;

    public long getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(long seanceId) {
        this.seanceId = seanceId;
    }

    public Date getSeanceDate() {
        return seanceDate;
    }

    public void setSeanceDate(Date seanceDate) {
        this.seanceDate = seanceDate;
    }

    public Date getSeanceTime() {
        return seanceTime;
    }

    public void setSeanceTime(Date seanceTime) {
        this.seanceTime = seanceTime;
    }

    public Movie getMovieId() {
        return movieId;
    }

    public void setMovieId(Movie movieId) {
        this.movieId = movieId;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Hall getHalsIid() {
        return halsIid;
    }

    public void setHalsIid(Hall halsIid) {
        this.halsIid = halsIid;
    }

    public List<Ticket> getTickes() {
        return tickes;
    }

    public void setTickes(List<Ticket> tickes) {
        this.tickes = tickes;
    }

}
