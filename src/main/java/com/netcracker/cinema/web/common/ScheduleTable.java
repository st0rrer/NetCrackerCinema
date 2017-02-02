package com.netcracker.cinema.web.common;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class ScheduleTable extends GridLayout {

    private final int GRID_COLUMNS = 4;

    @Autowired
    private MovieService movieService;

    public ScheduleTable() {
    }

    public void updateGrid(List<Seance> seances) {
        this.removeAllComponents();
        setGridSize(seances);
        if(this.getComponentCount() != 0) {
            this.removeAllComponents();
        }
        for (Seance seance : seances) {
            Movie movie = movieService.getById(seance.getMovieId());
            ScheduleComponent seanceComponent = new ScheduleComponent(seance, movie);
            addComponent(seanceComponent);
        }
    }

    private void setGridSize(List<Seance> seances) {
        int rows = seances.size() % GRID_COLUMNS + 1;
        setColumns(GRID_COLUMNS);
        setRows(rows);
    }

}
