package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.TicketDao;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Titarenko on 10.12.2016.
 */
public class TicketServiceImpl implements TicketService {
    private TicketDao ticketDao;

    @Autowired
    TicketServiceImpl(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    @Override
    public List<Ticket> getTicketsByCode(long code) {
        return ticketDao.getTicketsByCode(code);
    }

    @Override
    public boolean isAlreadyBookedTicket(long seanceId, long placeId) {
        return ticketDao.getBySeanceAndPlace(seanceId, placeId) != null;
    }

    @Override
    public List<Ticket> getBySeance(long seanceId) {
        return ticketDao.getBySeance(seanceId);
    }

    @Override
    public Ticket getById(long ticketId) {
        return ticketDao.get(ticketId);
    }

    @Override
    public void deleteById(long ticketId) {
        ticketDao.delete(ticketId);
    }

    @Override
    public long save(Ticket ticket) {
        return ticketDao.save(ticket);
    }

    @Override
    public int soldTickets(long objId, Date startDate, Date endDate) {
        return ticketDao.soldTickets(objId,
                new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
    }

    @Override
    public void deleteBlockForOneHour() {
        ticketDao.deleteBlockForOneHour();
    }
}