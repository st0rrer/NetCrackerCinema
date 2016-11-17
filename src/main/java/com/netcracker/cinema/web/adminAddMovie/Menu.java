package com.netcracker.cinema.web.adminAddMovie;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;

/**
 * Created by Илья on 17.11.2016.
 */
public class Menu extends HorizontalLayout {
    public Menu() {
        MenuBar barmenu = new MenuBar();
        addComponent(barmenu);
        MenuBar.MenuItem movies = barmenu.addItem("Movies", null, null);
        MenuBar.MenuItem schedule = barmenu.addItem("Schedule", null, null);
        MenuBar.MenuItem statistics = barmenu.addItem("Statistics", null, null);
        barmenu.setHeight("30px");
    }
}
