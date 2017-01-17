package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.SeanceDao;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Seance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.SeanceDaoQuery.*;

public class SeanceDaoImpl implements SeanceDao {
    private static final Logger logger = Logger.getLogger(SeanceDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SeanceDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Seance> findAll() {
        List<Seance> seances = jdbcTemplate.query(FIND_ALL_SQL, new SeanceRowMapper());
        logger.info("Find all seances: found " + seances.size() + " seance objects");
        return seances;
    }

    @Override
    public List<Seance> findAll(SeanceFilter filter) {
        if(filter == null) {
            throw new IllegalArgumentException("filter can't be null");
        }

        List<Seance> seances = jdbcTemplate.query(filter.buildQuery(), new SeanceRowMapper());
        logger.info("Find all seances with filter: found " + seances.size() + " seance objects");
        return seances;
    }

    @Override
    public Seance getById(long id) {
        Seance seance = jdbcTemplate.queryForObject(FIND_SEANCE_BY_ID, new Object[]{id}, new SeanceRowMapper());
        logger.info("Get seance by id: found not null seance");
        return seance;
    }

    @Override
    public List<Seance> getByHallAndDate(long hallId, Date date) {
        if(date == null) {
            throw new IllegalArgumentException("date can't be null");
        }
        List<Seance> seances = jdbcTemplate.query(
                FIND_SEANCE_BY_HALL_AND_DATE, new SeanceRowMapper(), hallId, date);
        logger.info("Get seance by hall and date: found " + seances.size() + " seances for hall " + hallId);
        return seances;
    }

    @Override
    public void save(Seance seance) {
        if(seance == null) {
            throw new IllegalArgumentException("Can't save null as seance");
        }

        int affected = jdbcTemplate.update(MERGE_SEANCE_OBJECT, seance.getId());
        jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_DATE, seance.getSeanceDate(), seance.getId());
        jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_MOVIE, seance.getMovieId(), seance.getId());
        jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_HALL, seance.getHallId(), seance.getId());

        if (seance.getId() == 0) {
            seance.setId(jdbcTemplate.queryForObject(SELECT_ID_FOR_INSERTED_SEANCE, Long.class));
        }

        logger.info("Save seance: affected " + affected + " rows");
    }

    @Override
    public void delete(Seance seance) {
        if(seance == null) {
            throw new IllegalArgumentException("Can't delete null seance");
        }

        int affected = jdbcTemplate.update(DELETE_SEANCE, seance.getId());
        logger.info("Delete seance: affected " + affected + " rows");
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize) {
        return new SeancePaginator(pageSize, new SeanceFilter());
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize, SeanceFilter filter) {
        if(filter == null) {
            throw new IllegalArgumentException("Can't return paginator with null filter");
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
