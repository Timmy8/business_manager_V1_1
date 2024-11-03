package com.github.Timmy8.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final List<NotificationProducer> producers;

    @PostConstruct
    public void init(){
        sendNotifications("Test Notification!");
    }

    public void sendNotifications(String message) {
        producers.forEach(producer -> producer.sendNotification(message));
    }
}
