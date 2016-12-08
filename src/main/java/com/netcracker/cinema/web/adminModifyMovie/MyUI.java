package com.netcracker.cinema.web.adminModifyMovie;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.web.admin.AdminMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SpringUI(path = "/adminModifyMovieUI")
@Theme("valo")
public class MyUI extends UI {

    @Autowired
    private MovieService movieService;
    private Grid grid = new Grid();
    private TextField filterText = new TextField();
    @Autowired
    private MovieForm movieForm;
    List<Movie> movie = null;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        // menu
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setWidth("100%");

        // filter and clear
        filterText.setInputPrompt("filter by movie...");
        filterText.setWidth("900");
        filterText.addTextChangeListener(e -> {
            grid.setContainerDataSource(new BeanItemContainer<>(Movie.class, search(e.getText())));
        });

        // clear text field
        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterText.clear();
            updateList();
        });

        // concat elements
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


        // show window with form, using vertical layout
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);

        Window subWindow = new Window("Sub-window");
        subWindow.setContent(subContent);
        subWindow.center();
        subWindow.setHeight("550");
        subWindow.setWidth("700");

        // add form to panel for form's resizing
        Panel panel = new Panel();
        panel.setContent(movieForm);

        // add panel to vertical layout
        subContent.addComponent(panel);

        // Button "Add new movie"
        Button addMovieBtn = new Button("Add new movie");
        addMovieBtn.addClickListener(e -> {
            movieForm.setMovie(new Movie(), subWindow, this);
            UI.getCurrent().addWindow(subWindow);
        });

        // Button "Edit movie"
        Button editMovieBtn = new Button("Edit");
        editMovieBtn.addClickListener(e -> {
            if(!grid.getSelectionModel().getSelectedRows().isEmpty()) {
                Long temp = ((Movie) grid.getSelectedRow()).getId();
                Movie movie = movieService.getById(temp);
                movieForm.setMovie(movie, subWindow, this);
                UI.getCurrent().addWindow(subWindow);
            } else {
                UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog(this, "Select the movie"));
            }
        });

        // Button "Delete movie"
        Button deleteMovieBtn = new Button("Delete");
        deleteMovieBtn.addClickListener(e -> {
            if(!grid.getSelectionModel().getSelectedRows().isEmpty()) {
                Movie movie = (Movie) grid.getSelectionModel().getSelectedRows().iterator().next();
                movieService.delete(movie);
                this.updateList();
                grid.setContainerDataSource(new BeanItemContainer<>(Movie.class, search(filterText.getValue())));
            } else {
                UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog(this, "Select the movie"));
            }
        });

        // horizontal
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addMovieBtn, editMovieBtn, deleteMovieBtn);
        toolbar.setSpacing(true);

        grid.setColumns("name", "duration", "imdb", "basePrice", "periodicity", "startDate", "endDate");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        layout.addComponents(toolbar, grid);

        updateList();

        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
    }

    public void updateList() {
        movie = movieService.findAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Movie.class, movie));
    }

    public List<Movie> search(String stringFilter) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        for (Movie contact : movie) {

            boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                    || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
            if (passesFilter) {
                arrayList.add(contact);
            }
        }
        Collections.sort(arrayList, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    @WebServlet(urlPatterns = "/VAADIN/adminModifyMovieUI/*", name = "adminModifyMovieUiUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends SpringVaadinServlet {
    }
}
