package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.web.common.ScheduleComponent;

public class ScheduleComponentUser extends ScheduleComponent {
    public ScheduleComponentUser(Seance seances, Movie movie) {
        super(seances, movie);
        addLayoutClickListener(event -> getUI().getNavigator().navigateTo(HallDetailsViewUser.VIEW_NAME + "/" + seances.getId()));
    }
}
