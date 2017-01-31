package com.netcracker.cinema.web.admin;

import com.netcracker.cinema.web.admin.movie.ModifyAdminMovieView;
import com.netcracker.cinema.web.admin.seance.AdminSeanceView;
import com.vaadin.server.Page;
import com.vaadin.ui.MenuBar;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminMenu extends MenuBar {

    public AdminMenu() {
        Command command = selectedItem -> {
            if(selectedItem.getText().equals("Movies")) {
                getUI().getNavigator().navigateTo(ModifyAdminMovieView.VIEW_NAME);
            }
            if(selectedItem.getText().equals("Seances")) {
                getUI().getNavigator().navigateTo(AdminSeanceView.VIEW_NAME);
            }
            if(selectedItem.getText().equals("Logout")){
                     SecurityContextHolder.clearContext();
                getUI().getSession().close();
                Page.getCurrent().open("login", null);
            }
        };
        addItem("Movies", command);
        addItem("Seances", command);
        addItem("Ratings", command);
        MenuItem logout = addItem("Logout", command);
        logout.setStyleName("logout-button");
    }
}