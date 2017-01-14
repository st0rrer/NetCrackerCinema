package com.netcracker.cinema.service.schedule.impl;


import com.netcracker.cinema.service.TicketService;
import com.vaadin.spring.annotation.SpringComponent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by User on 15.12.2016.
 */
@SpringComponent
public class Task implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        TicketService ticketService =(TicketService) jobExecutionContext.getMergedJobDataMap().get("ticketService");
        long seanceId = (long)jobExecutionContext.getMergedJobDataMap().get("id");
        ticketService.deleteBlockForOneHour(seanceId);
    }
}

