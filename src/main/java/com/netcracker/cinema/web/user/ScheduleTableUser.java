package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.web.common.ScheduleComponent;
import com.netcracker.cinema.web.common.ScheduleTable;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dimka on 25.12.2016.
 */
@SpringComponent
public class ScheduleTableUser extends ScheduleTable {

    @Autowired
    private MovieService movieService;

    @Override
    public void updateGrid(List<Seance> seances) {
        super.updateGrid(seances);
        for (Seance seance : seances) {
            Movie movie = movieService.getById(seance.getMovieId());
            ScheduleComponent seanceComponent = new ScheduleComponentUser(seance, movie);
            addComponent(seanceComponent);
        }
    }
}
