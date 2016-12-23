package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = ScheduleView.VIEW_NAME, ui = UserUI.class)
public class ScheduleView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "seance";
    private static final Integer SIZE_PAGE = 8;

    @Autowired
    SeanceService seanceService;
    @Autowired
    private ScheduleTable scheduleTable;
    @Autowired
    private ScheduleFilterComponent scheduleFilterComponent;

    private SeanceFilter filter = new SeanceFilter().actual().orderByIdDesc();
    private Paginator<Seance> paginator;
    private List<Seance> seances;

    private PaginationBar paginationBar;

    @PostConstruct
    void init() {

        paginator = seanceService.getPaginator(SIZE_PAGE, filter);

        addComponent(scheduleFilterComponent);
        addComponent(scheduleTable);

        scheduleTable.updateGrid(paginator.getPage(1));

        scheduleFilterComponent.getSelectDate().addValueChangeListener(event -> {
            scheduleFilterComponent.setDateFilter(event.getProperty().getValue());
            if(!filter.equals(scheduleFilterComponent.getSeanceFilter())) {
                updatePaginator(scheduleFilterComponent.getSeanceFilter());
            }
        });

        scheduleFilterComponent.getSelectHall().addValueChangeListener(event -> {
            scheduleFilterComponent.setHallFilter(event.getProperty().getValue());
            if(!filter.equals(scheduleFilterComponent.getSeanceFilter())) {
                updatePaginator(scheduleFilterComponent.getSeanceFilter());
            }
        });

        paginationBar = new PaginationBar(paginator.availablePages());
        addComponent(paginationBar);
        setComponentAlignment(paginationBar, Alignment.TOP_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private void updatePaginator(SeanceFilter seanceFilter) {
        filter = scheduleFilterComponent.getSeanceFilter();
        paginator = seanceService.getPaginator(SIZE_PAGE, filter);
        if(paginator.availablePages() >= 0) {
            seances = paginator.getPage(1);
            paginationBar.setMaxPage(paginator.availablePages());
            scheduleTable.updateGrid(paginator.getPage(1));
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

        PaginationBar(long maxPage) {
            this.maxPage = maxPage;
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
                } else if (event.getButton() == previous) {
                    currentPage--;
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
        }
    }

}
