package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Ticket;

import java.sql.Date;
import java.util.List;

/**
 * Created by Titarenko on 01.12.2016.
 */
public interface TicketDao {

    List<Ticket> findAll();

    List<Ticket> getTicketsByCode(long code);

    Ticket getBySeanceAndPlace(long seanceId, long placeId);

    List<Ticket> getBySeance(long seanceId);

    Ticket get(long ticketId);

    void delete(long ticketId);

    long getCode();

    void deleteBlockForOneHour(long seanceId);

    long save(Ticket ticket);

    int soldTickets(long objId, Date startDate, Date endDate);
}
