package com.netcracker.cinema.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaya on 05.11.2016.
 */
public class Hall implements Serializable {

    private long hallId;
    private String hallName;
    private int size;
    private List<Seance> seances;

    public long getHallId() {
        return hallId;
    }

    public void setHallId(long hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }

}
