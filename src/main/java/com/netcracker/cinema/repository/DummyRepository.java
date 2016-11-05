package com.netcracker.cinema.repository;

import com.netcracker.cinema.model.Dummy;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DummyRepository {

    Dummy findById(long id) throws DataAccessException;
    void save(Dummy dummy) throws DataAccessException;
    List<Dummy> findAll() throws DataAccessException;
}
