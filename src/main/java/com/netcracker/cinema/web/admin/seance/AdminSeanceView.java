package com.netcracker.cinema.web.admin.seance;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.AdminUI;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.sass.internal.selector.Selector;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@SpringView(name = AdminSeanceView.VIEW_NAME, ui = AdminUI.class)
public class AdminSeanceView extends GridLayout implements View {
    public static final String VIEW_NAME = "seances";

    private Date date = new Date(System.currentTimeMillis());
    private java.sql.Date sqlDate = new java.sql.Date(date.getTime());

    @Autowired
    private SeanceService seanceService;
    @Autowired
    private MovieService movieService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private FormLayout formLayout;
    private VerticalLayout verticalLayout1 = new VerticalLayout();
    private VerticalLayout verticalLayout2 = new VerticalLayout();
    private VerticalLayout verticalLayout3 = new VerticalLayout();

    @PostConstruct
    protected void init() {
//        setRows(4);
        setColumns(4);

        InlineDateField calendar = new InlineDateField();
        calendar.setShowISOWeekNumbers(true);
        calendar.setValue(date);

        Button button = new Button("Show seances");
        button.addClickListener(e -> {
            date = calendar.getValue();
            sqlDate = new java.sql.Date(date.getTime());
            getHall(1, verticalLayout2);
            getHall(2, verticalLayout3);
        });
        button.setWidth("270px");

        Window subWindow = new Window("Add new seance");
        subWindow.setContent(getComponents());
        subWindow.center();
        subWindow.setHeight("380");
        subWindow.setWidth("600");
        subWindow.setResizable(false);

        final Button open = new Button("Add new seance");
        open.addClickListener(e -> UI.getCurrent().addWindow(subWindow));

        verticalLayout1.setSpacing(true);
        verticalLayout1.setMargin(true);
        verticalLayout1.addComponents(calendar, button, open);

        getHall(1, verticalLayout2);
        getHall(2, verticalLayout3);

        addComponents(verticalLayout1, verticalLayout2, verticalLayout3);
        setSizeFull();
    }

    private Layout getComponents() {
        List<Movie> movieList = movieService.findAll();

        VerticalLayout subContent2 = new VerticalLayout();

        VerticalLayout subContent = new VerticalLayout();
        subContent.setSpacing(true);
        subContent.setMargin(true);

        DateField seanceDate = new DateField("Select date:");
        seanceDate.setResolution(Resolution.MINUTE);
        seanceDate.setWidth("240px");

        ComboBox movieName = new ComboBox("Select movie");
        movieName.setFilteringMode(FilteringMode.CONTAINS);
        movieName.setInputPrompt("start type...");
        movieName.setWidth("240px");
        for (Movie movie : movieList) {
            movieName.addItem(movie.getName());
        }
        movieName.addBlurListener(e -> {
            subContent2.removeAllComponents();
            for (Movie movie : movieList) {
                if (movie.getName().equals(movieName.getValue())) {
                    subContent2.addComponent(createPoster(movie, "250px"));
                }
            }
        });

        NativeSelect hallId = new NativeSelect("Select hall");
        hallId.addItems(1, 2);

        Button save = new Button("Save seance");
        save.addClickListener(e -> {
            for (Movie movie : movieList) {
                if (movie.getName().equals(movieName.getValue())) {
                    Seance newSeance = new Seance();
                    newSeance.setSeanceDate(seanceDate.getValue());
                    newSeance.setMovieId(movie.getId());
                    newSeance.setHallId(Long.parseLong(hallId.getValue().toString()));
                    seanceService.save(newSeance);
                    break;
                }
            }
        });
        subContent.addComponents(seanceDate, movieName, hallId, save);

        HorizontalLayout horizontSub = new HorizontalLayout();
        horizontSub.addComponents(subContent, subContent2);

        return horizontSub;
    }

    private void getHall(long hallId, VerticalLayout layout) {
        List<Seance> seanceList = seanceService.getByHallAndDate(hallId, sqlDate);
        seanceList.sort((s1, s2) -> s1.getSeanceDate().compareTo(s2.getSeanceDate()));
        layout.removeAllComponents();
        layout.setSpacing(true);
        layout.setMargin(true);

        Label hallLabel = new Label("Hall " + hallId);
        hallLabel.setStyleName(ValoTheme.LABEL_H2);
        Label dateLabel = new Label(dateFormat.format(date));

        VerticalLayout verticalLayout = new VerticalLayout();
        for (Seance seance : seanceList) {
            HorizontalLayout horizontalLayout = getSeanceRow(seance);
            verticalLayout.addComponent(horizontalLayout);
        }

        Panel hallPanel = new Panel();
        hallPanel.setHeight("720px");
        hallPanel.setContent(verticalLayout);

        layout.addComponents(hallLabel, dateLabel, hallPanel);
    }

    private HorizontalLayout getSeanceRow(Seance seance) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        Movie movie = movieService.getById(seance.getMovieId());

        VerticalLayout movieInfo = new VerticalLayout();
        movieInfo.setWidth("170px");
        Label name = new Label(movie.getName());
        Label time = new Label(timeFormat.format(seance.getSeanceDate()));
        movieInfo.addComponents(name, time);

        Button edit = new Button("Edit");
        Button remove = new Button("Remove");

        horizontalLayout.addComponents(createPoster(movie, "80px"), movieInfo, edit, remove);
        return horizontalLayout;
    }

    private Component createPoster(Movie movie, String height) {
        ExternalResource res = new ExternalResource(movie.getPoster());
        Image poster = new Image(null, res);
        poster.setHeight(height);
        return poster;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}