package com.netcracker.cinema.web.common;

import com.netcracker.cinema.model.Seance;
import com.vaadin.ui.GridLayout;

import java.util.List;

public class ScheduleTable extends GridLayout {

    private final int GRID_COLUMNS = 4;

    public ScheduleTable() {
        setSpacing(true);
        addStyleName("schedule-grid");
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
