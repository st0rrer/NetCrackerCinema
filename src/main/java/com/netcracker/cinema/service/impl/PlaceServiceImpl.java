package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.PlaceDao;
import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Илья on 08.12.2016.
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    private PlaceDao placeDao;

    @Autowired
    PlaceServiceImpl(PlaceDao placeDao){
        this.placeDao = placeDao;
    }

    @Override
    public List<Place> findAll() {
        return placeDao.findAll();
    }

    @Override
    public Place getById(long id) {
        return placeDao.getById(id);
    }

    @Override
    public List<Place> getByHall(long id) {
        return placeDao.getByHall(id);
    }

    @Override
    public List<Place> getByZone(long id) {
        return placeDao.getByZone(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Place place) {
        placeDao.save(place);
    }

    @Override
    public void delete(Place place) {
        placeDao.delete(place);
    }
}
