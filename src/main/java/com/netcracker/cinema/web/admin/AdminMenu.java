package com.netcracker.cinema.web.admin;

import com.netcracker.cinema.web.admin.price.AdminPriceView;
import com.netcracker.cinema.web.admin.seance.AdminSeanceView;
import com.netcracker.cinema.web.admin.movie.ModifyAdminMovieView;
import com.vaadin.ui.MenuBar;

public class AdminMenu extends MenuBar {

    public AdminMenu() {
        Command command = selectedItem -> {
            if(selectedItem.getText().equals("Movies")) {
                getUI().getNavigator().navigateTo(ModifyAdminMovieView.VIEW_NAME);
            }
            if(selectedItem.getText().equals("Seances")) {
                getUI().getNavigator().navigateTo(AdminSeanceView.VIEW_NAME);
            }
            if(selectedItem.getText().equals("Prices")) {
                getUI().getNavigator().navigateTo(AdminPriceView.VIEW_NAME);
            }
        };

        addItem("Movies", command);
        addItem("Seances", command);
        addItem("Prices", command);
    }
}