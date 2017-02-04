package com.netcracker.cinema.service.notification.impl;

import com.netcracker.cinema.exception.CinemaException;
import com.netcracker.cinema.model.Notification;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dimka on 27.01.2017.
 */
class NotificationBuilder {
    private static final Logger LOGGER = Logger.getLogger(NotificationBuilder.class);

    VelocityEngine velocityEngine;
    private Properties messageProperties = new Properties();

    NotificationBuilder() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }

    Notification buildBookingNotification(String email, String code) {
        try {
            messageProperties.load(getClass().getClassLoader().getResourceAsStream("message.properties"));
        } catch (IOException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new CinemaException(e.getMessage(), e);
        }
        Map<String, Object> varOfMessage = new HashMap<>();
        varOfMessage.put("code", code);
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "velocity/bookingMessage.vm", "UTF-8", varOfMessage);

        Notification notification = new Notification();
        notification.setRecipient(messageProperties.getProperty("message.from"));
        notification.setRecipientMail(email);
        notification.setSubject(messageProperties.getProperty("message.subject"));
        notification.setMessage(text);
        return notification;
    }
}
