package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Ticket;

import java.sql.Date;
import java.util.List;

/**
 * Created by Titarenko on 10.12.2016.
 */
public interface TicketService {

    List<Ticket> findAll();

    /**
     * @param id - id of seance or place
     * @return list of tickets which have references
     *    with seance or place in table objreference
     */
    List<Ticket> getBySeanceOrPlace(long id);

    Ticket getById(long id);

    void delete(Ticket ticket);

    void deleteById(long ticketId);

    /**
     * @return id of new or updated ticket
     */
    long save(Ticket ticket);

    /**
     * @param objId - OBJECT_ID from table OBJECTS for Zone, Hall or Movie
     * @return quantity of sold tickets or '-1' if 'objName' was not found
     */
    int soldTickets(long objId, Date startDate, Date endDate);

}
