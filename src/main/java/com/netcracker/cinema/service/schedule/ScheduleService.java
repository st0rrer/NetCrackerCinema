package com.netcracker.cinema.service.schedule;

import org.quartz.SchedulerException;

import java.util.Date;

/**
 * Created by User on 04.01.2017.
 */
public interface ScheduleService {

    void createTask(Date seanceDate, Long seanceId);

    void deleteTask(long seanceId);

    void shutdownSchedule();

}
