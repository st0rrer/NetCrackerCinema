package com.netcracker.cinema.model;


import com.vaadin.ui.Image;

import java.util.Date;
import java.util.List;

public class Movie {
    private long movieId;
    private String title;
    private String description;
    private int duration;
    private int IMDB;
    private Image poster;
    private int basePrice;
    private Date startDate;
    private Date endDate;
    private int price_hall_one;
    private int price_hall_two;
    private int AA;
    private int BB;
    private int CC;
    private int DD;
    private int EE;
    private int HH;


    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIMDB() {
        return IMDB;
    }

    public void setIMDB(int IMDB) {
        this.IMDB = IMDB;
    }

    public Image getPoster() {
        return poster;
    }

    public void setPoster(Image poster) {
        this.poster = poster;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPrice_hall_one() {
        return price_hall_one;
    }

    public void setPrice_hall_one(int price_hall_one) {
        this.price_hall_one = price_hall_one;
    }

    public int getPrice_hall_two() {
        return price_hall_two;
    }

    public void setPrice_hall_two(int price_hall_two) {
        this.price_hall_two = price_hall_two;
    }

    public int getAA() {
        return AA;
    }

    public void setAA(int AA) {
        this.AA = AA;
    }

    public int getBB() {
        return BB;
    }

    public void setBB(int BB) {
        this.BB = BB;
    }

    public int getCC() {
        return CC;
    }

    public void setCC(int CC) {
        this.CC = CC;
    }

    public int getDD() {
        return DD;
    }

    public void setDD(int DD) {
        this.DD = DD;
    }

    public int getEE() {
        return EE;
    }

    public void setEE(int EE) {
        this.EE = EE;
    }

    public int getHH() {
        return HH;
    }

    public void setHH(int HH) {
        this.HH = HH;
    }

}
