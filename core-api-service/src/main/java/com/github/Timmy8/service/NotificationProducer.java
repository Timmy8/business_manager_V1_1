package com.github.Timmy8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationProducer {
    private static final String TOPIC = "core_api_notifications_topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(String message){
        kafkaTemplate.send(TOPIC, message);
    }
}
