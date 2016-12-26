package com.netcracker.cinema.web.common;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dimka on 17.12.2016.
 */
public class ScheduleTable extends GridLayout {

    private final int GRID_COLUMNS = 4;

    public ScheduleTable() {
        super();
    }

    public void updateGrid(List<Seance> seances) {
        this.removeAllComponents();
        setGridSize(seances);
        if(this.getComponentCount() != 0) {
            this.removeAllComponents();
        }
    }

    private void setGridSize(List<Seance> seances) {
        int rows = seances.size() % GRID_COLUMNS + 1;
        setColumns(GRID_COLUMNS);
        setRows(rows);
    }

}
