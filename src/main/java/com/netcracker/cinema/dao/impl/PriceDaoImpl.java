package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.PriceDao;
import com.netcracker.cinema.model.Price;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Price> prices = jdbcTemplate.query(FIND_ALL_PRICES, new PriceMapper());
        LOGGER.info("Find all prices: found " + prices.size() + " price objects");
        return prices;
    }

    @Override
    public Price getById(long id) {
        Price price = jdbcTemplate.queryForObject(FIND_PRICE_BY_ID, new Object[]{id}, new PriceMapper());
        LOGGER.info("Get price by id: found not null price");
        return price;
    }

    @Override
    public void save(Price price) {
        int affected = jdbcTemplate.update(MERGE_PRICE_OBJECT, new Object[]{price.getId()});
        jdbcTemplate.update(MERGE_PRICE_ATTRIBUTE, new Object[]{price.getPrice(), price.getId()});
        jdbcTemplate.update(MERGE_PRICE_ATTRIBUTE_ZONE, new Object[]{price.getZoneId(), price.getId()});
        jdbcTemplate.update(MERGE_PRICE_ATTRIBUTE_SEANCE, new Object[]{price.getSeanceId(), price.getId()});

        if(price.getId() == 0) {
            price.setId(jdbcTemplate.queryForObject(SELECT_ID_FOR_INSERTED_PRICE, Long.class));
        }

        LOGGER.info("Save price: affected " + affected + " rows");
    }

    @Override
    public void delete(Price price) {
        int affected = jdbcTemplate.update(DELETE_PRICE, new Object[] {price.getId()});

        LOGGER.info("Delete price: affected " + affected + " rows");
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
