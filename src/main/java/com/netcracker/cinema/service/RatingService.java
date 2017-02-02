package com.netcracker.cinema.service;


import com.netcracker.cinema.model.Rating;

import java.util.*;

public interface RatingService {

    List<Rating> findAll();

    List<Rating> hallRatings();

    List<Rating> zoneRatings();

    List<Rating> allRating(Date startDate, Date endDate);

}
