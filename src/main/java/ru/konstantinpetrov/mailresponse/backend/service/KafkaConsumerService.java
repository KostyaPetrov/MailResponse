package ru.konstantinpetrov.mailresponse.backend.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "baeldung", groupId = "group_id")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}
