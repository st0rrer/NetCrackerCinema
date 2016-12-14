package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = ScheduleView.VIEW_NAME, ui = UserUI.class)
public class ScheduleView extends GridLayout implements View {

	@Autowired
	private SeanceService seanceService;

	@Autowired
	private MovieService movieService;

	public static final String VIEW_NAME = "seance";
	private final int GRID_COLUMNS = 4;

	@PostConstruct
	void initFilters() {
		ScheduleFilterComponent SeanceFilter = new ScheduleFilterComponent();
		addComponent(SeanceFilter);
	}

	@PostConstruct
	void init() {
		List<Seance> seances = seanceService.findAll();
		setGridSize(seances);
		for (Seance seance : seances) {
			ScheduleComponent seanceComponent = new ScheduleComponent(seance, movieService);
			addComponent(seanceComponent);
		}
	}

	private void setGridSize(List<Seance> seances) {
		int rows = seances.size() % GRID_COLUMNS + 1;
		setColumns(GRID_COLUMNS);
		setRows(rows);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
	}
}
