package com.github.Timmy8.service;

import com.github.Timmy8.entity.EmailUser;
import com.github.Timmy8.repository.EmailRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Setter
@Component
public class EmailNotificationProducer implements NotificationProducer{
    private final JavaMailSender emailSender;
    private final EmailRepository repository;
    @Value("${spring.mail.notification.message.subject}")
    private String subject;
    @Value("${spring.mail.notification.message.from}")
    private String from;


    //@PostConstruct
    public void init(){
        sendNotification("Hello users!");
    }

    @Override
    public void sendNotification(String message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        var users = repository.findAll();

        emailMessage.setTo(users.stream().map(EmailUser::getEmail).toArray(String[]::new));
        emailMessage.setSubject(subject);
        emailMessage.setFrom(from);
        emailMessage.setText(message);

        emailSender.send(emailMessage);
    }
}
