package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Dummy;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by dimka on 05.11.2016.
 */
public interface DummyDao {

    Dummy findById(long id) throws DataAccessException;
    void save(Dummy dummy) throws DataAccessException;
    List<Dummy> findAll() throws DataAccessException;
}
