package com.netcracker.cinema.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dimka on 27.01.2017.
 */
@Getter
@Setter
public class Notification {

    private String recipient;
    private String recipientMail;
    private String subject;
    private String message;

}
