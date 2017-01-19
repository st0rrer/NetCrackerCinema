package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.PlaceDao;
import com.netcracker.cinema.exception.CinemaDaoException;
import com.netcracker.cinema.exception.CinemaEmptyResultException;
import com.netcracker.cinema.model.Place;
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

import static com.netcracker.cinema.dao.impl.queries.PlaceDaoQuery.*;

/**
 * Created by Илья on 08.12.2016.
 */
public class PlaceDaoImpl implements PlaceDao {
    private static final Logger LOGGER = Logger.getLogger(PlaceDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PlaceDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Place> findAll() {
        try {
            List<Place> places = jdbcTemplate.query(FIND_ALL_SQL, new PlaceRowMapper());
            LOGGER.info("Found " + places.size() + " place objects");
            return places;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Place getById(long id) {
        try {
            Place place = jdbcTemplate.queryForObject(FIND_PLACE_BY_ID, new Object[]{id}, new PlaceRowMapper());
            LOGGER.info("Found not null place with id " + id);
            return place;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no place with id " + id);
            throw new CinemaEmptyResultException("There are no place with id " + id, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Place> getByHall(long id) {
        try {
            List<Place> places = jdbcTemplate.query(FIND_PLACE_BY_HALL, new Object[]{id}, new PlaceRowMapper());
            LOGGER.info("Found " + places.size() + " places with hall id " + id);
            return places;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Place> getByZone(long id) {
        try {
            List<Place> places = jdbcTemplate.query(FIND_PLACE_BY_ZONE, new Object[]{id}, new PlaceRowMapper());
            LOGGER.info("Found " + places.size() + " places with zone id " + id);
            return places;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Place place) {
        if (place == null) {
            LOGGER.error("Attempt to save null place");
            throw new IllegalArgumentException("Can't save null place");
        }
        try {
            int affected = jdbcTemplate.update(MERGE_PLACE_OBJECT, place.getId());
            affected += jdbcTemplate.update(MERGE_PLACE_ATTRIBUTE_NUMBER, place.getNumber(), place.getId());
            affected += jdbcTemplate.update(MERGE_PLACE_ATTRIBUTE_ROW, place.getRowNumber(), place.getId());
            affected += jdbcTemplate.update(MERGE_PLACE_ATTRIBUTE_HALL_REF, place.getHallId(), place.getId());
            affected += jdbcTemplate.update(MERGE_PLACE_ATTRIBUTE_ZONE_REF, place.getZoneId(), place.getId());
            if (place.getId() == 0) {
                place.setId(jdbcTemplate.queryForObject(SELECT_ID, Long.class));
            }
            LOGGER.info("Place was saved, affected " + affected + " rows, now place has id " + place.getId());
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Place place) {
        if (place == null) {
            LOGGER.error("Attempt to delete null place");
            throw new IllegalArgumentException("Can't delete null place");
        }
        try {
            int affected = jdbcTemplate.update(DELETE_PLACE, new Object[]{place.getId()});
            LOGGER.info("Place with id " + place.getId() + " was deleted, affected " + affected + " rows");
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    private class PlaceRowMapper implements RowMapper<Place> {
        @Override
        public Place mapRow(ResultSet resultSet, int i) throws SQLException {
            Place place = new Place();
            place.setId(resultSet.getLong("id"));
            place.setNumber(resultSet.getInt("numm"));
            place.setRowNumber(resultSet.getInt("roww"));
            place.setHallId(resultSet.getLong("hall"));
            place.setZoneId(resultSet.getLong("zonee"));
            return place;
        }
    }
}
