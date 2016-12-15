package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.PlaceDao;
import com.netcracker.cinema.model.Place;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import static com.netcracker.cinema.dao.impl.queries.PlaceDaoQuery.*;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
        List<Place> places = jdbcTemplate.query(FIND_ALL_SQL, new PlaceRowMapper());
        LOGGER.info("Find all places: found " + places.size() + " place objects");
        return places;
    }

    @Override
    public Place getById(long id) {
        Place place = jdbcTemplate.queryForObject(FIND_PLACE_BY_ID, new Object[]{id}, new PlaceRowMapper());
        LOGGER.info("Get place by id: found not null place");
        return place;
    }

    @Override
    public List<Place> getByHall(long id) {
        List<Place> places = jdbcTemplate.query(FIND_PLACE_BY_HALL, new Object[]{id}, new PlaceRowMapper());
        LOGGER.info("Get place by id: found not null place");
        return places;
    }

    @Override
    public List<Place> getByZone(long id) {
        List<Place> places = jdbcTemplate.query(FIND_PLACE_BY_ZONE, new Object[]{id}, new PlaceRowMapper());
        LOGGER.info("Get place by id: found not null place");
        return places;
    }

    @Override
    public void save(Place place) {
        int affected = jdbcTemplate.update(MERGE_PLACE_OBJECT, place.getId());
        jdbcTemplate.update(MERGE_PLACE_ATTRIBUTE_NUMBER, place.getNumber(), place.getId());
        jdbcTemplate.update(MERGE_PLACE_ATTRIBUTE_ROW, place.getRowNumber(), place.getId());
        jdbcTemplate.update(MERGE_PLACE_ATTRIBUTE_HALL_REF, place.getHallId(), place.getId());
        jdbcTemplate.update(MERGE_PLACE_ATTRIBUTE_ZONE_REF, place.getZoneId(), place.getId());

        if(place.getId() == 0) {
            place.setId(jdbcTemplate.queryForObject(SELECT_ID, Long.class));
        }

        LOGGER.info("Save place: affected " + affected + " rows");

    }

    @Override
    public void delete(Place place) {
        int affected = jdbcTemplate.update(DELETE_PLACE, new Object[]{place.getId()});

        LOGGER.info("Delete place: affected " + affected + " rows");
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
