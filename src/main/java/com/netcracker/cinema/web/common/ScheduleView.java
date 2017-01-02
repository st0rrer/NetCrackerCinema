package com.netcracker.cinema.web.common;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.HallService;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.cashier.ScheduleTableCashier;
import com.netcracker.cinema.web.user.ScheduleTableUser;
import com.vaadin.data.Container;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

public abstract class ScheduleView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "seance";
    private static final Integer SIZE_PAGE = 8;

    @Autowired
    SeanceService seanceService;
    @Autowired
    HallService hallService;
    @Autowired
    MovieService movieService;

    private SeanceFilter seanceFilter = new SeanceFilter().actual().orderByStartDateDesc();
    private Paginator<Seance> paginator;
    private List<Seance> seances;

    private PaginationBar paginationBar;
    private ScheduleFilterComponent scheduleFilterComponent;

    @PostConstruct
    public void init() {

        scheduleFilterComponent = new ScheduleFilterComponent();
        paginationBar = new PaginationBar();

        addComponent(scheduleFilterComponent);
        addComponent(paginationBar);
        setComponentAlignment(paginationBar, Alignment.TOP_CENTER);

        this.updatePaginator(seanceFilter);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private void updatePaginator(SeanceFilter seanceFilter) {
        paginator = seanceService.getPaginator(SIZE_PAGE, seanceFilter);
        if (paginator.availablePages() > 1) {
            seances = paginator.getPage(1);
            paginationBar.setMaxPage(paginator.availablePages());
            paginationBar.setVisible(true);
            updateGrid(seances);
        } else if (paginator.availablePages() >= 0 || paginator.availablePages() <= 1){
            seances = paginator.getPage(1);
            paginationBar.setVisible(false);
            paginationBar.setMaxPage(1);
            updateGrid(seances);
        }
    }

    public abstract void updateGrid(List<Seance> seances);

    private class PaginationBar extends HorizontalLayout {
        private CssLayout buttonsForPaging;
        private Button first;
        private Button last;
        private Button next;
        private Button previous;
        private Label currentPageView;

        private int currentPage = 1;
        private long maxPage;

        PaginationBar() {
            initButtons();
        }

        private final Button.ClickListener handler = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (event.getButton() == first) {
                    currentPage = 1;
                } else if (event.getButton() == last) {
                    currentPage = (int) maxPage;
                } else if (event.getButton() == next) {
                    currentPage++;
                    if(currentPage == maxPage) {
                        previous.setEnabled(true);
                        next.setEnabled(false);
                    } else {
                        previous.setEnabled(true);
                        next.setEnabled(true);
                    }
                } else if (event.getButton() == previous) {
                    currentPage--;
                    if(currentPage == 1) {
                        next.setEnabled(true);
                        previous.setEnabled(false);
                    } else {
                        next.setEnabled(true);
                        previous.setEnabled(true);
                    }
                }
                List<Seance> seances = paginator.getPage(currentPage);
                updateGrid(seances);
                currentPageView.setValue(String.valueOf(currentPage) + " page");
            }
        };

        private void initButtons() {
            currentPageView = new Label();
            currentPageView.setValue(String.valueOf(currentPage) + " page");
            first = new Button(FontAwesome.FAST_BACKWARD);
            first.addClickListener(handler);
            first.setStyleName(ValoTheme.BUTTON_BORDERLESS);
            last = new Button(FontAwesome.FAST_FORWARD);
            last.addClickListener(handler);
            last.setStyleName(ValoTheme.BUTTON_BORDERLESS);
            next = new Button(FontAwesome.FORWARD);
            next.addClickListener(handler);
            next.setStyleName(ValoTheme.BUTTON_BORDERLESS);
            previous = new Button(FontAwesome.BACKWARD);
            previous.addClickListener(handler);
            previous.setStyleName(ValoTheme.BUTTON_BORDERLESS);
            buttonsForPaging = new CssLayout(first, last, next, previous);
            addComponent(currentPageView);
            setComponentAlignment(currentPageView, Alignment.MIDDLE_CENTER);
            this.setExpandRatio(currentPageView, 1.0f);
            addComponent(buttonsForPaging);
            setComponentAlignment(buttonsForPaging, Alignment.BOTTOM_CENTER);
        }

        public void setMaxPage(long maxPage) {
            this.maxPage = maxPage;
            if(currentPage == 1) {
                previous.setEnabled(false);
            }
        }
    }

    private class ScheduleFilterComponent extends HorizontalLayout {

        private final String DEFAULT_DATE = "Available seances";
        private final String DEFAULT_HALL = "All halls";

        private NativeSelect selectDate;
        private NativeSelect selectHall;
        private ComboBox filterByName;

        private Object dateFilter = DEFAULT_DATE;
        private Object hallFilter = DEFAULT_HALL;

        public ScheduleFilterComponent() {
            this.setMargin(true);
            this.setSpacing(true);
            filterDay();
            filterHall();
            createLayoutForFilterName();
            this.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

            selectDate.addValueChangeListener(event -> {
                dateFilter = event.getProperty().getValue();
                updateSeanceFilter();
            });

            selectHall.addValueChangeListener(event -> {
                hallFilter = event.getProperty().getValue();
                updateSeanceFilter();
            });

            filterByName.addValueChangeListener(event -> {
                updateSeanceFilter();
            });
        }

        private void createLayoutForFilterName() {
            filterByName = new ComboBox("Filter by movie's name");
            filterByName.setSizeFull();
            filterByName.setWidth("350px");
            filterByName.setHeight("40px");
            filterByName.setFilteringMode(FilteringMode.CONTAINS);
            filterByName.setPageLength(5);
            List<Movie> movies = movieService.findAll();
            for (Movie movie : movies) {
                filterByName.addItem(movie.getName());
            }
            this.addComponent(filterByName);
            this.setComponentAlignment(filterByName, Alignment.BOTTOM_CENTER);
        }

        private void filterDay() {
            selectDate = new NativeSelect("Filter by Day");
            selectDate.setSizeFull();
            fillScrollOfDate(selectDate);
            setDefaultNativeSelect(selectDate);
            this.addComponent(selectDate);
        }

        private void filterHall() {
            selectHall = new NativeSelect("Filter by hall");
            selectHall.setSizeFull();
            fillScrollOfHalls(selectHall);
            setDefaultNativeSelect(selectHall);
            this.addComponent(selectHall);
        }

        private void setDefaultNativeSelect(NativeSelect nativeSelect) {
            nativeSelect.setNullSelectionAllowed(false);
            nativeSelect.setWidth("200px");
            nativeSelect.setHeight("40px");
            nativeSelect.setImmediate(true);
        }

        private void fillScrollOfHalls(NativeSelect selectHall) {
            selectHall.addItem(DEFAULT_HALL);
            List<Hall> allHalls = hallService.findAll();
            for(Hall hall : allHalls) {
                selectHall.addItem(hall);
                selectHall.setItemCaption(hall, hall.getName());
            }
            selectHall.setValue(DEFAULT_HALL);
        }

        private void fillScrollOfDate(NativeSelect selectDate) {
            Date currentDay = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            Date[] currentDayInterval = new Date[] {new Date(), DateUtils.addDays(currentDay, 1)};
            selectDate.addItem(currentDayInterval);
            selectDate.setItemCaption(currentDayInterval, "Today");
            Date[] tomorrowInterval = new Date[] {DateUtils.addDays(currentDay, 1), DateUtils.addDays(currentDay, 2)};
            selectDate.addItem(tomorrowInterval);
            selectDate.setItemCaption(tomorrowInterval, "Tomorrow");
            Date[] nextWeekInterval = new Date[] {new Date(), DateUtils.addDays(currentDay, 8)};
            selectDate.addItem(nextWeekInterval);
            selectDate.setItemCaption(nextWeekInterval, "Next week");
            selectDate.addItem(DEFAULT_DATE);
            selectDate.setValue(DEFAULT_DATE);
        }

        private void updateSeanceFilter() {
            seanceFilter = new SeanceFilter().orderByStartDateDesc().actual();
            if(dateFilter.getClass() != String.class) {
                Date[] dateInterval = (Date[]) dateFilter;
                seanceFilter.forDateRange(dateInterval[0], dateInterval[1]);
            }

            if(hallFilter.getClass() != String.class) {
                seanceFilter.forHallId(((Hall) hallFilter).getId());
            }

            if(filterByName.getValue() != null) {
                seanceFilter.forMovieName((String) filterByName.getValue());
            }
            updatePaginator(seanceFilter);
        }
    }
}
