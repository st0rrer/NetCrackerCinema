package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.SeanceDao;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.dao.Paginator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import static com.netcracker.cinema.dao.impl.queries.SeanceDaoQuery.*;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public void save(Seance seance) {
        int affected = jdbcTemplate.update(MERGE_SEANCE_OBJECT, seance.getId());
        jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_DATE, seance.getSeanceDate(), seance.getId());
        jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_MOVIE, seance.getMovieId(), seance.getId());
        jdbcTemplate.update(MERGE_SEANCE_ATTRIBUTE_HALL, seance.getHallId(), seance.getId());

        if(seance.getId() == 0) {
            seance.setId(jdbcTemplate.queryForObject(SELECT_ID_FOR_INSERTED_SEANCE, Long.class));
        }

        logger.info("Save seance: affected " + affected + " rows");
    }

    @Override
    public void delete(Seance seance) {
        int affected = jdbcTemplate.update(DELETE_SEANCE, new Object[]{seance.getId()});
        logger.info("Delete seance: affected " + affected + " rows");
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize) {
        return new SeancePaginator(pageSize, new SeanceFilter());
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize, SeanceFilter filter) {
        return new SeancePaginator(pageSize, filter);
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
