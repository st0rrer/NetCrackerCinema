package com.netcracker.cinema.validation;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.service.TicketService;
import com.netcracker.cinema.validation.routines.IntegerValidator;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.netcracker.cinema.validation.MessagesAndCaptions.*;
import static com.netcracker.cinema.web.admin.seance.SeanceTimeSettings.*;

@Service
public class SeancesValidation implements Validator {
    private String caption;
    private String message;

    @Autowired
    private SeanceService seanceService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MovieService movieService;

    public String getCaption() {
        return caption;
    }

    private void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        return false;
    }

    public boolean editableSeance(long seanceId) {
        List<Ticket> list = ticketService.getBySeance(seanceId);
        if (!list.isEmpty()) {
            setCaption(BOOKED_TICKETS_CAPTION);
            setMessage(BOOKED_TICKETS_MESSAGE);
        }
        return list.isEmpty();
    }

    public boolean checkPrice(String price) {
        Validator intValidator = new IntegerValidator(price);
        if (!intValidator.validate()) {
            setCaption(INVALID_PRICE_CAPTION);
            setMessage(intValidator.getMessage());
            return false;
        }
        return true;
    }

    public boolean checkSeanceDate(Seance newSeance) {
        Date minDate = new Date(System.currentTimeMillis() + MIN_TIME_TO_START_SEANCE * ONE_MINUTE);
        if (newSeance.getSeanceDate().before(minDate)) {
            setCaption(INVALID_DATE);
            setMessage("Must be at least " + MIN_TIME_TO_START_SEANCE
                    + " minutes before the beginning of the seance");
            return false;
        }
        if (!IsBetweenMovieRolledDates(newSeance)) {
            setCaption(INVALID_DATE);
            setMessage(ROLLED_DATES);
            return false;
        }
        if (!IsInWorkingTime(newSeance)) {
            long startHours = START_TIME_OF_WORKING_DAY / 100;
            long startMins = START_TIME_OF_WORKING_DAY % 100;
            String start = startHours + ":" + (startMins > 0 ? startMins : "00");

            long lastHours = LAST_START_TIME_OF_SEANCE / 100;
            long lastMins = LAST_START_TIME_OF_SEANCE % 100;
            String last = lastHours + ":" + (lastMins > 0 ? lastMins : "00");

            setCaption(INVALID_DATE);
            setMessage("Start time of seance should be" +
                    LineSeparator.Windows + "between " + start + " and " + last + " o'clock.");
            return false;
        }
        if (!IsHallFree(newSeance)) {
            setCaption(INVALID_DATE);
            setMessage(MOVIES_CONFLICT);
            return false;
        }
        return true;
    }

    public boolean checkPeriods(Date startDate, Date endDate) {
        if (endDate.getTime() - startDate.getTime() < 0) {
            setCaption(INVALID_DATE);
            setMessage(PERIOD_DATES);
            return false;
        }
        return true;
    }

    private boolean IsBetweenMovieRolledDates(Seance seance) {
        Movie movie = movieService.getById(seance.getMovieId());
        return (seance.getSeanceDate().after(movie.getStartDate()) &&
                seance.getSeanceDate().before(movie.getEndDate()));
    }

    private boolean IsInWorkingTime(Seance seance) {
        return IsInWorkingTime(seance.getSeanceDate());
    }

    public boolean IsInWorkingTime(Date date) {
        DateFormat timeFormat = new SimpleDateFormat("HHmm");
        long time = Integer.parseInt(timeFormat.format(date));
        long lastTime;
        if (START_TIME_OF_WORKING_DAY >= LAST_START_TIME_OF_SEANCE) {
            lastTime = LAST_START_TIME_OF_SEANCE + 2400;
            if (time <= LAST_START_TIME_OF_SEANCE) {
                time += 2400;
            }
        } else {
            lastTime = LAST_START_TIME_OF_SEANCE;
        }
        return time >= START_TIME_OF_WORKING_DAY && time <= lastTime;
    }

    private boolean IsHallFree(Seance newSeance) {
        List<Seance> seanceList = seanceService.getByHallAndDate(newSeance.getHallId(), newSeance.getSeanceDate());
        if (seanceList.size() != 0) {
            for (Seance existingSeance : seanceList) {
                if (newSeance.getId() != existingSeance.getId()) {
                    Movie movieOfNewSeance = movieService.getById(newSeance.getMovieId());
                    Movie movieOfExistingSeance = movieService.getById(existingSeance.getMovieId());

                    Date startDateOfNewSeance = newSeance.getSeanceDate();
                    Date endDateOfNewSeance = new java.util.Date(startDateOfNewSeance.getTime() +
                            (movieOfNewSeance.getDuration() + TIME_FOR_CLEANING) * ONE_MINUTE);
                    Date startDateOfExistingSeance = existingSeance.getSeanceDate();
                    Date endDateOfExistingSeance = new java.util.Date(startDateOfExistingSeance.getTime() +
                            (movieOfExistingSeance.getDuration() + TIME_FOR_CLEANING) * ONE_MINUTE);

                    if (startDateOfNewSeance.compareTo(startDateOfExistingSeance) == 0 ||
                            endDateOfNewSeance.compareTo(endDateOfExistingSeance) == 0 ||
                            startDateOfNewSeance.after(startDateOfExistingSeance) && startDateOfNewSeance.before(endDateOfExistingSeance) ||
                            endDateOfNewSeance.after(startDateOfExistingSeance) && endDateOfNewSeance.before(endDateOfExistingSeance) ||
                            startDateOfNewSeance.before(startDateOfExistingSeance) && endDateOfNewSeance.after(endDateOfExistingSeance)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}