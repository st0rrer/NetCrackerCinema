package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Ticket;

import java.util.Date;
import java.util.List;

public interface TicketService {

    List<Ticket> findAll();

    boolean isAlreadyBookedTicket(long seanceId, long placeId);

    List<Ticket> getBySeance(long seanceId);

    List<Ticket> getTicketsByCode(long code);

    Ticket getById(long id);

    void deleteById(long ticketId);

    void save(Ticket ticket);

    long getCode();

    int soldTickets(long objId, Date startDate, Date endDate);

    void deleteBlockForOneHour(long seanceId);
}