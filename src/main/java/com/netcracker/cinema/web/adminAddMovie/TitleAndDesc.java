package com.netcracker.cinema.web.adminAddMovie;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Илья on 17.11.2016.
 */
public class TitleAndDesc extends VerticalLayout {
    TextField title;
    TextArea description;

    public TitleAndDesc() {
        title = new TextField();
        title.setCaption("Title");
        description = new TextArea();
        description.setCaption("Description");
        addComponent(title);
        addComponent(description);
        title.setNullSettingAllowed(false);
        description.setNullSettingAllowed(false);

    }
}
