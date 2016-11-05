package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Dummy;
import com.netcracker.cinema.repository.DummyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyServiceImpl implements DummyService {

    private DummyRepository dummyRepository;

    @Autowired
    public DummyServiceImpl(DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    @Override
    public Dummy getById(long id) throws DataAccessException {
        return dummyRepository.findById(id);
    }

    @Override
    public void save(Dummy dummy) throws DataAccessException {
        dummyRepository.save(dummy);
    }

    @Override
    public List<Dummy> findAll() throws DataAccessException {
        return dummyRepository.findAll();
    }
}
