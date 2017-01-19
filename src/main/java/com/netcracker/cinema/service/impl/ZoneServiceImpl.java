package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.ZoneDao;
import com.netcracker.cinema.model.Zone;
import com.netcracker.cinema.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dimka on 24.11.2016.
 */
@Service
public class ZoneServiceImpl implements ZoneService {

    private ZoneDao zoneDao;

    @Autowired
    ZoneServiceImpl(ZoneDao zoneDao) {
        this.zoneDao = zoneDao;
    }

    @Override
    public List<Zone> findAll() {
        return zoneDao.findAll();
    }

    @Override
    public Zone getById(long id) {
        return zoneDao.getById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Zone zone) {
        zoneDao.save(zone);
    }

    @Override
    public void delete(Zone zone) {
        zoneDao.delete(zone);
    }
}
