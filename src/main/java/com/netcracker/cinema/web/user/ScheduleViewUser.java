package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.web.UserUI;
import com.netcracker.cinema.web.common.ScheduleView;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = ScheduleView.VIEW_NAME, ui = UserUI.class)
public class ScheduleViewUser extends ScheduleView {

    @Autowired
    ScheduleTableUser scheduleTable;

    @PostConstruct
    public void init() {
        super.init();
        scheduleTable.setSizeFull();
        addComponent(scheduleTable, 1);
    }

    @Override
    public void updateGrid(List<Seance> seances) {
        scheduleTable.updateGrid(seances);
    }
}