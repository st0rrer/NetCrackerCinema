package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Price;

import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
public interface PriceService {

    List<Price> findAll();

    Price getById(long id);

    void save(Price price);

    void delete(Price price);

    int getPriceBySeanceColRow(int seanceId, int col, int row);

    Price getPriceBySeanceZone(long seanceId, long zoneId);
}
