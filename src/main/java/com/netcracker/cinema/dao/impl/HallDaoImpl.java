package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.HallDao;
import com.netcracker.cinema.exception.CinemaDaoException;
import com.netcracker.cinema.exception.CinemaEmptyResultException;
import com.netcracker.cinema.model.Hall;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.HallDaoQuery.*;

public class HallDaoImpl implements HallDao {
    private static final Logger LOGGER = Logger.getLogger(HallDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public HallDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Hall> findAll() {
        try {
            List<Hall> halls = jdbcTemplate.query(FIND_ALL_SQL, new HallMapper());
            LOGGER.info("Found " + halls.size() + " hall objects");
            return halls;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Hall getById(long id) {
        try {
            Hall hall = jdbcTemplate.queryForObject(FIND_HALL_BY_ID, new Object[]{id}, new HallMapper());
            LOGGER.info("Found not null hall with id " + id);
            return hall;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no hall with id " + id);
            throw new CinemaEmptyResultException("There are no hall with id " + id, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Hall hall) {
        if (hall == null) {
            LOGGER.error("Attempt to save null hall");
            throw new IllegalArgumentException("Can't save null hall");
        }
        try {
            int affected = jdbcTemplate.update(MERGE_HALL_OBJECT, hall.getId(), hall.getName());
            if (hall.getId() == 0) {
                hall.setId(jdbcTemplate.queryForObject(SELECT_ID_FOR_INSERTED_HALL, Long.class));
            }
            LOGGER.info("Hall was saved, affected " + affected + " rows, now hall has id " + hall.getId());
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Hall hall) {
        if (hall == null) {
            LOGGER.error("Attempt to delete null hall");
            throw new IllegalArgumentException("Can't delete null hall");
        }
        try {
            int affected = jdbcTemplate.update(DELETE_HALL, hall.getId());
            LOGGER.info("Hall with id " + hall.getId() + " was deleted, affected " + affected + " rows");
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private class HallMapper implements RowMapper<Hall> {
        @Override
        public Hall mapRow(ResultSet resultSet, int i) throws SQLException {
            Hall hall = new Hall();
            hall.setId(resultSet.getLong("id"));
            hall.setName(resultSet.getString("name"));
            return hall;
        }
    }
}