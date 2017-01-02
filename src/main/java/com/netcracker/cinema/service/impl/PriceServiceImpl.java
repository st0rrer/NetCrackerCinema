package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.PriceDao;
import com.netcracker.cinema.model.Price;
import com.netcracker.cinema.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by User on 16.12.2016.
 */
@Service
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

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Price price) {
        priceDao.save(price);
    }

    @Override
    public void delete(Price price) {
        priceDao.delete(price);
    }

    @Override
    public int getPriceBySeanceColRow(int seanceId, int col, int row) {
        return priceDao.getPriceBySeanceColRow(seanceId, col, row);
    }

    @Override
    public int getPriceBySeanceZone(long seanceId, long zoneId) {
        return priceDao.getPriceBySeanceZone(seanceId, zoneId);
    }
}
