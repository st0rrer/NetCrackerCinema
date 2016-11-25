package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.ZoneDao;
import com.netcracker.cinema.dao.impl.queries.ZoneDaoQuery;
import com.netcracker.cinema.model.Zone;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.ZoneDaoQuery.*;

public class ZoneDaoImpl implements ZoneDao {
    private static final Logger LOGGER = Logger.getLogger(MovieDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ZoneDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Zone> findAll() {
        List<Zone> zones = jdbcTemplate.query(FIND_ALL_SQL, new ZoneDaoImpl.ZoneMapper());
        return zones;
    }

    @Override
    public Zone getById(long id) {
        Zone zone = jdbcTemplate.queryForObject(FIND_ZONE_BY_ID, new Object[]{id}, new ZoneMapper());
        return zone;
    }

    @Override
    public void save(Zone hall) {
        jdbcTemplate.update(INSERT_ZONE, new Object[]{hall.getName()});
    }

    @Override
    public void update(Zone hall) {
        jdbcTemplate.update(UPDATE_ZONE_OBJECTS, new Object[]{hall.getName(), hall.getId()});
    }

    @Override
    public void delete(Zone hall) {
        jdbcTemplate.update(DELETE_ZONE, new Object[]{hall.getId()});
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