package com.netcracker.cinema.service.schedule;

import java.util.Date;

public interface ScheduleService {

    void createTask(Date seanceDate, Long seanceId);

    void deleteTask(long seanceId);

    void shutdownSchedule();

}
