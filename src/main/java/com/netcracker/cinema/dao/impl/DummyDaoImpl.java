package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.DummyDao;
import com.netcracker.cinema.model.Dummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by dimka on 05.11.2016.
 */
public class DummyDaoImpl implements DummyDao {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Dummy dummy) {

        String sql = "INSERT INTO DUMMIES(DUMMY_ID, DUMMY_NAME)" +
                "VALUES(?, ?)";

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql, new Object[] {
                dummy.getId(), dummy.getName()
        });
    }
}
