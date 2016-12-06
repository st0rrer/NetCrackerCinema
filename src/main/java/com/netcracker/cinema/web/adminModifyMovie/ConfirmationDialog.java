package com.netcracker.cinema.web.adminModifyMovie;

import com.vaadin.ui.*;
        import com.vaadin.ui.Button.ClickEvent;

public class ConfirmationDialog {

    public Window infoDialog(MyUI myUI, String message) {
        Window subWindow = new Window("Confirmation");
        subWindow.setHeight("150px");
        subWindow.setWidth("200px");

        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        subWindow.setContent(subContent);
        subWindow.center();

        Button button = new Button("OK");
        button.addClickListener(e -> {
            myUI.removeWindow(subWindow);
        });
        Label label = new Label(message);

        subContent.addComponents(label, button);
        return subWindow;
    }

}