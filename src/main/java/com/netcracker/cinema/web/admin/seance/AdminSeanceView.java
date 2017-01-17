package com.netcracker.cinema.web.admin.seance;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Price;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.PriceService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.service.schedule.impl.ScheduleServiceImpl;
import com.netcracker.cinema.web.AdminUI;
import com.netcracker.cinema.web.admin.movie.MovieForm;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.WebBrowser;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;

/**
 * Created by Titarenko on 22.12.2016.
 */
@SpringView(name = AdminSeanceView.VIEW_NAME, ui = AdminUI.class)
public class AdminSeanceView extends GridLayout implements View {
    public static final String VIEW_NAME = "seances";

    @Autowired
    SeanceService seanceService;
    @Autowired
    MovieService movieService;
    @Autowired
    PriceService priceService;
    @Autowired
    ScheduleServiceImpl schedule;

    Date date = new Date(System.currentTimeMillis());
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private VerticalLayout verticalLayout1 = new VerticalLayout();
    private VerticalLayout hallLayout1 = new VerticalLayout();
    private VerticalLayout hallLayout2 = new VerticalLayout();
    private Window subWindow;

    private WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
    private int screenWidth = webBrowser.getScreenWidth();
    private int screenHeight = webBrowser.getScreenHeight();

    @PostConstruct
    protected void init() {
        InlineDateField calendar = new InlineDateField();
        calendar.setShowISOWeekNumbers(true);
        calendar.setValue(date);
        calendar.addValueChangeListener(e -> {
            date = calendar.getValue();
            getHall(1);
            getHall(2);
        });

        Button windowButton = new Button("Add new seance");
        windowButton.setWidth("286px");
        windowButton.addClickListener(e -> {
            if (subWindow != null) {
                subWindow.close();
            }
            subWindow = new SubWindow(this, new Seance());
            UI.getCurrent().addWindow(subWindow);
        });

        verticalLayout1.setSpacing(true);
        verticalLayout1.setMargin(true);
        verticalLayout1.addComponents(calendar, windowButton);
        verticalLayout1.setWidth("360px");
        hallLayout1.setWidth("570px");
        hallLayout2.setWidth("570px");
        getHall(1);
        getHall(2);

        setColumns(3);
        addComponent(verticalLayout1, 0, 0);
        addComponent(hallLayout1, 1, 0);
        addComponent(hallLayout2, 2, 0);
        setSizeFull();
    }

    private void notificationForUnmodifiedSeance() {
        String message = "Few seconds ago someone has booked tickets"
                + LineSeparator.Windows + "for this seance, so it can't be modified.";
        Notification.show("Booked tickets", message, Notification.Type.TRAY_NOTIFICATION);
    }

    void getHall(long hallId) {
        VerticalLayout layout = (hallId == 1 ? hallLayout1 : hallLayout2);

        List<Seance> seanceList = seanceService.getByHallAndDate(hallId, date);
        seanceList.sort(Comparator.comparing(Seance::getSeanceDate));
        layout.removeAllComponents();
        layout.setSpacing(true);
        layout.setMargin(true);

        Label hallLabel = new Label("Hall " + hallId);
        hallLabel.setStyleName(ValoTheme.LABEL_H2);
        Label dateLabel = new Label(dateFormat.format(date));

        VerticalLayout verticalLayout = new VerticalLayout();
        for (Seance seance : seanceList) {
            HorizontalLayout horizontalLayout = getSeanceRow(seance);
            horizontalLayout.setMargin(true);
            verticalLayout.addComponent(horizontalLayout);
        }

        Panel hallPanel = new Panel();
        hallPanel.setHeight(screenHeight > 1000 ? "750px" : "550px");
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
        Label beginTime = new Label("begin: " + timeFormat.format(seance.getSeanceDate()));
        Date endDate = new Date(seance.getSeanceDate().getTime() + movie.getDuration() * 60_000);
        Label endTime = new Label("end: " + timeFormat.format(endDate) + " + 20min");

        String priceList = "";
        int zoneId = (seance.getHallId() == 1 ? 3 : 6);
        long maxZoneId = zoneId + 3;
        for (; zoneId < maxZoneId; zoneId++) {
            Price price = priceService.getPriceBySeanceZone(seance.getId(), zoneId);
            priceList = priceList + " " + price.getPrice() + ",";
        }
        Label prices = new Label("prices: " + priceList.substring(0, priceList.length() - 1));

        movieInfo.addComponents(name, beginTime, endTime, prices);

        Button editButton = new Button("Edit");
        editButton.setEnabled(seanceService.editableSeance(seance.getId()));
        editButton.addClickListener(e -> {
            if (subWindow != null) {
                subWindow.close();
            }
            subWindow = new SubWindow(this, seance);
            UI.getCurrent().addWindow(subWindow);
        });

        Button removeButton = new Button("Remove");
        removeButton.setEnabled(seanceService.editableSeance(seance.getId()));
        removeButton.addClickListener(e -> UI.getCurrent().addWindow(confirmWindow(seance)));

        horizontalLayout.addComponents(
                createPoster(movie, "80px"), movieInfo, editButton, removeButton);
        return horizontalLayout;
    }

    private Window confirmWindow(Seance seance) {
        Window removalWindow = new Window("Confirm erasing");
        removalWindow.setHeight("170px");
        removalWindow.setWidth("350px");
        removalWindow.setResizable(false);
        removalWindow.setModal(true);
        removalWindow.center();

        Label seanceDate = new Label();
        seanceDate.setValue("Seance date: " + dateFormat.format(seance.getSeanceDate())
                + " " + timeFormat.format(seance.getSeanceDate()));
        Label message = new Label();
        message.setValue("Do you really want to delete this seance?");

        Button yesButton = new Button("Yes");
        yesButton.setWidth("60px");
        yesButton.addClickListener(e -> {
            if (seanceService.editableSeance(seance.getId())) {
                seanceService.delete(seance);

                schedule.deleteTask(seance.getId());

                removalWindow.close();
                getHall(seance.getHallId());
                Notification.show("Seance deleted");
            } else {
                notificationForUnmodifiedSeance();
            }
        });
        Button noButton = new Button("No");
        noButton.setWidth("60px");
        noButton.addClickListener(e -> removalWindow.close());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponents(yesButton, noButton);

        AbsoluteLayout layout = new AbsoluteLayout();
        layout.addComponent(seanceDate, "left: 20px; top: 10px;");
        layout.addComponent(message, "left: 20px; top: 40px;");
        layout.addComponent(horizontalLayout, "left: 100px; top: 80px;");

        removalWindow.setContent(layout);
        return removalWindow;
    }

    Component createPoster(Movie movie, String height) {
        ExternalResource res = new ExternalResource(movie.getPoster());
        Image poster = new Image(null, res);
        poster.setHeight(height);
        poster.setWidth("54px");
        return poster;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}