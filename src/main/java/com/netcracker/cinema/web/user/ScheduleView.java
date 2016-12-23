package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.HallService;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.data.Property;
import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringView(name = ScheduleView.VIEW_NAME, ui = UserUI.class)
public class ScheduleView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "seance";
    private static final Integer SIZE_PAGE = 8;

    @Autowired
    SeanceService seanceService;
    @Autowired
    HallService hallService;
    @Autowired
    private ScheduleTable scheduleTable;

    private SeanceFilter seanceFilter = new SeanceFilter().actual().orderByStartDateDesc();
    private Paginator<Seance> paginator;
    private List<Seance> seances;

    private PaginationBar paginationBar;
    private ScheduleFilterComponent scheduleFilterComponent;
    @PostConstruct
    void init() {

        scheduleFilterComponent = new ScheduleFilterComponent();
        paginationBar = new PaginationBar();

        addComponent(scheduleFilterComponent);
        addComponent(scheduleTable);
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
            scheduleTable.updateGrid(seances);
        } else if (paginator.availablePages() == 0) {
            seances = paginator.getPage(1);
            paginationBar.setVisible(false);
            scheduleTable.updateGrid(seances);
        }
    }

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
                scheduleTable.updateGrid(paginator.getPage(currentPage));
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
            this.setExpandRatio(currentPageView, 1.0f);
            addComponent(buttonsForPaging);
            setComponentAlignment(buttonsForPaging, Alignment.TOP_CENTER);
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

        private Object dateFilter = DEFAULT_DATE;
        private Object hallFilter = DEFAULT_HALL;

        public ScheduleFilterComponent() {
            this.setMargin(true);
            this.setSpacing(true);
            filterDay();
            filterHall();

            selectDate.addValueChangeListener(event -> {
                dateFilter = event.getProperty().getValue();
                updateSeanceFilter();
            });

            selectHall.addValueChangeListener(event -> {
                hallFilter = event.getProperty().getValue();
                updateSeanceFilter();
            });
        }

        private void filterDay() {
            selectDate = new NativeSelect("Filter by Day");
            fillScrollOfDate(selectDate);
            setDefaultNativeSelect(selectDate);
            this.addComponent(selectDate);
        }

        private void filterHall() {
            selectHall = new NativeSelect("Filter by hall");
            fillScrollOfHalls(selectHall);
            setDefaultNativeSelect(selectHall);
            this.addComponent(selectHall);
        }

        private void setDefaultNativeSelect(NativeSelect nativeSelect) {
            nativeSelect.setNullSelectionAllowed(false);
            nativeSelect.setWidth("100px");
            nativeSelect.setHeight("50px");
            nativeSelect.setSizeFull();
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
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd MM yyyy");
            Date currentDay = new Date();
            currentDateFormat.format(currentDay);
            selectDate.addItem(currentDay);
            selectDate.setItemCaption(currentDay, "Today");
            selectDate.addItem(DateUtils.addDays(currentDay, 1));
            selectDate.setItemCaption(DateUtils.addDays(currentDay, 1), "Tomorrow");
            selectDate.addItem(DateUtils.addDays(currentDay, 7));
            selectDate.setItemCaption(DateUtils.addDays(currentDay, 7), "Next week");
            selectDate.addItem(DEFAULT_DATE);
            selectDate.setValue(DEFAULT_DATE);
        }

        private void updateSeanceFilter() {
            seanceFilter = new SeanceFilter().orderByStartDateDesc();
            if(dateFilter.getClass() == String.class) {
                seanceFilter.actual();
            } else {
                seanceFilter.forDateRange(new Date(), (Date) dateFilter);
            }

            if(hallFilter.getClass() != String.class) {
                seanceFilter.forHallId(((Hall) hallFilter).getId());
            }
            updatePaginator(seanceFilter);
        }
    }
}