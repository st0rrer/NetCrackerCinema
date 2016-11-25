package com.netcracker.cinema.web.user;

import com.vaadin.ui.MenuBar;

public class UserMenu extends MenuBar {

    public UserMenu() {
        MenuBar.Command command = selectedItem -> {
            if(selectedItem.getText().equals("Movies")) {
                getUI().getNavigator().navigateTo(MoviesView.VIEW_NAME);
            }

            if(selectedItem.getText().equals("Schedule")) {
                getUI().getNavigator().navigateTo(ScheduleView.VIEW_NAME);
            }
        };

        addItem("Movies", command);
        addItem("Schedule", command);
    }
}
