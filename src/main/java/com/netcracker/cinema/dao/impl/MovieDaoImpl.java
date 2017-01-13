package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.MovieDao;
import com.netcracker.cinema.model.Movie;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.MovieDaoQuery.*;

/**
 * Created by dimka on 16.11.2016.
 */

public class MovieDaoImpl implements MovieDao {
    private static final Logger LOGGER = Logger.getLogger(MovieDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = jdbcTemplate.query(FIND_ALL_SQL, new MovieMapper());
        LOGGER.info("Find all movies: found " + movies.size() + " movie objects");
        return movies;
    }

    @Override
    public List<Movie> findWhereRollingPeriodWasStarted() {
        List<Movie> movies = jdbcTemplate.query(FIND_WHERE_ROLLING_PERIOD_WAS_STARTED, new MovieMapper());
        LOGGER.info("Find movies by rolling period was startred: found " + movies.size() + " movie object");
        return movies;
    }

    @Override
    public Movie getById(long id) {
        Movie movie = null;
        try {
            movie = jdbcTemplate.queryForObject(FIND_MOVIE_BY_ID, new Object[]{id}, new MovieMapper());
            LOGGER.info("Get seance by id: found not null movie");
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Can't find seance with id " + id + " return null");
        }
        return movie;
    }

    @Override
    public void save(Movie movie) {
        if(movie == null) {
            throw new IllegalArgumentException("Can't save null as seance");
        }

        int affected = 0;

        affected += jdbcTemplate.update(MERGE_MOVIE_OBJECT, movie.getId(), movie.getName());
        affected += jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_DESCRIPTION, movie.getDescription(), movie.getId());
        affected += jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_DURATION, movie.getDuration(), movie.getId());
        affected += jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_IMDB, movie.getImdb(), movie.getId());
        affected += jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_PERIODICITY, movie.getPeriodicity(), movie.getId());
        affected += jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_BASE_PRICE, movie.getBasePrice(), movie.getId());
        affected += jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_ROLLING_START, new Date(movie.getStartDate().getTime()), movie.getId());
        affected += jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_ROLLING_END, new Date(movie.getEndDate().getTime()), movie.getId());
        affected += jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_POSTER, movie.getPoster(), movie.getId());

        LOGGER.info("Save movie: affected " + affected + " rows");
    }

    @Override
    public void delete(Movie movie) {
        if(movie == null) {
            throw new IllegalArgumentException("Can't delete null movie");
        }
        int affected = jdbcTemplate.update(DELETE_MOVIE, movie.getId());

        LOGGER.info("Delete movie: affected " + affected + " rows");
    }

    class MovieMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
            Movie movie = new Movie();
            movie.setId(resultSet.getLong("id"));
            movie.setName(resultSet.getString("title"));
            movie.setDescription(resultSet.getString("description"));
            movie.setDuration(resultSet.getString("duration") == null ? null : resultSet.getInt("duration"));
            movie.setImdb(resultSet.getString("imdb") == null ? null : resultSet.getInt("imdb"));
            movie.setPeriodicity(resultSet.getString("periodicity") == null ? null : resultSet.getInt("periodicity"));
            movie.setBasePrice(resultSet.getString("basePrice") == null ? null : resultSet.getInt("basePrice"));
            movie.setPoster(resultSet.getString("poster"));
            movie.setStartDate(resultSet.getDate("startDate") == null ? null : new Date(resultSet.getDate("startDate").getTime()));
            movie.setEndDate(resultSet.getDate("endDate") == null ? null : new Date(resultSet.getDate("endDate").getTime()));
            return movie;
        }
    }
}