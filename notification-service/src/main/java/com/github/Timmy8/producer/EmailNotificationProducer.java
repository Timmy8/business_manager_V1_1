package com.github.Timmy8.producer;

import com.github.Timmy8.entity.EmailUser;
import com.github.Timmy8.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
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

    @Override
    public void sendNotification(String message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        var users = repository.findAll();

        emailMessage.setTo(users.stream().map(EmailUser::getEmail).toArray(String[]::new));
        emailMessage.setSubject(subject);
        emailMessage.setFrom(from);
        emailMessage.setText(message);

        log.info("Trying to send email notification to all users.\nMessage: {}", message);

        try{
            emailSender.send(emailMessage);
        } catch (MailException ex){
            log.error("Failed to send email message. Error: {}", ex.getMessage(), ex);
            throw new RuntimeException("Unable to send message via Gmail API", ex);
        }
    }
}
