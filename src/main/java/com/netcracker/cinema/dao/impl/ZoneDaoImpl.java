package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.ZoneDao;
import com.netcracker.cinema.exception.CinemaDaoException;
import com.netcracker.cinema.exception.CinemaEmptyResultException;
import com.netcracker.cinema.model.Zone;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.ZoneDaoQuery.*;

public class ZoneDaoImpl implements ZoneDao {
    private static final Logger LOGGER = Logger.getLogger(ZoneDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ZoneDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Zone> findAll() {
        try {
            List<Zone> zones = jdbcTemplate.query(FIND_ALL_SQL, new ZoneDaoImpl.ZoneMapper());
            LOGGER.info("Found " + zones.size() + " zone objects");
            return zones;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Zone getById(long id) {
        try {
            Zone zone = jdbcTemplate.queryForObject(FIND_ZONE_BY_ID, new Object[]{id}, new ZoneMapper());
            LOGGER.info("Found not null zone for id " + id);
            return zone;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no zone objects for id " + id);
            throw new CinemaEmptyResultException("There are no zone objects for id " + id, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Zone zone) {
        if (zone == null) {
            LOGGER.error("Attempt to save null zone");
            throw new CinemaDaoException("Can't save null zone");
        }
        try {
            long affected = jdbcTemplate.update(MERGE_ZONE_OBJECT, zone.getId(), zone.getName());
            if (zone.getId() == 0) {
                zone.setId(jdbcTemplate.queryForObject(SELECT_ID_FOR_INSERTED_ZONE, Long.class));
            }
            LOGGER.info("Zone was saved, affected " + affected + " rows, now zone has id " + zone.getId());
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Zone zone) {
        if (zone == null) {
            LOGGER.error("Attemtp to delete null zone");
            throw new CinemaDaoException("Can't delete null zone");
        }
        try {
            int affected = jdbcTemplate.update(DELETE_ZONE, zone.getId());
            LOGGER.info("Zone with id " + zone.getId() + " was deleted, affected " + affected + " rows");
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    class ZoneMapper implements RowMapper<Zone> {
        @Override
        public Zone mapRow(ResultSet resultSet, int i) throws SQLException {
            Zone zone = new Zone();
            zone.setId(resultSet.getLong("id"));
            zone.setName(resultSet.getString("name"));
            return zone;
        }
    }
}