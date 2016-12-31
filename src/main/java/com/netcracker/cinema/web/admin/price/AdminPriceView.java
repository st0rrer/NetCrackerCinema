package com.netcracker.cinema.web.admin.price;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.AdminUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Titarenko on 31.12.2016.
 */
@SpringView(name = AdminPriceView.VIEW_NAME, ui = AdminUI.class)
public class AdminPriceView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "price";

    @Autowired
    private SeanceService seanceService;
    private Seance seance;
    @Autowired
    private MovieService movieService;
    private List<Movie> movieList;

    private Date date = new Date(System.currentTimeMillis());
    private java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private VerticalLayout verticalLayout1 = new VerticalLayout();
    private VerticalLayout verticalLayout2 = new VerticalLayout();

    public AdminPriceView() {
    }

    @PostConstruct
    protected void init() {
        setSizeFull();

        InlineDateField calendar = new InlineDateField();
        calendar.setShowISOWeekNumbers(true);
        calendar.setValue(date);
        calendar.setImmediate(true);
        calendar.setResolution(Resolution.MINUTE);
        calendar.addValueChangeListener(e -> {
            Notification.show("Value changed:", String.valueOf(calendar.getValue()),
                    Notification.Type.TRAY_NOTIFICATION);
        });
        verticalLayout1.addComponent(calendar);
        verticalLayout1.setSpacing(true);
        verticalLayout1.setMargin(true);

        addComponent(verticalLayout1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}