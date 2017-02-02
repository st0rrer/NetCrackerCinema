package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.TicketDao;
import com.netcracker.cinema.exception.CinemaEmptyResultException;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        try {
            Ticket ticket = ticketDao.getBySeanceAndPlace(seanceId, placeId);
            return true;
        } catch (CinemaEmptyResultException e) {
            return false;
        }
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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Ticket ticket) {
        ticketDao.save(ticket);
    }

    @Override
    public long getCode() {
        return ticketDao.getCode();
    }

    @Override
    public int soldTickets(long objId, Date startDate, Date endDate) {
        return ticketDao.soldTickets(objId, startDate, endDate);
    }

    @Override
    public void deleteBlockForOneHour(long seanceId) {
        ticketDao.deleteBlockForOneHour(seanceId);
    }
}