package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Dummy;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DummyService {
    Dummy getById(long id) throws DataAccessException;
    void save(Dummy dummy) throws DataAccessException;
    List<Dummy> findAll() throws DataAccessException;
}
