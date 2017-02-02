package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.RatingDao;
import com.netcracker.cinema.exception.CinemaDaoException;
import com.netcracker.cinema.model.Rating;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static com.netcracker.cinema.dao.impl.queries.RatingDaoQuery.*;

public class RatingDaoImpl implements RatingDao {
    private static final Logger logger = Logger.getLogger(RatingDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Rating> findAll() {
        try {
            List<Rating> ratings = jdbcTemplate.query(ALL_RATINGS, new AllRatingMapper());
            logger.info("Find all rating: found " + ratings.size() + " rating all objects");
            return ratings;
        } catch (DataAccessException e) {
            logger.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Rating> hallRatings() {
        try {
            List<Rating> ratings = jdbcTemplate.query(HALL_RATINGS, new RatingMapperHall());
            logger.info("Find hall rating: found " + ratings.size() + " rating objects");
            return ratings;
        } catch (DataAccessException e) {
            logger.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Rating> zoneRatings() {
        try {
            List<Rating> ratings = jdbcTemplate.query(ZONE_RATINGS, new RatingMapperZone());
            logger.info("Find zone rating: found " + ratings.size() + " rating objects");
            return ratings;
        } catch (DataAccessException e) {
            logger.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Rating> allRatings(Date startDate, Date endDate) {
        if (startDate == null) {
            logger.error("startDate can't be null");
            throw new IllegalArgumentException("startDate can't be null");
        }
        if (endDate == null) {
            logger.error("endDate can't be null");
            throw new IllegalArgumentException("endDate can't be null");
        }
        try {
            List<Rating> ratings = jdbcTemplate.query(CALL_ALL_RATINGS, new AllRatingMapper(), new java.sql.Date(startDate.getTime()),
                    new java.sql.Date(endDate.getTime()));
            logger.info("Find all rating: found  rating objects");
            return ratings;
        } catch (DataAccessException e) {
            logger.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    class AllRatingMapper implements RowMapper<Rating> {
        @Override
        public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
            Rating rating = new Rating();
            logger.debug(resultSet);
            rating.setMovieName(resultSet.getString("MOVIE_NAME"));
            rating.setHallName(resultSet.getString("HALL"));
            rating.setZoneName(resultSet.getString("ZONE"));
            rating.setTicketSold(resultSet.getLong("TICKET_SOLD"));
            rating.setPrice(resultSet.getInt("TICKET_PRICE"));
            rating.setStartDate(resultSet.getDate("START_DATE"));
            rating.setEndDate(resultSet.getDate("END_DATE"));
            return rating;
        }
    }

    class RatingMapperHall implements RowMapper<Rating> {
        @Override
        public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
            Rating rating = new Rating();
            logger.debug(resultSet);
            rating.setHallName(resultSet.getString("HALL"));
            rating.setTicketSold(resultSet.getLong("TICKET_SOLD"));
            rating.setPrice(resultSet.getInt("TICKET_PRICE"));
            return rating;
        }
    }

    class RatingMapperZone implements RowMapper<Rating> {
        @Override
        public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
            Rating rating = new Rating();
            logger.debug(resultSet);
            rating.setHallName(resultSet.getString("HALL"));
            rating.setZoneName(resultSet.getString("ZONE"));
            rating.setTicketSold(resultSet.getLong("TICKET_SOLD"));
            rating.setPrice(resultSet.getInt("TICKET_PRICE"));
            return rating;
        }
    }
}