package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.PriceDao;
import com.netcracker.cinema.model.Price;
import com.netcracker.cinema.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
public class PriceServiceImpl implements PriceService {

    private PriceDao priceDao;

    @Autowired
    PriceServiceImpl(PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    @Override
    public List<Price> findAll() {
        return priceDao.findAll();
    }

    @Override
    public Price getById(long id) {
        return priceDao.getById(id);
    }

    @Override
    public void save(Price price) {
        priceDao.save(price);
    }

    @Override
    public void delete(Price price) {
        priceDao.delete(price);
    }
}
