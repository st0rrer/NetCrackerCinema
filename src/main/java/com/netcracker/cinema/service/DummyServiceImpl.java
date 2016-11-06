package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Dummy;
import com.netcracker.cinema.dao.DummyDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyServiceImpl implements DummyService {
    private static final Logger logger = Logger.getLogger(DummyServiceImpl.class);

    private DummyDao dummyDao;

    @Autowired
    public DummyServiceImpl(DummyDao dummyDao) {
        this.dummyDao = dummyDao;
    }

    @Override
    public Dummy getById(long id) throws DataAccessException {
        return dummyDao.findById(id);
    }

    @Override
    public void save(Dummy dummy) throws DataAccessException {
        dummyDao.save(dummy);
    }

    @Override
    public List<Dummy> findAll() throws DataAccessException {
        return dummyDao.findAll();
    }
}
