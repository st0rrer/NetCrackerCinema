package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Rating;

import java.util.Date;
import java.util.List;

public interface RatingDao {

    List<Rating> findAll();

    List<Rating> allRatings(Date startDate, Date endDate);

}
