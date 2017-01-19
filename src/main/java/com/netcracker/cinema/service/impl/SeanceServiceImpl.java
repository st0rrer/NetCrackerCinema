package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.SeanceDao;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SeanceServiceImpl implements SeanceService {
    private final long START_TIME_OF_WORKING_DAY = 10_00;
    private final long LAST_START_TIME_OF_SEANCE = 22_00;
    private final long TWENTY_MINUTES_FOR_CLEANING = 1_200_000;

    private SeanceDao seanceDao;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MovieService movieService;

    @Autowired
    public SeanceServiceImpl(SeanceDao seanceDao) {
        this.seanceDao = seanceDao;
    }

    @Override
    public List<Seance> findAll() {
        return seanceDao.findAll();
    }

    @Override
    public List<Seance> findAll(SeanceFilter filter) {
        return seanceDao.findAll(filter);
    }

    @Override
    public Seance getById(long id) {
        return seanceDao.getById(id);
    }

    @Override
    public List<Seance> getByHallAndDate(long id, Date date) {
        return seanceDao.getByHallAndDate(id, new java.sql.Date(date.getTime()));
    }

    @Override
    public void delete(Seance seance) {
        seanceDao.delete(seance);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Seance seance) {
        seanceDao.save(seance);
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize) {
        return seanceDao.getPaginator(pageSize);
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize, SeanceFilter filter) {
        return seanceDao.getPaginator(pageSize, filter);
    }

    @Override
    public boolean editableSeance(long seanceId) {
        List<Ticket> list = ticketService.getBySeance(seanceId);
        return list.size() == 0;
    }

    @Override
    public boolean checkDate(Seance seance) {
        Movie movie = movieService.getById(seance.getMovieId());
        return (seance.getSeanceDate().after(movie.getStartDate()) &&
                seance.getSeanceDate().before(movie.getEndDate()));
    }

    @Override
    public boolean checkIfInWorkingTime(Seance seance) {
        DateFormat timeFormat = new SimpleDateFormat("HHmm");
        int time = Integer.parseInt(timeFormat.format(seance.getSeanceDate()));
        return time >= START_TIME_OF_WORKING_DAY && time <= LAST_START_TIME_OF_SEANCE;
    }

    @Override
    public boolean checkIfHallIsFree(Seance newSeance) {
        List<Seance> seanceList = getByHallAndDate(newSeance.getHallId(), newSeance.getSeanceDate());
        if (seanceList.size() != 0) {
            for (Seance existingSeance : seanceList) {
                if (newSeance.getId() != existingSeance.getId()) {
                    Movie movieOfNewSeance = movieService.getById(newSeance.getMovieId());
                    Movie movieOfExistingSeance = movieService.getById(existingSeance.getMovieId());

                    Date startDateOfNewSeance = newSeance.getSeanceDate();
                    Date endDateOfNewSeance = new java.util.Date(startDateOfNewSeance.getTime() +
                            movieOfNewSeance.getDuration() * 60_000 + TWENTY_MINUTES_FOR_CLEANING);
                    Date startDateOfExistingSeance = existingSeance.getSeanceDate();
                    Date endDateOfExistingSeance = new java.util.Date(startDateOfExistingSeance.getTime() +
                            movieOfExistingSeance.getDuration() * 60_000 + TWENTY_MINUTES_FOR_CLEANING);

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

    @Override
    public long getCountActiveMoviesById(long movieId) {
        return seanceDao.getCountActiveMoviesById(movieId);
    }
}