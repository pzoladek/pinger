package com.pzold.pinger.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final String sentFrom;
    private final String sentTo;

    public MailService(final JavaMailSender mailSender,
                       @Value("${spring.mail.username}") final String sentFrom,
                       @Value("${mail.admin}") final String sentTo) {
        this.mailSender = mailSender;
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
    }

    @Scheduled(fixedRateString = "${mail.scheduling.rate}")
    public void sendAdminMail() {
        final var message = new SimpleMailMessage();
        message.setFrom(sentFrom);
        message.setTo(sentTo);
        message.setSubject("pinger test email");
        message.setText("Hi, it's test mail from pinger app.");
        mailSender.send(message);
    }
}
