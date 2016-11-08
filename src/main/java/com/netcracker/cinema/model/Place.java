package com.netcracker.cinema.model;


import java.io.Serializable;

/**
 * Created by gaya on 05.11.2016.
 */
public class Place implements Serializable {

    private long placeId;
    private int rowNumber;
    private int number;
    private Zone zoneId;

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Zone getZoneId() {
        return zoneId;
    }

    public void setZoneId(Zone zoneId) {
        this.zoneId = zoneId;
    }

}
