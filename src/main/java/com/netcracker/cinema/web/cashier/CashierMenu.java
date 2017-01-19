package com.netcracker.cinema.web.cashier;

import com.vaadin.server.Page;
import com.vaadin.ui.MenuBar;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by dimka on 04.12.2016.
 */
public class CashierMenu extends MenuBar {

    public CashierMenu() {
        MenuBar.Command command = selectedItem -> {
            if (selectedItem.getText().equals("Schedule")) {
                getUI().getNavigator().navigateTo(ScheduleViewCashier.VIEW_NAME);
            }

            if (selectedItem.getText().equals("Payment confirmation")) {
                getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME);
            }

            if(selectedItem.getText().equals("Logout")){
                SecurityContextHolder.clearContext();
                getUI().getSession().close();
                Page.getCurrent().open("login", null);
            }
        };

        addItem("Schedule", command);
        addItem("Payment confirmation", command);
        addItem("Logout", command);
    }
}
