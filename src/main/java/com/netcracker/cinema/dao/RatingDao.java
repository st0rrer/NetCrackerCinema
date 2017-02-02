package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Rating;

import java.util.*;

public interface RatingDao {

    List<Rating> findAll();

    List<Rating> hallRatings();

    List<Rating> zoneRatings();

    List<Rating> allRatings(Date startDate, Date endDate);

}
