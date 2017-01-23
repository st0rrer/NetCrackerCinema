package com.netcracker.cinema.web.admin.seance;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Price;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.PriceService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.service.schedule.impl.ScheduleServiceImpl;
import com.netcracker.cinema.web.AdminUI;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.WebBrowser;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Titarenko on 22.12.2016.
 */
@SpringView(name = AdminSeanceView.VIEW_NAME, ui = AdminUI.class)
public class AdminSeanceView extends HorizontalLayout implements View {
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
    Locale englishLocale = new Locale("en", "US");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", englishLocale);
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private VerticalLayout verticalLayout1 = new VerticalLayout();
    private VerticalLayout hallLayout1 = new VerticalLayout();
    private VerticalLayout hallLayout2 = new VerticalLayout();
    private Window subWindow;

    private WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
    private int screenHeight = webBrowser.getScreenHeight();

    @PostConstruct
    protected void init() {
        InlineDateField calendar = new InlineDateField();
        calendar.setLocale(englishLocale);
        calendar.setValue(date);
        calendar.addValueChangeListener(e -> {
            date = calendar.getValue();
            getHall(1);
            getHall(2);
        });

        Button windowButton = new Button("Add new seance");
        windowButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        windowButton.addClickListener(e -> {
            if (subWindow != null) {
                subWindow.close();
            }
            subWindow = new SubWindow(this, new Seance());
            UI.getCurrent().addWindow(subWindow);
        });

        verticalLayout1.setWidth("252px");
        verticalLayout1.setSpacing(true);
        verticalLayout1.addStyleName("seances-calendar");
        windowButton.addStyleName("seances-calendar-button");
        verticalLayout1.addComponents(calendar, windowButton);

        getHall(1);
        getHall(2);
        hallLayout1.addStyleName("hall-admin");
        hallLayout2.addStyleName("hall-admin");

        HorizontalLayout wrapper = new HorizontalLayout();
        wrapper.setMargin(true);
        wrapper.setSpacing(true);
        wrapper.addComponents(verticalLayout1, hallLayout1, hallLayout2);

        addComponent(wrapper);
        setComponentAlignment(wrapper, Alignment.TOP_CENTER);
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

        Label hallLabel = new Label("Hall " + hallId);
        hallLabel.addStyleName("hall-label");
        Label dateLabel = new Label("<i>" + dateFormat.format(date) + "</i>",
                ContentMode.HTML);

        VerticalLayout verticalLayout = new VerticalLayout();
        for (Seance seance : seanceList) {
            HorizontalLayout horizontalLayout = getSeanceRow(seance);
            verticalLayout.addComponent(horizontalLayout);
        }

        Panel hallPanel = new Panel();
        hallPanel.addStyleName("seance-panel");
        hallPanel.setHeight(screenHeight > 1000 ? "800px" : "490px");
        hallPanel.setContent(verticalLayout);

        layout.addComponents(hallLabel, dateLabel, hallPanel);
//        layout.setComponentAlignment(hallLabel, Alignment.MIDDLE_CENTER);
    }

    private HorizontalLayout getSeanceRow(Seance seance) {
        Movie movie = movieService.getById(seance.getMovieId());

        Label movieName = new Label(movie.getName());
        movieName.addStyleName("movie-name");

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

        VerticalLayout movieInfo = new VerticalLayout();
        movieInfo.setWidth("185px");
        movieInfo.addComponents(movieName, beginTime, endTime, prices);

        Button editButton = new Button("edit");
        editButton.setEnabled(seanceService.editableSeance(seance.getId()));
        editButton.addClickListener(e -> {
            if (subWindow != null) {
                subWindow.close();
            }
            subWindow = new SubWindow(this, seance);
            UI.getCurrent().addWindow(subWindow);
        });
        editButton.addStyleName("seance-buttons");

        Button removeButton = new Button("remove");
        removeButton.setEnabled(seanceService.editableSeance(seance.getId()));
        removeButton.addClickListener(e -> UI.getCurrent().addWindow(confirmWindow(seance)));
        removeButton.addStyleName("seance-buttons");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.addStyleName("seance-row");
        horizontalLayout.addComponents(
                createPoster(movie, 90), movieInfo, editButton, removeButton);
        return horizontalLayout;
    }

    private Window confirmWindow(Seance seance) {
        Window removalWindow = new Window("Confirm erasing");
        removalWindow.setHeight("170px");
        removalWindow.setWidth("350px");
        removalWindow.setResizable(false);
        removalWindow.setModal(true);
        removalWindow.center();

        Label message = new Label("Do you really want to delete this seance?");
        Label seanceDate = new Label();
        seanceDate.setValue("Seance date: " + dateFormat.format(seance.getSeanceDate())
                + " " + timeFormat.format(seance.getSeanceDate()));

        Button yesButton = new Button("Yes");
        yesButton.setWidth("60px");
        yesButton.setStyleName(ValoTheme.BUTTON_DANGER);
        yesButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
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
        noButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        noButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        noButton.addClickListener(e -> removalWindow.close());

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.addComponents(yesButton, noButton);

        AbsoluteLayout layout = new AbsoluteLayout();
        layout.addComponent(message, "left: 20px; top: 10px;");
        layout.addComponent(seanceDate, "left: 20px; top: 40px;");
        layout.addComponent(buttons, "left: 100px; top: 80px;");

        removalWindow.setContent(layout);
        return removalWindow;
    }

    Component createPoster(Movie movie, int height) {
        ExternalResource res = new ExternalResource(movie.getPoster());
        Image poster = new Image(null, res);
        poster.setHeight(height + "px");
        poster.setWidth(0.675 * height + "px");
        return poster;
    }

    @PreDestroy
    public void preDestroy() {
//        LOGGER.info("Destroy bean: " + this.getClass().getSimpleName());
        Collection<Window> windows = UI.getCurrent().getWindows();
//        LOGGER.debug("Get all windows: " + !windows.isEmpty());
        windows.forEach(Window::close);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}