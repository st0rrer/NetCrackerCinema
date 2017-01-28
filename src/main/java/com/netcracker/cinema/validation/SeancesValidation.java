package com.netcracker.cinema.validation;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.service.TicketService;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.netcracker.cinema.web.admin.seance.SeanceTimeSettings.*;

/**
 * Created by Titarenko on 26.01.2017.
 */
@Service
public class SeancesValidation extends AbstractValidator {
    @Autowired
    private SeanceService seanceService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MovieService movieService;

    @Override
    public boolean validate() {
        return false;
    }

    public boolean editableSeance(long seanceId) {
        List<Ticket> list = ticketService.getBySeance(seanceId);
        if (!list.isEmpty()) {
            setCaption(Caption.booked_tickets);
            setMessage("Few seconds ago someone has booked tickets"
                    + LineSeparator.Windows + "for this seance, so it can't be modified.");
        }
        return list.isEmpty();
    }

    public boolean checkPrice(String price) {
        try {
            Integer.parseInt(price);
            return true;
        } catch (NumberFormatException ex) {
            setCaption(Caption.invalid_price);
            setMessage("Only numbers!");
            return false;
        }
    }

    public boolean checkSeanceDate(Seance newSeance) {
        Date minDate = new Date(System.currentTimeMillis() + MIN_TIME_TO_START_SEANCE * ONE_MINUTE);
        if (newSeance.getSeanceDate().before(minDate)) {
            setCaption(Caption.invalid_date);
            setMessage("Must be at least " + MIN_TIME_TO_START_SEANCE
                    + " minutes before the beginning of the seance");
            return false;
        }

        if (!IsBetweenMovieRolledDates(newSeance)) {
            setCaption(Caption.invalid_date);
            setMessage("Date of seance should be between" + LineSeparator.Windows +
                    "start and final dates of selected movie.");
            return false;
        }

        if (!IsInWorkingTime(newSeance)) {
            String start = String.valueOf(START_TIME_OF_WORKING_DAY);
            start = start.substring(0, 2) + ":" + start.substring(2);
            String last = String.valueOf(LAST_START_TIME_OF_SEANCE);
            last = last.substring(0, 2) + ":" + last.substring(2);

            setCaption(Caption.invalid_date);
            setMessage("Start time of seance should be" +
                    LineSeparator.Windows + "between " + start + " and " + last + " o'clock.");
            return false;
        }

        if (!IsHallFree(newSeance)) {
            setCaption(Caption.invalid_date);
            setMessage("There is other movie in this hall in the same time.");
            return false;
        }

        return true;
    }

    public boolean checkPeriods(Date startDate, Date endDate) {
        if (endDate.getTime() - startDate.getTime() < 0) {
            setCaption(Caption.invalid_date);
            setMessage("Start date of period should be" +
                    LineSeparator.Windows + "before end date of period.");
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
        int time = Integer.parseInt(timeFormat.format(date));
        return time >= START_TIME_OF_WORKING_DAY && time <= LAST_START_TIME_OF_SEANCE;
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