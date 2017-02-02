package com.netcracker.cinema.web.admin.rating;

import com.netcracker.cinema.model.*;
import com.netcracker.cinema.service.RatingService;
import com.netcracker.cinema.web.AdminUI;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.tepi.filtertable.paged.PagedFilterTable;
import org.tepi.filtertable.paged.PagedFilterControlConfig;
import com.vaadin.ui.CustomTable.RowHeaderMode;
import com.vaadin.annotations.*;

import javax.annotation.PostConstruct;
import java.util.Locale;

@SpringView(name = AllRatingsView.VIEW_NAME, ui = AdminUI.class)
@SuppressWarnings("serial")
@Title("Rating")
@Theme("valo")
public class AllRatingsView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "ratings";

    @Autowired
    private RatingService ratingService;

    @PostConstruct
    protected void init() {
        setLocale(new Locale("ru", "RU"));
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(new MarginInfo(true, false, false, false));
        mainLayout.setSizeFull();

        final TabSheet tabSheet = new TabSheet();
        tabSheet.setStyleName(Reindeer.TABSHEET_MINIMAL);
        tabSheet.setSizeFull();
        mainLayout.addComponent(tabSheet);
        mainLayout.setExpandRatio(tabSheet, 1);

        tabSheet.addTab(allBuildPagedTableTab(), "All Rating");
        tabSheet.addTab(hallBuildPagedTableTab(), "Hall Rating");
        tabSheet.addTab(zoneBuildPagedTableTab(), "Zone Rating");

        addComponents(mainLayout);
    }


    private Component allBuildPagedTableTab() {
        /* Create FilterTable */
        PagedFilterTable<IndexedContainer> pagedFilterTable = allBuildPagedFilterTable();
        final VerticalLayout mainLayout = new VerticalLayout();
        addComponent(mainLayout);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.addComponent(pagedFilterTable);
        mainLayout.addComponent(pagedFilterTable.createControls(new PagedFilterControlConfig()));
        return mainLayout;
    }

    private PagedFilterTable<IndexedContainer> allBuildPagedFilterTable() {
        PagedFilterTable<IndexedContainer> filter = new PagedFilterTable<IndexedContainer>();
        filter.setWidth("100%");
        filter.setFilterDecorator(new DemoFilterDecorator());
        filter.setFilterGenerator(new DemoFilterGenerator());
        filter.setFilterBarVisible(true);
        filter.setSelectable(true);
        filter.setImmediate(true);
        filter.setMultiSelect(true);
        filter.setRowHeaderMode(RowHeaderMode.INDEX);
        filter.setColumnCollapsingAllowed(true);
        filter.setColumnReorderingAllowed(true);
        filter.setContainerDataSource(allBuildContainer());
        filter.setVisibleColumns((Object[]) new String[]{"movieName", "hallName", "zoneName", "ticketSold", "price", "startDate", "endDate"});
        return filter;
    }

    @SuppressWarnings("unchecked")
    private Container allBuildContainer() {
        // Have a container of some type to contain the data
        BeanItemContainer<Rating> container = new BeanItemContainer<Rating>(Rating.class, ratingService.findAll());
        return container;
    }

    private Component hallBuildPagedTableTab() {
        final VerticalLayout mainLayout = new VerticalLayout();
        addComponent(mainLayout);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.addComponent(hallBuildPagedFilterTable());
        return mainLayout;
    }

    private PagedFilterTable<IndexedContainer> hallBuildPagedFilterTable() {
        PagedFilterTable<IndexedContainer> filter = new PagedFilterTable<IndexedContainer>();
        filter.setWidth("100%");
        filter.setHeight("139px");
        filter.setFilterDecorator(new DemoFilterDecorator());
        filter.setFilterGenerator(new DemoFilterGenerator());
        filter.setFilterBarVisible(true);
        filter.setSelectable(true);
        filter.setImmediate(true);
        filter.setMultiSelect(true);
        filter.setRowHeaderMode(RowHeaderMode.INDEX);
        filter.setColumnCollapsingAllowed(true);
        filter.setColumnReorderingAllowed(true);
        filter.setContainerDataSource(hallBuildContainer());
        filter.setVisibleColumns((Object[]) new String[]{"hallName", "ticketSold", "price"});
        return filter;
    }

    @SuppressWarnings("unchecked")
    private Container hallBuildContainer() {
        BeanItemContainer<Rating> container = new BeanItemContainer<Rating>(Rating.class, ratingService.hallRatings());
        return container;
    }

    private Component zoneBuildPagedTableTab() {
        final VerticalLayout mainLayout = new VerticalLayout();
        addComponent(mainLayout);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.addComponent(zoneBuildPagedFilterTable());
        return mainLayout;
    }

    private PagedFilterTable<IndexedContainer> zoneBuildPagedFilterTable() {
        PagedFilterTable<IndexedContainer> filter = new PagedFilterTable<IndexedContainer>();
        filter.setWidth("100%");
        filter.setHeight("290px");
        filter.setFilterDecorator(new DemoFilterDecorator());
        filter.setFilterGenerator(new DemoFilterGenerator());
        filter.setFilterBarVisible(true);
        filter.setSelectable(true);
        filter.setImmediate(true);
        filter.setMultiSelect(true);
        filter.setRowHeaderMode(RowHeaderMode.INDEX);
        filter.setColumnCollapsingAllowed(true);
        filter.setColumnReorderingAllowed(true);
        filter.setContainerDataSource(zoneBuildContainer());
        filter.setVisibleColumns((Object[]) new String[]{"hallName", "zoneName", "ticketSold", "price"});
        return filter;
    }

    @SuppressWarnings("unchecked")
    private Container zoneBuildContainer() {
        BeanItemContainer<Rating> container = new BeanItemContainer<Rating>(Rating.class, ratingService.zoneRatings());
        return container;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
