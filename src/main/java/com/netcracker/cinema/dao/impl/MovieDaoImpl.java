package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.MovieDao;
import com.netcracker.cinema.model.Movie;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by dimka on 16.11.2016.
 */
@Repository
public class MovieDaoImpl implements MovieDao, MovieDaoQuery {
    private static final Logger LOGGER = Logger.getLogger(MovieDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = jdbcTemplate.query(FIND_ALL_SQL, new MovieMapper());
        return movies;
    }

    @Override
    public Movie getById(long id) {
        Movie movie = jdbcTemplate.queryForObject(FIND_MOVIE_BY_ID, new Object[]{id}, new MovieMapper());
        return movie;
    }

    @Override
    public void save(Movie movie) {
        jdbcTemplate.update(MERGE_MOVIE_OBJECT, new Object[] {movie.getId(), movie.getName()});
        jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_DESCRIPTION, new Object[]{movie.getDescription(), movie.getId()});
        jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_DURATION, new Object[]{movie.getDuration(), movie.getId()});
        jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_IMDB, new Object[]{movie.getImdb(), movie.getId()});
        jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_PERIODICITY, new Object[]{movie.getPeriodicity(), movie.getId()});
        jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_BASE_PRICE, new Object[]{movie.getBasePrice(), movie.getId()});
        jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_ROLLING_START, new Object[]{getCurrentDate(movie.getStartDate()), movie.getId()});
        jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_ROLLING_END, new Object[]{getCurrentDate(movie.getEndDate()), movie.getId()});
        jdbcTemplate.update(MERGE_MOVIE_ATTRIBUTES_POSTER, new Object[]{movie.getPoster(), movie.getId()});
    }

    @Override
    public void delete(Movie movie) {
        jdbcTemplate.update(DELETE_MOVIE, new Object[] {movie.getId()});
    }

    private static java.sql.Date getCurrentDate(LocalDate localDate) {
        if(localDate == null) {
            return null;
        }

        java.sql.Date date = java.sql.Date.valueOf(localDate);
        return date;
    }

    class MovieMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
            Movie movie = new Movie();
            movie.setId(resultSet.getInt("id"));
            movie.setName(resultSet.getString("title"));
            movie.setDescription(resultSet.getString("description"));
            movie.setDuration(resultSet.getString("duration") == null ? null : resultSet.getInt("duration"));
            movie.setImdb(resultSet.getString("imdb") == null ? null : resultSet.getInt("imdb"));
            movie.setPeriodicity(resultSet.getString("periodicity") == null ? null : resultSet.getInt("periodicity"));
            movie.setBasePrice(resultSet.getString("basePrice") == null ? null : resultSet.getInt("periodicity"));
            movie.setPoster(resultSet.getString("poster"));
            movie.setStartDate(resultSet.getDate("startDate") == null ? null : resultSet.getDate("startDate").toLocalDate());
            movie.setEndDate(resultSet.getDate("endDate") == null ? null : resultSet.getDate("endDate").toLocalDate());
            return movie;
        }
    }
}