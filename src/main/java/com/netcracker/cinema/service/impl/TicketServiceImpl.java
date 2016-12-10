package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.TicketDao;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
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
    public List<Ticket> getBySeanceOrPlace(long id) {
        return ticketDao.getBySeanceOrPlace(id);
    }

    @Override
    public Ticket getById(long ticketId) {
        return ticketDao.get(ticketId);
    }

    @Override
    public void delete(Ticket ticket) {
        deleteById(ticket.getId());
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
        return ticketDao.soldTickets(objId, startDate, endDate);
    }
}
