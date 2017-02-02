package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.web.common.ScheduleComponent;
import com.netcracker.cinema.web.common.ScheduleTable;
import com.netcracker.cinema.web.user.ScheduleComponentUser;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class ScheduleTableCashier extends ScheduleTable {

    @Autowired
    private MovieService movieService;

    @Override
    public void updateGrid(List<Seance> seances) {
        super.updateGrid(seances);
        for (Seance seance : seances) {
            Movie movie = movieService.getById(seance.getMovieId());
            ScheduleComponent seanceComponent = new ScheduleComponentCashier(seance, movie);
            addComponent(seanceComponent);
        }
    }
}
