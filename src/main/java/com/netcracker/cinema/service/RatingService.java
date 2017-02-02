package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Rating;

import java.util.Date;
import java.util.List;

public interface RatingService {

    List<Rating> findAll();

    List<Rating> allRating(Date startDate, Date endDate);

}
