package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dimka on 17.12.2016.
 */
@SpringComponent
@ViewScope
public class ScheduleTable extends GridLayout {

    private final int GRID_COLUMNS = 4;

    @Autowired
    private MovieService movieService;

    ScheduleTable() {
        super();
    }

    public void updateGrid(List<Seance> seances) {
        this.removeAllComponents();
        setGridSize(seances);
        if(this.getComponentCount() != 0) {
            this.removeAllComponents();
        }
        for (Seance seance : seances) {
            Movie movie = movieService.getById(seance.getMovieId());
            ScheduleComponent seanceComponent = new ScheduleComponent(seance, movieService);
            addComponent(seanceComponent);
        }
    }

    private void setGridSize(List<Seance> seances) {
        int rows = seances.size() % GRID_COLUMNS + 1;
        setColumns(GRID_COLUMNS);
        setRows(rows);
    }

}
