package com.netcracker.cinema.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaya on 05.11.2016.
 */

public class Schedule implements Serializable {

    private long scheduleId;
    private List<Seance> seances;

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }

}
