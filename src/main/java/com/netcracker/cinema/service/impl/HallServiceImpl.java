package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.HallDao;
import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dimka on 24.11.2016.
 */
@Service
public class HallServiceImpl implements HallService {

    private HallDao hallDao;

    @Autowired
    HallServiceImpl(HallDao hallDao) {
        this.hallDao = hallDao;
    }

    @Override
    public List<Hall> findAll() {
        return hallDao.findAll();
    }

    @Override
    public Hall getById(int id) {
        return hallDao.getById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Hall hall) {
        hallDao.save(hall);
    }

    @Override
    public void delete(Hall hall) {
        hallDao.delete(hall);
    }
}
