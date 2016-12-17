package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;

class ScheduleFilterComponent extends CustomComponent {

    private SeanceFilter seanceFilter;
    private NativeSelect selectDate;
    private NativeSelect selectHall;

    public ScheduleFilterComponent() {
        HorizontalLayout root = new HorizontalLayout();
        root.setMargin(true);
        root.setSpacing(true);
        setCompositionRoot(root);
        filterDay(root);
        filterHall(root);
        seanceFilter = new SeanceFilter().orderByIdDesc();
    }

    private void filterDay(HorizontalLayout layout) {
        ArrayList<String> list = new ArrayList<>();
        list.add("All days");
        list.add("Today");
        list.add("Tomorrow");
        selectDate = new NativeSelect("Filter by Day");
        selectDate.setNullSelectionAllowed(false);
        selectDate.addItems(list);
        selectDate.setValue(list.get(0));
        selectDate.setWidth("100px");
        selectDate.setHeight("50px");
        layout.addComponent(selectDate);
        selectDate.setSizeFull();
        selectDate.setImmediate(true);
        selectDate.addAttachListener(attachEvent -> {
            createNewFilter();
        });
    }

    private void filterHall(HorizontalLayout layout) {
        ArrayList<String> list = new ArrayList<>();
        list.add("All hall");
        list.add("Hall 1");
        list.add("Hall 2");
        selectHall = new NativeSelect("Filter by hall");
        selectHall.setNullSelectionAllowed(false);
        selectHall.addItems(list);
        selectHall.setValue(list.get(0));
        selectHall.setWidth("100px");
        selectHall.setHeight("50px");
        layout.addComponent(selectHall);
        selectHall.setSizeFull();
        selectHall.setImmediate(true);

        selectHall.addAttachListener(attachEvent -> {
            createNewFilter();
        });
    }


    private void createNewFilter() {
        String hallFilter = (String) selectHall.getValue();
        String dateFilter = (String) selectDate.getValue();
        Date today = new Date();
        seanceFilter = new SeanceFilter().forHallId(Integer.parseInt(hallFilter)).forDateRange(new Date(), DateUtils.addDays(new Date(), 1));
    }
}
