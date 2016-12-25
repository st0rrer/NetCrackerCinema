package com.netcracker.cinema.web.cashier;

import com.vaadin.ui.MenuBar;

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
        };

        addItem("Schedule", command);
        addItem("Payment confirmation", command);
    }
}
