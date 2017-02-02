package com.netcracker.cinema.utils;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;

public class ConfirmationDialog {

    public Window infoDialog(String message) {
        Window subWindow = new Window("Confirmation");
        subWindow.setHeight("225px");
        subWindow.setWidth("300px");

        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        subWindow.setContent(subContent);
        subWindow.center();
        subWindow.setModal(true);

        Button button = new Button("OK");
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        button.addClickListener(e -> {
            UI.getCurrent().removeWindow(subWindow);
        });
        Label label = new Label(message);

        subContent.addComponents(label, button);
        return subWindow;
    }

}