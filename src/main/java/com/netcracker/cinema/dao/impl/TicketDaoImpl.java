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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
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
        List<Ticket> tickets = jdbcTemplate.query(FIND_ALL_TICKETS, new TicketMapper());
        LOGGER.info("There are " + tickets.size() + " tickets in DB");
        return tickets;
    }

    @Override
    public List<Ticket> getTicketsByCode(long code) {
        if (code > 0) {
            List<Ticket> tickets = jdbcTemplate.query(FIND_TICKETS_BY_CODE, new TicketMapper(), code);
            LOGGER.info("There are found " + tickets.size() + " tickets with code = " + code);
            return tickets;
        } else {
            return Collections.emptyList();
        }

    }

    @Override
    public Ticket getBySeanceAndPlace(long seanceId, long placeId) {
        Ticket ticket = null;
        try {
            ticket = jdbcTemplate.queryForObject(FIND_TICKET_BY_SEANCE_AND_PLACE, new TicketMapper(), seanceId, placeId);
        } catch (EmptyResultDataAccessException e) {
        } //Just this place for this seance are free
        return ticket;
    }

    @Override
    public List<Ticket> getBySeance(long seanceId) {
        return jdbcTemplate.query(FIND_TICKETS_BY_SEANCE, new TicketMapper(), seanceId);
    }

    @Override
    public Ticket get(long ticketId) {
        Ticket ticket = null;
        if (ticketId < 1) {
            LOGGER.info("Id should be > 0");
        } else {
            try {
                ticket = jdbcTemplate.queryForObject(FIND_TICKET_BY_ID, new TicketMapper(), ticketId);
            } catch (EmptyResultDataAccessException e) {
                LOGGER.error("Id is not exist for tickets", e);
            }
        }
        return ticket;
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
    public void deleteBlockForOneHour() {
        jdbcTemplate.update(DELETE_BLOCK_FOR_ONE_HOUR);
    }

    @Override
    public long save(Ticket ticket) {
        long ticketId;
        if (ticket == null) {
            LOGGER.info("Ticket == null");
            ticketId = 0;
        } else {
            Object[] arrayOfFields = new Object[]{ticket.getId(), ticket.getCode(),
                    ticket.getEmail(), ticket.getPrice(), (ticket.isPaid() + "").toUpperCase(),
                    ticket.getSeanceId(), ticket.getPlaceId()};
            LOGGER.info("Saving ticket: " + ticket);
            ticketId = jdbcTemplate.queryForObject(SAVE_TICKET, long.class, arrayOfFields);
        }
//        if (ticket.getCode() == 0) {
//            ticket.setCode(jdbcTemplate.queryForObject(FIND_CODE_TICKET, Long.class));
//        }
        return ticketId;
    }


    @Override
    public int soldTickets(long objId, Date startDate, Date endDate) {
        if ((objId < 1) || (startDate.after(endDate))) {
            LOGGER.info("Id should be > 0 and startDate before endDate");
            return -1;
        } else {
            int count = jdbcTemplate.queryForObject(
                    SOLD_TICKETS, int.class, objId, startDate, endDate);
            LOGGER.info("For id = " + objId + " sold " + count + " tickets");
            return count;
        }
    }

    @Override
    public long getCode() {
        long code;
        code = jdbcTemplate.queryForObject(FIND_CODE_TICKET, Long.class);
        return code;
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
