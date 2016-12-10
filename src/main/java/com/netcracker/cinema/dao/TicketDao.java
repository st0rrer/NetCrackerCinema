package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Ticket;

import java.sql.Date;
import java.util.List;

/**
 * Created by Titarenko on 01.12.2016.
 */
public interface TicketDao {

    List<Ticket> findAll();

    /**
     * @param id - id of seance or place
     * @return list of tickets which have references
     *      with seance or place in table objreference
     */
    List<Ticket> getBySeanceOrPlace(long id);

    Ticket getById(long id);

    void deleteById(long ticketId);

    void delete(Ticket ticket);

    void save(Ticket ticket);

    /**
     * @param objName - NAME from table OBJECTS
     * @return quantity of sold tickets or '-1' if 'objName' was not found
     * @see db/function_sold_tickets.sql
     */
    Integer soldTickets(String objName, Date startDate, Date endDate);
}
