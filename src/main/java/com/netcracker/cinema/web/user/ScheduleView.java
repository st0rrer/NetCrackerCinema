package com.netcracker.cinema.web.user;

import javax.annotation.PostConstruct;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SpringView(name = ScheduleView.VIEW_NAME, ui = UserUI.class)
public class ScheduleView extends CustomComponent implements View {

	public static final String VIEW_NAME = "schedule";
	
	@PostConstruct
	public void init() {
		Label label = new Label("Implement me!");
		setCompositionRoot(label);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}
}
