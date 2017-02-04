package com.netcracker.cinema.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by dimka on 27.01.2017.
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfiguration {

    @Autowired
    Environment environment;

    @Bean
    public JavaMailSender javaMailSender() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        mailProperties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        mailProperties.put("mail.debug", environment.getProperty("mail.debug"));

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty("mail.host"));
        javaMailSender.setPort(Integer.parseInt(environment.getProperty("mail.port")));
        javaMailSender.setUsername(environment.getProperty("mail.username"));
        javaMailSender.setPassword(environment.getProperty("mail.password"));
        javaMailSender.setProtocol(environment.getProperty("mail.protocol"));
        javaMailSender.setJavaMailProperties(mailProperties);

        return javaMailSender;
    }

}
