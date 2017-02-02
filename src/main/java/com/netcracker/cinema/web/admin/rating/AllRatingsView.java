package com.netcracker.cinema.web.admin.rating;

import com.netcracker.cinema.model.*;
import com.netcracker.cinema.service.RatingService;
import com.netcracker.cinema.web.AdminUI;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.tepi.filtertable.paged.PagedFilterTable;
import org.tepi.filtertable.paged.PagedFilterControlConfig;
import com.vaadin.ui.CustomTable.RowHeaderMode;
import com.vaadin.annotations.*;

import javax.annotation.PostConstruct;

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
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.addComponent(allBuildPagedTableTab());
        addComponents(layout);
    }


    private Component allBuildPagedTableTab() {
        /* Create FilterTable */
        PagedFilterTable<IndexedContainer> pagedFilterTable = allBuildPagedFilterTable();
        final VerticalLayout mainLayout = new VerticalLayout();
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
        filter.setVisibleColumns((Object[]) new String[]{"movieName", "hallName", "zoneName",
                "ticketSold", "price", "startDate", "endDate"});

        return filter;
    }

    @SuppressWarnings("unchecked")
    private Container allBuildContainer() {
        // Have a container of some type to contain the data
        BeanItemContainer<Rating> container = new BeanItemContainer<Rating>(Rating.class, ratingService.findAll());
        return container;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
