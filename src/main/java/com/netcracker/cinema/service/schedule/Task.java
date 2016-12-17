package com.netcracker.cinema.service.schedule;


import com.netcracker.cinema.service.TicketService;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by User on 15.12.2016.
 */
@SpringComponent
public class Task {

    @Autowired
    private TicketService ticketService;

    public void removeBlockForOneHour() {
        ticketService.deleteBlockForOneHour();
    }


}
