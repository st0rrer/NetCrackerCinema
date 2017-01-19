package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.SeanceDao;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.exception.CinemaDaoException;
import com.netcracker.cinema.exception.CinemaEmptyResultException;
import com.netcracker.cinema.model.Seance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.SeanceDaoQuery.*;

public class SeanceDaoImpl implements SeanceDao {
    private static final Logger LOGGER = Logger.getLogger(SeanceDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SeanceDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Seance> findAll() {
        try {
            List<Seance> seances = jdbcTemplate.query(FIND_ALL_SQL, new SeanceRowMapper());
            LOGGER.info("Found " + seances.size() + " seance objects");
            return seances;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Seance> findAll(SeanceFilter filter) {
        if(filter == null) {
            LOGGER.error("Filter can't be null");
            throw new IllegalArgumentException("Filter can't be null");
        }
        try {
            List<Seance> seances = jdbcTemplate.query(filter.buildQuery(), new SeanceRowMapper());
            LOGGER.info("Found " + seances.size() + " seance objects with filter");
            return seances;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Seance getById(long id) {
        try {
            Seance seance = jdbcTemplate.queryForObject(FIND_SEANCE_BY_ID, new Object[]{id}, new SeanceRowMapper());
            LOGGER.info("Found not null seance with id " + id);
            return seance;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no seance with id " + id);
            throw new CinemaEmptyResultException("There are no seance with id " + id, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Seance> getByHallAndDate(long hallId, Date date) {
        if(date == null) {
            LOGGER.error("Date can't be null");
            throw new IllegalArgumentException("Date can't be null");
        }
        try {
            List<Seance> seances = jdbcTemplate.query(
                    FIND_SEANCE_BY_HALL_AND_DATE, new SeanceRowMapper(), hallId, date);
            LOGGER.info("Found " + seances.size() + " seances for hall " + hallId + " date " + date);
            return seances;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Seance seance) {
        if(seance == null) {
            LOGGER.error("Attempt to save null seance");
            throw new IllegalArgumentException("Can't save null seance");
        }
        try {
            int affected = jdbcTemplate.update(MERGE_SEANCE_OBJECT, seance.getId());
            affected += jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_DATE, seance.getSeanceDate(), seance.getId());
            affected += jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_MOVIE, seance.getMovieId(), seance.getId());
            affected += jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_HALL, seance.getHallId(), seance.getId());
            if (seance.getId() == 0) {
                seance.setId(jdbcTemplate.queryForObject(SELECT_ID_FOR_INSERTED_SEANCE, Long.class));
            }
            LOGGER.info("Seance was saved, affected " + affected + " rows, now seance has id " + seance.getId());
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Seance seance) {
        if(seance == null) {
            LOGGER.error("Attempt to delete null seance");
            throw new IllegalArgumentException("Can't delete null seance");
        }
        try {
            int affected = jdbcTemplate.update(DELETE_SEANCE, seance.getId());
            LOGGER.info("Seance with id " + seance.getId() + " was deleted, affected " + affected + " rows");
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize) {
        if (pageSize <= 0) {
            LOGGER.error("Attempt to create paginator with page size=" + pageSize);
            throw new IllegalArgumentException("Can't create paginator with pageSize=" + pageSize);
        }
        return new SeancePaginator(pageSize, new SeanceFilter());
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize, SeanceFilter filter) {
        if(filter == null) {
            LOGGER.error("Can't return paginator with null filter");
            throw new IllegalArgumentException("Can't return paginator with null filter");
        }
        if (pageSize <= 0) {
            LOGGER.error("Attempt to create paginator with page size=" + pageSize);
            throw new IllegalArgumentException("Can't create paginator with pageSize=" + pageSize);
        }
        return new SeancePaginator(pageSize, filter);
    }

    @Override
    public long getCountActiveMoviesById(long movieId) {
        return jdbcTemplate.queryForObject(COUNT_ACTIVE_MOVIES_BY_ID, Long.class, movieId);
    }

    private class SeanceRowMapper implements RowMapper<Seance> {
        @Override
        public Seance mapRow(ResultSet resultSet, int i) throws SQLException {
            Seance seance = new Seance();
            seance.setId(resultSet.getLong("ID"));
            seance.setSeanceDate(resultSet.getTimestamp("START_DATE"));
            seance.setMovieId(resultSet.getLong("MOVIE_ID"));
            seance.setHallId(resultSet.getLong("HALL_ID"));
            return seance;
        }
    }

    private class SeancePaginator extends AbstractPaginator<Seance> {

        public SeancePaginator(int pageSize, SeanceFilter filter) {
            super(filter, new SeanceRowMapper(), jdbcTemplate, pageSize);
        }
    }
}
