package com.netcracker.cinema.service.schedule.impl;

import com.netcracker.cinema.service.TicketService;
import com.netcracker.cinema.service.schedule.ScheduleService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by User on 04.01.2017.
 */
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    TicketService ticketService;
    HashMap<Long, JobKey> hashMap = new HashMap<>();
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler scheduler = sf.getScheduler();

    public ScheduleServiceImpl() throws SchedulerException {}

    @Override
    public void createTask(Date seanceDate, Long seanceId) {
        Date date = new Date(seanceDate.getTime()-1000*60*60); // your date
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(java.util.Calendar.YEAR);
        int month = cal.get(java.util.Calendar.MONTH);
        int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
        int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = cal.get(java.util.Calendar.MINUTE);
        int seconds = cal.get(java.util.Calendar.SECOND);

        if(seanceId != 0) {
            deleteTask(seanceId);
        }

        //String cronTime = "50 8 16 13 1 ? 2017";
        String cronTime = seconds + " " + minute + " " + hour + " " + day + " " + (month+1) + " ? " + year;

        JobDetail job = newJob(Task.class)
                .withIdentity("job" + seanceId, "group")
                .build();
        job.getJobDataMap().put("ticketService", ticketService);
        job.getJobDataMap().put("id", seanceId);

        CronTrigger trigger = newTrigger()
                .withIdentity("trigger" + seanceId, "group")
                .withSchedule(cronSchedule(cronTime))
                .build();

        try {
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        hashMap.put(seanceId, job.getKey());
    }

    @Override
    public void deleteTask(long seanceId) {
        try {
            scheduler.deleteJob(hashMap.get(seanceId));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        hashMap.remove(seanceId);
    }

    @Override
    public void shutdownSchedule() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
