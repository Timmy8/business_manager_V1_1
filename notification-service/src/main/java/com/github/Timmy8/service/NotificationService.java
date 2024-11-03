package com.github.Timmy8.service;

import com.github.Timmy8.producer.NotificationProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {
    private final List<NotificationProducer> producers;

    @KafkaListener(topics = "core_api_notifications", groupId = "notification-group")
    public void listen(String message){
        if (message.trim().isEmpty())
            log.warn("Trying to send empty message.");
        else
            sendNotifications(message);
    }

    public void sendNotifications(String message) {
        if (producers.isEmpty())
            throw new IllegalStateException("No producers available to send notification!");

        producers.forEach(producer -> {
            try {
                producer.sendNotification(message);
            } catch (Exception ex){
                log.warn("Exception during sending using " + producer.getClass().getName() + "\n"
                            + "message: " + ex.getMessage(), ex);
            }
        });
    }
}
