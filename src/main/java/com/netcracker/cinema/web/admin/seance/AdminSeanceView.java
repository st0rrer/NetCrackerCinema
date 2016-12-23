package com.netcracker.cinema.web.admin.seance;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.AdminUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
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
    private List<Seance> seanceList1;
    private List<Seance> seanceList2;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    private FormLayout formLayout;

    @PostConstruct
    protected void init() {
//        setRows(30);
//        setColumns(30);
        seanceList1 = seanceService.getByHallAndDate(1, sqlDate);
        seanceList2 = seanceService.getByHallAndDate(2, sqlDate);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        VerticalLayout verticalLayout1 = new VerticalLayout();
        VerticalLayout verticalLayout2 = new VerticalLayout();
        VerticalLayout verticalLayout3 = new VerticalLayout();

        InlineDateField calendar = new InlineDateField();
        calendar.setShowISOWeekNumbers(true);
        calendar.setValue(date);
        verticalLayout1.addComponent(calendar);

        Label hallLabel1 = new Label("Hall 1");
        hallLabel1.setStyleName(ValoTheme.LABEL_H2);
        verticalLayout2.addComponent(hallLabel1);

        Label dateLabel1 = new Label(dateFormat.format(date));
        verticalLayout2.addComponent(dateLabel1);

        for (Seance seance : seanceList1) {
            HorizontalLayout horizontalLayout = getSeanceRow(seance);
            verticalLayout2.addComponent(horizontalLayout);
        }

        Label hallLabel2 = new Label("Hall 2");
        hallLabel2.setStyleName(ValoTheme.LABEL_H2);
        verticalLayout3.addComponent(hallLabel2);

        Label dateLabel2 = new Label(dateFormat.format(date));
        verticalLayout3.addComponent(dateLabel2);

        for (Seance seance : seanceList2) {
            HorizontalLayout horizontalLayout = getSeanceRow(seance);
            verticalLayout3.addComponent(horizontalLayout);
        }

        Button button = new Button("Show");
        button.addClickListener(e -> {
            sqlDate = new java.sql.Date(calendar.getValue().getTime());
            dateLabel1.setValue(dateFormat.format(date));

            seanceList1 = seanceService.getByHallAndDate(1, sqlDate);
            verticalLayout2.removeAllComponents();
            verticalLayout2.addComponent(hallLabel1);
            verticalLayout2.addComponent(dateLabel1);
            for (Seance seance : seanceList1) {
                HorizontalLayout horizontalLayout = getSeanceRow(seance);
                verticalLayout2.addComponent(horizontalLayout);
            }

            seanceList2 = seanceService.getByHallAndDate(2, sqlDate);
            verticalLayout3.removeAllComponents();
            verticalLayout3.addComponent(hallLabel2);
            verticalLayout3.addComponent(dateLabel2);
            for (Seance seance : seanceList2) {
                HorizontalLayout horizontalLayout = getSeanceRow(seance);
                verticalLayout3.addComponent(horizontalLayout);
            }
        });
        button.setWidth("260px");
        verticalLayout1.addComponent(button);

        verticalLayout1.setSpacing(true);
        verticalLayout2.setSpacing(true);
        verticalLayout3.setSpacing(true);
        horizontalLayout1.setSpacing(true);

        horizontalLayout1.addComponent(verticalLayout1);
        horizontalLayout1.addComponent(verticalLayout2);
        horizontalLayout1.addComponent(verticalLayout3);
        addComponent(horizontalLayout1);
        setSizeFull();
    }

    private HorizontalLayout getSeanceRow(Seance seance) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Label time = new Label(timeFormat.format(seance.getSeanceDate()));
        Button edit = new Button("Edit");
        Button remove = new Button("Remove");
        horizontalLayout.setSpacing(true);

        Movie movie = movieService.getById(seance.getMovieId());

        horizontalLayout.addComponent(createPoster(movie));
        horizontalLayout.addComponent(time);
        horizontalLayout.addComponent(edit);
        horizontalLayout.addComponent(remove);
        return horizontalLayout;
    }

    private Component createPoster(Movie movie) {
        ExternalResource res = new ExternalResource(movie.getPoster());
        Image poster = new Image(null, res);
//        poster.setWidth("70px");
        poster.setHeight("100px");
        return poster;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}