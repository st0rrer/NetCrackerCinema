package com.netcracker.cinema.model;


import java.io.Serializable;

/**
 * Created by gaya on 05.11.2016.
 */
public class Zone implements Serializable {

    private long zoneId;
    private String zoneName;
    private Hall hallId;

    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Hall getHallId() {
        return hallId;
    }

    public void setHallId(Hall hallId) {
        this.hallId = hallId;
    }

}
