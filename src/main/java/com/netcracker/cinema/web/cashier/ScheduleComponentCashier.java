package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.web.common.ScheduleComponent;
import com.netcracker.cinema.web.user.HallDetailsViewUser;

/**
 * Created by dimka on 25.12.2016.
 */
public class ScheduleComponentCashier extends ScheduleComponent {

    public ScheduleComponentCashier(Seance seances, Movie movie) {
        super(seances, movie);
        addLayoutClickListener(event -> getUI().getNavigator().navigateTo(HallDetailsViewCashier.VIEW_NAME + "/" + seances.getId()));
    }
}
