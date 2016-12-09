package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.TicketDao;
import com.netcracker.cinema.model.Ticket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.TicketDaoQuery.*;

/**
 * Created by Titarenko on 01.12.2016.
 */
@Repository
public class TicketDaoImpl implements TicketDao {

    private static final Logger LOGGER = Logger.getLogger(TicketDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Ticket> findAll() {
        return jdbcTemplate.query(FIND_ALL_TICKETS, new TicketMapper());
    }

    @Override
    public List<Ticket> getBySeanceOrPlace(long id) {
        return jdbcTemplate.query(FIND_TICKET_BY_SEANCE_OR_PLACE, new TicketMapper(), id);
    }

    @Override
    public Ticket getById(long id) {
        Ticket ticket;
        try {
            ticket = jdbcTemplate.queryForObject(FIND_TICKET_BY_ID, new TicketMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.trace("Id is not exist", e);
            ticket = null;
        }
        return ticket;
    }

    @Override
    public void deleteById(long ticketId) {
        LOGGER.trace("Deleting ticket with id = " + ticketId);
        jdbcTemplate.update(DELETE_TICKET, ticketId);
    }

    @Override
    public void delete(Ticket ticket) {
        deleteById(ticket.getId());
    }

    @Override
    public void save(Ticket ticket) {
        Object[] array = new Object[]{ticket.getId(), ticket.getCode(), ticket.getEmail(), ticket.getPrice(),
                (ticket.isPaid() + "").toUpperCase(), ticket.getSeanceId(), ticket.getPlaceId()};
        LOGGER.trace("Save ticket: " + Arrays.toString(array));
        jdbcTemplate.update(SAVE_TICKET, array);
    }

    @Override
    public Integer soldTickets(String objName, Date startDate, Date endDate) {
        List<Integer> list = jdbcTemplate.queryForList(
                SOLD_TICKETS, Integer.class, objName, startDate, endDate);
        return list.get(0);
    }

    private class TicketMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getLong("id"));
            ticket.setCode(rs.getLong("code"));
            ticket.setEmail(rs.getString("email"));
            ticket.setPrice(rs.getInt("price"));
            ticket.setPaid(rs.getString("paid").equals("TRUE"));
            ticket.setSeanceId(rs.getLong("seance"));
            ticket.setPlaceId(rs.getLong("place"));
            return ticket;
        }
    }
}
