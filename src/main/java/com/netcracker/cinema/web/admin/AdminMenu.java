package com.netcracker.cinema.web.admin;


import com.netcracker.cinema.web.admin.addMovie.AdminAddMovieView;
import com.vaadin.ui.MenuBar;

public class AdminMenu extends MenuBar {

    public AdminMenu() {
        Command command = selectedItem -> {
            if(selectedItem.getText().equals("Edit Movie")) {
              //  getUI().getNavigator().navigateTo(AdminModifyMovie.VIEW_NAME);
            }

            if(selectedItem.getText().equals("Add Movie")) {
                getUI().getNavigator().navigateTo(AdminAddMovieView.VIEW_NAME);

            }

        };
        addItem("Edit Movie", command);
        addItem("Add Movie", command);

    }
}
