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
        return movies;
    }

    @Override
    public Movie getById(long id) {
        Movie movie = jdbcTemplate.queryForObject(FIND_MOVIE_BY_ID, new Object[]{id}, new MovieMapper());
        return movie;
    }

    @Override
    public void save(Movie movie) {
        jdbcTemplate.update(INSERT_MOVIE, new Object[]{movie.getName(), movie.getDescription(),
                String.valueOf(movie.getDuration()), String.valueOf(movie.getImdb()),
                String.valueOf(movie.getPeriodicity()), String.valueOf(movie.getBasePrice()),
                getCurrentDate(movie.getStartDate()), getCurrentDate(movie.getEndDate()), movie.getPoster()});
    }

    @Override
    public void update(Movie movie) {
        jdbcTemplate.update(UPDATE_MOVIE_OBJECTS, new Object[]{movie.getName(), movie.getId()});
        jdbcTemplate.update(UPDATE_MOVIE_ATTRIBUTES_DESCRIPTION, new Object[]{movie.getDescription(), movie.getId()});
        jdbcTemplate.update(UPDATE_MOVIE_ATTRIBUTES_DURATION, new Object[]{movie.getDuration(), movie.getId()});
        jdbcTemplate.update(UPDATE_MOVIE_ATTRIBUTES_IMDB, new Object[]{movie.getImdb(), movie.getId()});
        jdbcTemplate.update(UPDATE_MOVIE_ATTRIBUTES_PERIODICITY, new Object[]{movie.getPeriodicity(), movie.getId()});
        jdbcTemplate.update(UPDATE_MOVIE_ATTRIBUTES_BASE_PRICE, new Object[]{movie.getBasePrice(), movie.getId()});
        jdbcTemplate.update(UPDATE_MOVIE_ATTRIBUTES_ROLLING_START, new Object[]{getCurrentDate(movie.getStartDate()), movie.getId()});
        jdbcTemplate.update(UPDATE_MOVIE_ATTRIBUTES_ROLLING_END, new Object[]{getCurrentDate(movie.getEndDate()), movie.getId()});
        jdbcTemplate.update(UPDATE_MOVIE_ATTRIBUTES_POSTER, new Object[]{movie.getPoster(), movie.getId()});
    }

    @Override
    public void delete(Movie movie) {
        jdbcTemplate.update(DELETE_MOVIE, new Object[] {movie.getId()});
    }

    private static java.sql.Date getCurrentDate(LocalDate localDate) {
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
            movie.setDuration(Integer.parseInt(resultSet.getString("duration")));
            movie.setImdb(Integer.parseInt(resultSet.getString("imdb")));
            movie.setPeriodicity(Integer.parseInt(resultSet.getString("periodicity")));
            movie.setBasePrice(Integer.parseInt(resultSet.getString("basePrice")));
            movie.setPoster(resultSet.getString("poster"));
            movie.setStartDate(resultSet.getDate("startDate").toLocalDate());
            movie.setEndDate(resultSet.getDate("endDate").toLocalDate());
            return movie;
        }
    }

    private static final String FIND_ALL_SQL =
        "SELECT MOVIE.OBJECT_ID id, MOVIE.NAME title, DESC_ATT.VALUE description, DUR_ATT.VALUE duration, IMDB_ATT.VALUE IMDB, \n" +
        "PERIOD_ATT.VALUE periodicity, BASE_ATT.VALUE basePrice, RSTART_ATT.DATE_VALUE startDate, REND_ATT.DATE_VALUE endDate, POSTER_ATT.VALUE poster \n" +
        "FROM OBJECTS MOVIE INNER JOIN ATTRIBUTES DESC_ATT \n" +
        "ON MOVIE.OBJECT_ID = DESC_ATT.OBJECT_ID \n" +
        "INNER JOIN ATTRIBUTES DUR_ATT \n" +
        "ON MOVIE.OBJECT_ID = DUR_ATT.OBJECT_ID \n" +
        "INNER JOIN ATTRIBUTES IMDB_ATT \n" +
        "ON MOVIE.OBJECT_ID = IMDB_ATT.OBJECT_ID \n" +
        "INNER JOIN ATTRIBUTES PERIOD_ATT \n" +
        "ON MOVIE.OBJECT_ID = PERIOD_ATT.OBJECT_ID \n" +
        "INNER JOIN ATTRIBUTES BASE_ATT \n" +
        "ON MOVIE.OBJECT_ID = BASE_ATT.OBJECT_ID \n" +
        "INNER JOIN ATTRIBUTES RSTART_ATT \n" +
        "ON MOVIE.OBJECT_ID = RSTART_ATT.OBJECT_ID \n" +
        "INNER JOIN ATTRIBUTES REND_ATT \n" +
        "ON MOVIE.OBJECT_ID = REND_ATT.OBJECT_ID \n" +
        "INNER JOIN ATTRIBUTES POSTER_ATT \n" +
        "ON MOVIE.OBJECT_ID = POSTER_ATT.OBJECT_ID \n" +
        "WHERE MOVIE.OBJECT_TYPE_ID = 1 \n" +
        "AND DESC_ATT.ATTR_ID = 1 AND DUR_ATT.ATTR_ID = 2 AND IMDB_ATT.ATTR_ID = 3 AND PERIOD_ATT.ATTR_ID = 4 \n" +
        "AND BASE_ATT.ATTR_ID = 5 AND RSTART_ATT.ATTR_ID = 6 AND REND_ATT.ATTR_ID = 7 AND POSTER_ATT.ATTR_ID = 8";

    private static final String FIND_MOVIE_BY_ID =
        "SELECT MOVIE.OBJECT_ID id, MOVIE.NAME title, DESC_ATT.VALUE description, DUR_ATT.VALUE duration, IMDB_ATT.VALUE IMDB, \n" +
        "PERIOD_ATT.VALUE periodicity, BASE_ATT.VALUE basePrice, RSTART_ATT.DATE_VALUE startDate, REND_ATT.DATE_VALUE endDate, POSTER_ATT.VALUE poster \n" +
        "FROM OBJECTS MOVIE INNER JOIN ATTRIBUTES DESC_ATT \n" +
        "ON MOVIE.OBJECT_ID = DESC_ATT.OBJECT_ID\n" +
        "INNER JOIN ATTRIBUTES DUR_ATT\n" +
        "ON MOVIE.OBJECT_ID = DUR_ATT.OBJECT_ID\n" +
        "INNER JOIN ATTRIBUTES IMDB_ATT\n" +
        "ON MOVIE.OBJECT_ID = IMDB_ATT.OBJECT_ID\n" +
        "INNER JOIN ATTRIBUTES PERIOD_ATT\n" +
        "ON MOVIE.OBJECT_ID = PERIOD_ATT.OBJECT_ID\n" +
        "INNER JOIN ATTRIBUTES BASE_ATT\n" +
        "ON MOVIE.OBJECT_ID = BASE_ATT.OBJECT_ID\n" +
        "INNER JOIN ATTRIBUTES RSTART_ATT\n" +
        "ON MOVIE.OBJECT_ID = RSTART_ATT.OBJECT_ID\n" +
        "INNER JOIN ATTRIBUTES REND_ATT\n" +
        "ON MOVIE.OBJECT_ID = REND_ATT.OBJECT_ID\n" +
        "INNER JOIN ATTRIBUTES POSTER_ATT\n" +
        "ON MOVIE.OBJECT_ID = POSTER_ATT.OBJECT_ID\n" +
        "WHERE MOVIE.OBJECT_ID = ?\n" +
        "AND MOVIE.OBJECT_TYPE_ID = 1 AND DESC_ATT.ATTR_ID = 1 AND DUR_ATT.ATTR_ID = 2 AND IMDB_ATT.ATTR_ID = 3\n" +
        "AND PERIOD_ATT.ATTR_ID = 4 AND BASE_ATT.ATTR_ID = 5 AND RSTART_ATT.ATTR_ID = 6 AND REND_ATT.ATTR_ID = 7\n" +
        "AND POSTER_ATT.ATTR_ID = 8";

    private static final String INSERT_MOVIE =
        "INSERT ALL\n" +
        "INTO OBJECTS(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) VALUES (GET_OBJ_ID.nextval, NULL, 1, ?, NULL)\n" +
        "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (1, GET_OBJ_ID.currval, ?, NULL) \n" + //DESCRIPTION
        "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (2, GET_OBJ_ID.currval, ?, NULL) \n" + //DURATION
        "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (3, GET_OBJ_ID.currval, ?, NULL) \n" + //IMDB
        "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (4, GET_OBJ_ID.currval, ?, NULL) \n" + //PERIODICITY
        "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (5, GET_OBJ_ID.currval, ?, NULL) \n" + //BASE_PRICE
        "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (6, GET_OBJ_ID.currval, NULL, ?) \n" + //ROLLING_START
        "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (7, GET_OBJ_ID.currval, NULL, ?) \n" + //ROLLING_END
        "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (8, GET_OBJ_ID.currval, ?, NULL) \n" + //POSTER
        "SELECT * FROM dual";

    private static final String UPDATE_MOVIE_OBJECTS =
        "UPDATE OBJECTS \n" +
        "SET NAME = ? \n" +
        "WHERE OBJECT_ID = ?";
    private static final String UPDATE_MOVIE_ATTRIBUTES_DESCRIPTION =
        "UPDATE ATTRIBUTES \n" +
        "SET VALUE = ? \n" +
        "WHERE OBJECT_ID = ? AND ATTR_ID = 1";
    private static final String UPDATE_MOVIE_ATTRIBUTES_DURATION =
        "UPDATE ATTRIBUTES \n" +
        "SET VALUE = ? \n" +
        "WHERE OBJECT_ID = ? AND ATTR_ID = 2";
    private static final String UPDATE_MOVIE_ATTRIBUTES_IMDB =
        "UPDATE ATTRIBUTES \n" +
        "SET VALUE = ? \n" +
        "WHERE OBJECT_ID = ? AND ATTR_ID = 3";
    private static final String UPDATE_MOVIE_ATTRIBUTES_PERIODICITY =
        "UPDATE ATTRIBUTES \n" +
        "SET VALUE = ? \n" +
        "WHERE OBJECT_ID = ? AND ATTR_ID = 4";
    private static final String UPDATE_MOVIE_ATTRIBUTES_BASE_PRICE =
        "UPDATE ATTRIBUTES \n" +
        "SET VALUE = ? \n" +
        "WHERE OBJECT_ID = ? AND ATTR_ID = 5";
    private static final String UPDATE_MOVIE_ATTRIBUTES_ROLLING_START =
        "UPDATE ATTRIBUTES \n" +
        "SET DATE_VALUE = ? \n" +
        "WHERE OBJECT_ID = ? AND ATTR_ID = 6";
    private static final String UPDATE_MOVIE_ATTRIBUTES_ROLLING_END =
        "UPDATE ATTRIBUTES \n" +
        "SET DATE_VALUE = ? \n" +
        "WHERE OBJECT_ID = ? AND ATTR_ID = 7";
    private static final String UPDATE_MOVIE_ATTRIBUTES_POSTER =
        "UPDATE ATTRIBUTES \n" +
        "SET VALUE = ? \n" +
        "WHERE OBJECT_ID = ? AND ATTR_ID = 8";

    private static final String DELETE_MOVIE =
            "DELETE FROM OBJECTS \n" +
            "  WHERE OBJECT_ID = ?";
}