package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.TicketDao;
import com.netcracker.cinema.exception.CinemaDaoException;
import com.netcracker.cinema.exception.CinemaEmptyResultException;
import com.netcracker.cinema.model.Ticket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.netcracker.cinema.dao.impl.queries.TicketDaoQuery.*;

/**
 * Created by Titarenko on 01.12.2016.
 */
public class TicketDaoImpl implements TicketDao {
    private static final Logger LOGGER = Logger.getLogger(TicketDaoImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Ticket> findAll() {
        try {
            List<Ticket> tickets = jdbcTemplate.query(FIND_ALL_TICKETS, new TicketMapper());
            LOGGER.info("Found " + tickets.size() + " ticket objects");
            return tickets;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Ticket> getTicketsByCode(long code) {
        try {
            List<Ticket> tickets = jdbcTemplate.query(FIND_TICKETS_BY_CODE, new TicketMapper(), code);
            LOGGER.info("Found " + tickets.size() + " ticket objects with code " + code);
            return tickets;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Ticket getBySeanceAndPlace(long seanceId, long placeId) {
        try {
            Ticket ticket = jdbcTemplate.queryForObject(FIND_TICKET_BY_SEANCE_AND_PLACE, new TicketMapper(), seanceId, placeId);
            LOGGER.info("Found ticket for seanceId " + seanceId + " and placeId " + placeId);
            return ticket;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no ticket for seanceId " + seanceId + " and placeId " + placeId);
            throw new CinemaEmptyResultException("There are no ticket for seanceId " + seanceId + " and placeId " + placeId, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<Ticket> getBySeance(long seanceId) {
        try {
            List<Ticket> tickets = jdbcTemplate.query(FIND_TICKETS_BY_SEANCE, new TicketMapper(), seanceId);
            LOGGER.info("Found " + tickets.size() + " ticket objects for seanceId " + seanceId);
            return tickets;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public Ticket get(long ticketId) {
        try {
            Ticket ticket = jdbcTemplate.queryForObject(FIND_TICKET_BY_ID, new TicketMapper(), ticketId);
            LOGGER.info("Found not null ticket for ticketId " + ticketId);
            return ticket;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("There are no ticket for ticketId " + ticketId);
            throw new CinemaEmptyResultException("There are no ticket for ticketId " + ticketId, e);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long ticketId) {
        try {
            int affected = jdbcTemplate.update(DELETE_TICKET, ticketId);
            LOGGER.info("Ticket with id " + ticketId + " was deleted, affected " + affected + " rows");
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteBlockForOneHour(long seanceId) {
        try {
            int affected = jdbcTemplate.update(DELETE_BLOCK_FOR_ONE_HOUR, seanceId);
            LOGGER.info(affected + " tickets have been removed for 60 minutes before the start of the seance");
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Ticket ticket) {
        if (ticket == null) {
            LOGGER.error("Attempt to save null ticket");
            throw new IllegalArgumentException("Can't save null ticket");
        }
        try {
            long ticketId = jdbcTemplate.queryForObject(SAVE_TICKET, long.class, ticket.getId(), ticket.getCode(),
                    ticket.getEmail(), ticket.getPrice(), String.valueOf(ticket.isPaid()).toUpperCase(), ticket.getSeanceId(),
                    ticket.getPlaceId());
            ticket.setId(ticketId);
            LOGGER.info("Ticket was saved, now ticket has id " + ticketId);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public int soldTickets(long objId, Date startDate, Date endDate) {
        if (startDate == null) {
            LOGGER.error("startDate can't be null");
            throw new IllegalArgumentException("startDate can't be null");
        }
        if (endDate == null) {
            LOGGER.error("endDate can't be null");
            throw new IllegalArgumentException("endDate can't be null");
        }
        if (!startDate.before(endDate)) {
            LOGGER.error("startDate must be before endDate");
            throw new IllegalArgumentException("startDate must be before endDate");
        }
        try {
            int count = jdbcTemplate.queryForObject(
                    SOLD_TICKETS, int.class, objId, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
            LOGGER.info("For id " + objId + " sold " + count + " tickets");
            return count;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
        }
    }

    @Override
    public long getCode() {
        try {
            long code = jdbcTemplate.queryForObject(FIND_CODE_TICKET, Long.class);
            LOGGER.info("Return " + code + " code for tickets");
            return code;
        } catch (DataAccessException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaDaoException(e.getMessage(), e);
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
