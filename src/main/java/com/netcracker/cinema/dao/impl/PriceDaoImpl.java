package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.PriceDao;
import com.netcracker.cinema.exception.CinemaDaoException;
import com.netcracker.cinema.exception.CinemaEmptyResultException;
import com.netcracker.cinema.model.Price;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.PriceDaoQuery.*;

/**
 * Created by Parpalak on 11.12.2016.
 */
public class PriceDaoImpl implements PriceDao {
    private static final Logger LOGGER = Logger.getLogger(PriceDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PriceDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Price> findAll() {
        try {
            List<Price> prices = jdbcTemplate.query(FIND_ALL_PRICES, new PriceMapper());
            LOGGER.info("Found " + prices.size() + " price objects");
            return prices;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Price getById(long id) {
        try {
            Price price = jdbcTemplate.queryForObject(FIND_PRICE_BY_ID, new Object[]{id}, new PriceMapper());
            LOGGER.info("Found not null price for id " + id);
            return price;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no price with id " + id);
            throw new CinemaEmptyResultException("There are no price with id " + id, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Price price) {
        if(price == null) {
            LOGGER.error("Attempt to save null price");
            throw new IllegalArgumentException("Can't save null price");
        }
        try {
            int affected = jdbcTemplate.update(MERGE_PRICE_OBJECT, price.getId());
            affected += jdbcTemplate.update(MERGE_PRICE_ATTRIBUTE, price.getPrice(), price.getId());
            affected += jdbcTemplate.update(MERGE_PRICE_ATTRIBUTE_ZONE, price.getZoneId(), price.getId());
            affected += jdbcTemplate.update(MERGE_PRICE_ATTRIBUTE_SEANCE, price.getSeanceId(), price.getId());
            if (price.getId() == 0) {
                price.setId(jdbcTemplate.queryForObject(SELECT_ID_FOR_INSERTED_PRICE, Long.class));
            }
            LOGGER.info("Price was saved, affected " + affected + " rows, now price has id " + price.getId());
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Price price) {
        if (price == null) {
            LOGGER.error("Attempt to delete null price");
            throw new IllegalArgumentException("Can't delete null price");
        }
        try {
            int affected = jdbcTemplate.update(DELETE_PRICE, price.getId());
            LOGGER.info("Price with id " + price.getId() + " was deleted, affected " + affected + " rows");
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public int getPriceBySeanceColRow(long seanceId, int col, int row) {
        try {
            int price = jdbcTemplate.queryForObject(SELECT_PRICE_SEANCE_COL_ROW, new Object[]{String.valueOf(col), String.valueOf(row), seanceId}, Integer.class);
            LOGGER.info("Found price " + price + " for seanceId " + seanceId + " column " + col + " row " + row);
            return price;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no price for seanceId " + seanceId + " column " + col + " row " + row, e);
            throw new CinemaEmptyResultException("There are no price for seanceId " + seanceId + " column " + col + " row " + row, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Price getPriceBySeanceZone(long seanceId, long zoneId) {
        try {
            Price price = jdbcTemplate.queryForObject(
                    SELECT_PRICE_BY_SEANCE_ZONE, new PriceMapper(), seanceId, zoneId);
            LOGGER.info("Found not null price " + price.getPrice() + " for zoneId " + zoneId + " seanceId " + seanceId);
            return price;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no price for seanceId " + seanceId + " zoneId " + zoneId, e);
            throw new CinemaEmptyResultException("There are no price for seanceId " + seanceId + " zoneId " + zoneId, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    class PriceMapper implements RowMapper<Price> {
        @Override
        public Price mapRow(ResultSet resultSet, int i) throws SQLException {
            Price price = new Price();
            price.setId(resultSet.getLong("id"));
            price.setPrice(resultSet.getInt("price"));
            price.setZoneId(resultSet.getLong("zone_id"));
            price.setSeanceId(resultSet.getLong("seance_id"));
            return price;
        }
    }
}
