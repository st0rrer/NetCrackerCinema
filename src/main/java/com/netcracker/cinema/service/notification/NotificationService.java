package com.netcracker.cinema.service.notification;

import com.netcracker.cinema.model.Notification;
import org.springframework.stereotype.Service;

/**
 * Created by dimka on 27.01.2017.
 */
public interface NotificationService {
    void sendNotification(String email, String code);
}
