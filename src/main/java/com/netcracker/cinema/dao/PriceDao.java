package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Price;

import java.util.List;

/**
 * Created by Parpalak on 10.12.2016.
 */
public interface PriceDao {

    List<Price> findAll();

    Price getById(long id);

    void save(Price price);

    void delete(Price price);

    int getPriceBySeanceColRow(int seanceId, int col, int row);

    int getPriceBySeanceZone(long seanceId, long zoneId);
}
