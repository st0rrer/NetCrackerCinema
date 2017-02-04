package com.netcracker.cinema.service.notification.impl;

import com.netcracker.cinema.exception.CinemaException;
import com.netcracker.cinema.model.Notification;
import com.netcracker.cinema.service.notification.NotificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by dimka on 27.01.2017.
 */
@Service
@Qualifier(value = "notificationService")
public class NotificationServiceImpl implements NotificationService {
    private static final Logger LOGGER = Logger.getLogger(NotificationServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;
    private NotificationBuilder builder;

    @PostConstruct
    void init() {
        builder = new NotificationBuilder();
    }

    @Async(value = "emailExecutor")
    @Override
    public void sendNotification(String email, String code) {
        Notification notification = builder.buildBookingNotification(email, code);
        send(notification.getRecipient(), notification.getRecipientMail(), notification.getSubject(), notification.getMessage());
    }

    private void send(String from, String to, String subject, String message) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);
            javaMailSender.send(mail);
        } catch (MessagingException exception) {
            throw new CinemaException("Can't send the message", exception);
        }
    }
}
