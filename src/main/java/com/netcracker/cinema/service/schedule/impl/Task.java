package com.netcracker.cinema.service.schedule.impl;


import com.netcracker.cinema.service.ApplicationContextHandler;
import com.netcracker.cinema.service.TicketService;
import com.netcracker.cinema.web.CinemaConfiguration;
import com.vaadin.spring.annotation.SpringComponent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by User on 15.12.2016.
 */
@SpringComponent
public class Task implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        TicketService ticketService =(TicketService) jobExecutionContext.getMergedJobDataMap().get("ticketService");
        ApplicationContext ctx = ApplicationContextHandler.getApplicationContext();
        TicketService ticketService = (TicketService) ctx.getBean("ticketService");
        long seanceId = (long)jobExecutionContext.getMergedJobDataMap().get("id");
        ticketService.deleteBlockForOneHour(seanceId);
    }
}

