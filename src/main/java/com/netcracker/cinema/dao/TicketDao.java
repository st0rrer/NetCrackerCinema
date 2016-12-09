package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Ticket;

import java.sql.Date;
import java.util.List;

/**
 * Created by Titarenko on 01.12.2016.
 */
public interface TicketDao {

    List<Ticket> findAll();

    Ticket getById(long id);

    void deleteById(long ticketId);

    void delete(Ticket ticket);

    void save(Ticket ticket);

    Integer soldTickets(String objName, Date startDate, Date endDate);
}
