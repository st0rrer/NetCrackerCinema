package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.TicketDao;
import com.netcracker.cinema.model.Ticket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.netcracker.cinema.dao.impl.queries.TicketDaoQuery.*;

/**
 * Created by Titarenko on 01.12.2016.
 */
@Repository
public class TicketDaoImpl implements TicketDao {
    private List<Ticket> list = Collections.emptyList();

    private static final Logger LOGGER = Logger.getLogger(TicketDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Ticket> findAll() {
        list = jdbcTemplate.query(FIND_ALL_TICKETS, new TicketMapper());
        if (list.size() < 1) {
            LOGGER.info("There are no any tickets in DB");
        }
        return list;
    }

    @Override
    public List<Ticket> getTicketsByCode(long code) {
        List<Ticket> ticketsByCode = jdbcTemplate.query(FIND_TICKETS_BY_CODE, new TicketMapper(), String.valueOf(code));
        LOGGER.info("Find all tickets by code " + code + " : found " + ticketsByCode.size() + " ticket objects");
        return ticketsByCode;
    }

    @Override
    public List<Ticket> getBySeanceOrPlace(long id) {
        if (id > 0) {
            list = jdbcTemplate.query(FIND_TICKET_BY_SEANCE_OR_PLACE, new TicketMapper(), id);
        }
        if (list.size() < 1) {
            LOGGER.info("There are no tickets for seance or place with id = " + id);
        } else {
            LOGGER.info("There are + " + list.size() + " tickets for id = " + id);
        }
        return list;
    }

    @Override
    public Ticket get(long ticketId) {
        if (ticketId < 1) {
            LOGGER.info("Id should be > 0");
            return null;
        } else {
            Ticket ticket = null;
            try {
                ticket = jdbcTemplate.queryForObject(FIND_TICKET_BY_ID, new TicketMapper(), ticketId);
            } catch (EmptyResultDataAccessException e) {
                LOGGER.error("Id is not exist for tickets", e);
            }
            return ticket;
        }
    }

    @Override
    public void delete(long ticketId) {
        if (ticketId < 1) {
            LOGGER.info("Id should be > 0");
        } else {
            LOGGER.trace("Deleting ticket with id = " + ticketId);
            jdbcTemplate.update(DELETE_TICKET, ticketId);
        }
    }

    @Override
    public long save(Ticket ticket) {
        if (ticket == null) {
            LOGGER.info("Ticket == null");
            return 0;
        } else {
            Object[] arrayOfFields = new Object[]{ticket.getId(), ticket.getCode(),
                    ticket.getEmail(), ticket.getPrice(), (ticket.isPaid() + "").toUpperCase(),
                    ticket.getSeanceId(), ticket.getPlaceId()};
            LOGGER.info("Saving ticket: " + ticket);
            return jdbcTemplate.queryForObject(
                    SAVE_TICKET, long.class, arrayOfFields);
        }
    }

    @Override
    public int soldTickets(long objId, Date startDate, Date endDate) {
        if ((objId < 1) || (startDate.after(endDate))) {
            LOGGER.info("Id should be > 0");
            return 0;
        } else {
            return jdbcTemplate.queryForObject(
                    SOLD_TICKETS, int.class, objId, startDate, endDate);
        }
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
